package com.codecool.grocerylist.services.authentication;

import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class AuthFileHandler {

    private String filename;
    private Context context;

    AuthFileHandler(String filename, Context context){
        this.filename = filename;
        this.context = context;
    }

     boolean checkIfDeviceAuthenticated(){

        try {
            context.openFileInput(filename);
            return true;
        } catch (FileNotFoundException e) {
            return false;
        }
    }


    String readDeviceID(){
        File directory = context.getFilesDir();
        File file = new File(directory, filename);
        String deviceID = "";
        try {
            Scanner sc = new Scanner(file);

            while (sc.hasNextLine())
                deviceID += sc.nextLine();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return deviceID;
    }

    void writeAuthToFile(String token){
        FileOutputStream outputStream;
        try {
            outputStream = context.openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(token.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void clearFile(){
        context.deleteFile(filename);
    }
}
