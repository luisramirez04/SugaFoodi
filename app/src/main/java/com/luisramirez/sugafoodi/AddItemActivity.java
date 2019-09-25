package com.luisramirez.sugafoodi;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class AddItemActivity extends AppCompatActivity {

    private final String TAG = "LOG:";
    public static final int PICK_IMAGE = 1;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseStorage storage;
    StorageReference storageReference;
    private FusedLocationProviderClient fusedLocationProviderClient;

    EditText nameEditText, restaurantEditText, locationEditText;
    RadioGroup typeRadioGroup;
    String type;
    Button submitButton, attachImageButton;
    ImageView foodImage;
    private Uri filePath;

    private final DocumentReference newRestaurantRef = db.collection("restaurants").document();
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("restaurantLocations");
    private final GeoFire geoFire = new GeoFire(mDatabase);


    final DocumentReference newItemRef = db.collection("items").document();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_item);

        // Access a Cloud Firestore instance from your Activity


        foodImage = findViewById(R.id.foodImage);

        submitButton = (Button) findViewById(R.id.buttonSubmit);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickSubmit(view);
            }
        });

        attachImageButton = (Button) findViewById(R.id.buttonAttach);
        attachImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickAttach(v);
            }
        });

        locationEditText = (EditText) findViewById(R.id.editTextAddRestaurantLocation);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_COARSE_LOCATION}, 1);


        } else {
            fusedLocationProviderClient.getLastLocation()
                    .addOnSuccessListener(new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                            List<Address> addresses;
                            try {
                                addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                                locationEditText.setText(addresses.get(0).getPostalCode());
                            } catch (Exception e) {
                                Log.d("Exception", e.toString());
                            }
                        }
                    });
        }

    }

    public void onClickAttach(View v) {
        Log.d("Click", "attach");
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if((requestCode == PICK_IMAGE) && (resultCode == RESULT_OK)
                && (data != null) && (data.getData() != null))
        {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                foodImage.setImageBitmap(bitmap);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    public void onClickSubmit(View v) {
        nameEditText = findViewById(R.id.editTextItemName);
        restaurantEditText = findViewById(R.id.editTextRestaurantName);
        typeRadioGroup = findViewById(R.id.radioGroupType);
        type = "";

        final String itemName = nameEditText.getText().toString();
        final String restaurantName = restaurantEditText.getText().toString();

        switch (typeRadioGroup.getCheckedRadioButtonId()) {
            case R.id.radioButtonFood:
                type = "Food";
                break;
            case R.id.radioButtonDrink:
                type = "Drink";
                break;
            case R.id.radioButtonDessert:
                type = "Dessert";
                break;
            default: break;
        }

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        // Create a new item
        Item item = new Item(newItemRef.getId(), itemName, restaurantName, type);

        // Add a new document with a generated ID
        newItemRef.set(item);

        db.collection("restaurants")
                .whereEqualTo("title", restaurantName)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            QuerySnapshot documentSnapshot = task.getResult();
                            if (documentSnapshot.isEmpty()) {
                                Log.d(TAG, "Adding new restaurant");
                                Restaurant restaurant = new Restaurant(newRestaurantRef.getId(), restaurantName, newItemRef.getId());

                                // Add a new document with a generated ID
                                newRestaurantRef.set(restaurant);
                            } else {
                                Log.d(TAG, "Updating restaurant");
                                for(QueryDocumentSnapshot doc : task.getResult()) {
                                    db.collection("restaurants").document(doc.getId())
                                            .update("items", FieldValue.arrayUnion((newItemRef.getId())));
                                }
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

        if(filePath != null)
            {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            StorageReference ref = storageReference.child("images/"+ newItemRef.getId());
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Uploaded", Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Failed "+e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded "+(int)progress+"%");
                        }
                    });
    }

        uploadLocation(newRestaurantRef, geoFire);


        Intent intent = new Intent(this, RestaurantsListActivity.class);
        startActivity(intent);

    }

    public void uploadLocation(final DocumentReference ref, final GeoFire geoFire) {
        Geocoder geocoder = new Geocoder(this);
        final String zipText = locationEditText.getText().toString();
        try {
            List<Address> addresses = geocoder.getFromLocationName(zipText, 1);
            if (addresses != null && !addresses.isEmpty()) {
                geoFire.setLocation(newRestaurantRef.getId(), new GeoLocation(addresses.get(0).getLatitude(), addresses.get(0).getLongitude()), new GeoFire.CompletionListener() {
                    @Override
                    public void onComplete(String key, DatabaseError error) {
                        if (error == null) {
                            Log.d("LocationDB: ", "Good");
                        } else {
                            Log.d("LocationDB: ", "Error");
                        }
                    }
                });
            }

        } catch(IOException e) {

        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    try {
                        fusedLocationProviderClient.getLastLocation()
                                .addOnSuccessListener(new OnSuccessListener<Location>() {
                                    @Override
                                    public void onSuccess(Location location) {
                                        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                                        List<Address> addresses;
                                        try {
                                            addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                                            locationEditText.setText(addresses.get(0).getPostalCode());
                                        } catch (Exception e) {
                                            Log.d("Exception", e.toString());
                                        }
                                    }
                                });
                    } catch (SecurityException e) {
                        Log.d(TAG, "Security Exception");
                    }
                } else {


                }
                return;
            }

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
