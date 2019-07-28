package com.example.travelwithme;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class GetNearby extends AsyncTask<String, String, String> {
    private getNearbyListener gnListener;

    public GetNearby(getNearbyListener gnListener){
        this.gnListener = gnListener;
    }


    @Override
    protected String doInBackground(String... strings) {

        try {
            URL url = new URL(strings[0]);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoInput(true);
            urlConnection.setRequestProperty("Content-Type", "application/json");

            int statusCode = urlConnection.getResponseCode();

            if(statusCode==200){
                InputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                StringBuilder sb = new StringBuilder();
                String line;
                try{
                    while((line=bufferedReader.readLine())!= null){
                        sb.append(line);
                    }
                }catch(IOException e){
                    Log.d("Data error", e.getMessage());
                }
                return sb.toString();
            }
            else{
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    protected void onPostExecute(String s) {
        gnListener.getResult(s);
    }

    interface getNearbyListener{
        ArrayList getResult(String jsonData);
    }
}
