package com.codecool.grocerylist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.codecool.grocerylist.adapters.ListItemAdapter;
import com.codecool.grocerylist.model.GroceryListItem;
import com.codecool.grocerylist.services.DataAccess;
import com.codecool.grocerylist.services.authentication.AuthHandler;
import com.codecool.grocerylist.services.authentication.VolleyCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private ListItemAdapter adapter;
    private List<GroceryListItem> items = new ArrayList<>();
    private RecyclerView recyclerView;
    private TextView total;
    private DataAccess dao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.dao = new DataAccess("https://immense-peak-65799.herokuapp.com",getApplicationContext(),"filename");
        dao.getGroceryList(new VolleyCallback() {
            @Override
            public void onSuccessResponse(String result) {
                System.out.println(result);
                items.addAll(dao.parseJSONArray(result));
                adapter.notifyDataSetChanged();
            }
        });

        recyclerView = findViewById(R.id.groceryList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        /*items.add(new GroceryListItem("Milk",4,120));
        items.add(new GroceryListItem("Bread",1,150));
        items.add(new GroceryListItem("Coke",1,160));*/

        total = findViewById(R.id.total);

        adapter = new ListItemAdapter(this,items,total,dao);
        recyclerView.setAdapter(adapter);

        Button addItemButton = findViewById(R.id.add_item_button);

        addItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addItemIntent = new Intent(MainActivity.this,AddItemActivity.class);
                startActivityForResult(addItemIntent,1);
            }
        });




    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            if(resultCode == RESULT_OK) {
                String item = data.getStringExtra("item");
                int quantity = Integer.valueOf(data.getStringExtra("quantity"));
                int price = Integer.valueOf(data.getStringExtra("price"));

                GroceryListItem newItem = new GroceryListItem(item,quantity,price, dao.getDeviceID(),UUID.randomUUID().toString());


                items.add(newItem);
                total.setText(getTotalAsString() + " HUF");
                adapter.notifyDataSetChanged();
                dao.addToSQL(newItem);



            }
        }
    }

    private String getTotalAsString(){
        int total = 0;

        for(GroceryListItem item: items){
            total+= item.getPrice()*item.getQuantity();
        }

        return String.valueOf(total);
    }
}
