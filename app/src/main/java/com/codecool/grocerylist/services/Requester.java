package com.codecool.grocerylist.services;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.codecool.grocerylist.model.GroceryListItem;
import com.codecool.grocerylist.services.authentication.VolleyCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Requester {

    private String serverURL;
    private Context context;

    public Requester(String serverURL, Context context){
        this.serverURL = serverURL;
        this.context = context;
    }

    public void requestGroceryList(String deviceID, final VolleyCallback callback){
        String url = serverURL + "/getgrocery/"+ deviceID;
        RequestQueue queue = Volley.newRequestQueue(context);

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, new JSONArray(), new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                callback.onSuccessResponse(response.toString());

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        queue.add(request);
    }

    public void addToSQL(GroceryListItem item, String deviceID){
        String url = serverURL + "/add";
        JSONObject itemJson = new JSONObject();
        try {
            itemJson.put("name", item.getName());
            itemJson.put("price", String.valueOf(item.getPrice()));
            itemJson.put("quantity", String.valueOf(item.getQuantity()));
            itemJson.put("id", String.valueOf(item.getId()));
            itemJson.put("deviceID", deviceID);
        }catch (JSONException e){
            e.printStackTrace();
        }
        RequestQueue queue = Volley.newRequestQueue(context);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, itemJson, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(request);
    }

    public void deleteFromSQL(GroceryListItem item){
        String url = serverURL + "/grocery/" + item.getId();
        RequestQueue queue = Volley.newRequestQueue(context);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.DELETE, url, new JSONObject(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(request);
    }

    public void updateInSQL(GroceryListItem item){
        String url = serverURL + "/grocery";
        JSONObject itemJson = new JSONObject();
        try {
            itemJson.put("name", item.getName());
            itemJson.put("price", String.valueOf(item.getPrice()));
            itemJson.put("quantity", String.valueOf(item.getQuantity()));
            itemJson.put("id", String.valueOf(item.getId()));
            itemJson.put("deviceID", item.getDeviceID());
        }catch (JSONException e){
            e.printStackTrace();
        }

        RequestQueue queue = Volley.newRequestQueue(context);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, url, itemJson, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(request);
    }



}
