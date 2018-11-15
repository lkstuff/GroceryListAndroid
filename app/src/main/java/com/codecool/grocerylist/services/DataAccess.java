package com.codecool.grocerylist.services;

import android.content.Context;

import com.codecool.grocerylist.model.GroceryListItem;
import com.codecool.grocerylist.services.authentication.AuthHandler;
import com.codecool.grocerylist.services.authentication.VolleyCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DataAccess {

    private AuthHandler authHandler;
    private Requester requester;

    public DataAccess(String serverURL, Context context, String filename){
        this.authHandler = new AuthHandler(filename,context);
        this.requester = new Requester(serverURL,context);
    }

    public String getDeviceID(){
        return authHandler.getDeviceID();
    }

    public void getGroceryList(final VolleyCallback callback){
        String deviceID = authHandler.getDeviceID();
        requester.requestGroceryList(deviceID, new VolleyCallback() {
            @Override
            public void onSuccessResponse(String result) {
                callback.onSuccessResponse(result);
            }
        });
    }

    public void addToSQL(GroceryListItem item){
        requester.addToSQL(item,authHandler.getDeviceID());
    }

    public void deleteFromSQL(GroceryListItem item){
        requester.deleteFromSQL(item);
    }

    public void updateInSQL(GroceryListItem item){
        requester.updateInSQL(item);
    }

    public List<GroceryListItem> parseJSONArray(String arrayString){
        List<GroceryListItem> list = new ArrayList<>();
        try {
            JSONArray arr = new JSONArray(arrayString);

            for(int i = 0; i < arr.length();i++){
                JSONObject temp = arr.getJSONObject(i);
                String name = temp.getString("name");
                String id = temp.getString("id");
                String deviceID = temp.getString("deviceID");
                int price = temp.getInt("price");
                int quantity = temp.getInt("quantity");
                GroceryListItem tempItem = new GroceryListItem(name,quantity,price,deviceID,id);
                list.add(tempItem);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return list;


    }




}
