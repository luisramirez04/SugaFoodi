package com.luisramirez.sugafoodi;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


public class AddItemActivity extends AppCompatActivity {

    private final String TAG = "LOG:";

    FirebaseFirestore db;

    EditText nameEditText, restaurantEditText;
    RadioGroup typeRadioGroup;
    String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_item);

        // Access a Cloud Firestore instance from your Activity
        db = FirebaseFirestore.getInstance();

        Button submitButton = (Button) findViewById(R.id.buttonSubmit);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickSubmit(view);
            }
        });
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
        // Create a new item


        final DocumentReference newItemRef = db.collection("items").document();
        Item item = new Item(newItemRef.getId(), itemName, restaurantName, type);

        // Add a new document with a generated ID
        newItemRef.set(item);

        db.collection("restaurants")
                .whereEqualTo("name", restaurantName)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, task.getResult().toString());
                            QuerySnapshot documentSnapshot = task.getResult();
                            if (documentSnapshot.isEmpty()) {

                                DocumentReference newRestaurantRef = db.collection("restaurants").document();
                                Restaurant restaurant = new Restaurant(newRestaurantRef.getId(), restaurantName, newItemRef.getId());

                                // Add a new document with a generated ID
                                newRestaurantRef.set(restaurant);
                            } else {
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



        Intent intent = new Intent(this, RestaurantsListActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
