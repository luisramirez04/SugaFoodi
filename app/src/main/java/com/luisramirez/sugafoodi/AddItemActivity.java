package com.luisramirez.sugafoodi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class AddItemActivity extends AppCompatActivity {

    EditText nameEditText, restaurantEditText;
    RadioGroup typeRadioGroup;
    String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_item);


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

        String itemName = nameEditText.getText().toString();
        String restaurantName = restaurantEditText.getText().toString();

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

        Item newItem = new Item(itemName, restaurantName, type);

        Intent intent = new Intent(this, RestaurantsListActivity.class);
        startActivity(intent);
    }



}
