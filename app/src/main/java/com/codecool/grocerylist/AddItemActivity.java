package com.codecool.grocerylist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddItemActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_item_layout);

        Button doneButton = findViewById(R.id.doneButton);

        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent returnIntent = getIntent();

                EditText item = findViewById(R.id.itemInput);
                String itemString = item.getText().toString();

                EditText quantity = findViewById(R.id.quantityInput);
                String quantityString = quantity.getText().toString();

                EditText price = findViewById(R.id.priceInput);
                String priceString = price.getText().toString();


                if(itemString.equals("")){
                    Toast.makeText(AddItemActivity.this,"Please provide an item name", Toast.LENGTH_SHORT).show();
                }else if(quantityString.equals("")){
                    Toast.makeText(AddItemActivity.this,"Please provide a quantity", Toast.LENGTH_SHORT).show();
                }else if(priceString.equals("")){
                    Toast.makeText(AddItemActivity.this,"Please provide a price", Toast.LENGTH_SHORT).show();
                }else{
                    try{

                        int testprice = Integer.valueOf(priceString);
                        int testquantity = Integer.valueOf(quantityString);

                        if(testprice < 1 || testquantity < 1){
                            throw new NumberFormatException();
                        }

                        returnIntent.putExtra("item",itemString);
                        returnIntent.putExtra("quantity",quantityString);
                        returnIntent.putExtra("price",priceString);

                        setResult(RESULT_OK,returnIntent);

                        finish();
                    }catch (NumberFormatException e){
                        Toast.makeText(AddItemActivity.this,"Not valid Integer", Toast.LENGTH_SHORT).show();
                    }

                }



            }
        });


    }
}
