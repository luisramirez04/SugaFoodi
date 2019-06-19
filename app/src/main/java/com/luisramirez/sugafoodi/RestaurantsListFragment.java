package com.luisramirez.sugafoodi;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQuery;
import com.firebase.geofire.GeoQueryDataEventListener;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class RestaurantsListFragment extends Fragment {

    private final String TAG = "LOG: ";
    final List<Restaurant> restaurants = new ArrayList<Restaurant>();
    final RestaurantListAdapter restaurantListAdapter = new RestaurantListAdapter(restaurants);
    private FusedLocationProviderClient fusedLocationProviderClient;
    EditText userLocationEditText;

    public RestaurantsListFragment() {
        // Required empty public constructor
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_restaurants_list, container, false);

        RecyclerView restaurantsListRecyclerView = (RecyclerView)
                (v.findViewById(R.id.restaurantslist_recyclerview));
        userLocationEditText = (EditText) v.findViewById(R.id.userLocation);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getContext());
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(getActivity(), new String[] {Manifest.permission.ACCESS_COARSE_LOCATION}, 1);


        } else {
            fusedLocationProviderClient.getLastLocation()
                    .addOnSuccessListener(new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {
                                Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
                                List<Address> addresses;
                                try {
                                    addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                                    userLocationEditText.setText(addresses.get(0).getPostalCode());
                                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("restaurantLocations");
                                    GeoFire geoFire = new GeoFire(ref);
                                    GeoQuery geoQuery = geoFire.queryAtLocation(new GeoLocation(location.getLatitude(), location.getLongitude()), 5);
                                    geoQuery.addGeoQueryDataEventListener(new GeoQueryDataEventListener() {
                                        @Override
                                        public void onDataEntered(DataSnapshot dataSnapshot, GeoLocation location) {
                                            String restid = dataSnapshot.getKey();
                                            FirebaseFirestore db = FirebaseFirestore.getInstance();
                                            DocumentReference restaurantsRef = db.collection("restaurants").document(restid);
                                            restaurantsRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                @Override
                                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                    Restaurant restaurant = documentSnapshot.toObject(Restaurant.class);
                                                    restaurants.add(restaurant);
                                                    restaurantListAdapter.notifyDataSetChanged();
                                                }
                                            });
                                        }

                                        @Override
                                        public void onDataExited(DataSnapshot dataSnapshot) {

                                        }

                                        @Override
                                        public void onDataMoved(DataSnapshot dataSnapshot, GeoLocation location) {

                                        }

                                        @Override
                                        public void onDataChanged(DataSnapshot dataSnapshot, GeoLocation location) {

                                        }

                                        @Override
                                        public void onGeoQueryReady() {

                                        }

                                        @Override
                                        public void onGeoQueryError(DatabaseError error) {

                                        }
                                    });

                                } catch (Exception e) {
                                    Log.d("Error: ", e.toString());
                                }

                            }
                        }
                    });
        }

        restaurantsListRecyclerView.setAdapter(restaurantListAdapter);
        restaurantListAdapter.setListener((RestaurantListAdapter.Listener)getActivity());

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        restaurantsListRecyclerView.setLayoutManager(mLayoutManager);

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        restaurantListAdapter.notifyDataSetChanged();
    }

}
