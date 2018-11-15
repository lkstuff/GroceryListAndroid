package com.codecool.grocerylist.services.authentication;

import android.content.Context;

import java.util.UUID;

public class AuthHandler {


    private AuthFileHandler authFileHandler;

    private String deviceID = null;


    public AuthHandler(String authFileName, Context context){
        this.authFileHandler = new AuthFileHandler(authFileName,context);
    }

    public String getDeviceID(){
        String deviceID;
        if(authFileHandler.checkIfDeviceAuthenticated()){
           deviceID = authFileHandler.readDeviceID();
        }else{
           deviceID = UUID.randomUUID().toString();
           authFileHandler.writeAuthToFile(deviceID);
        }

        return deviceID;


    }



}
