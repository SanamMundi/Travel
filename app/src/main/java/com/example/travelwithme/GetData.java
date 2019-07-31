package com.example.travelwithme;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetData {

    public String getData(String url)
    {
        HttpURLConnection c = null;
        try
        {
            URL u = new URL(url);
            c = (HttpURLConnection)u.openConnection();
            c.connect();
            int status = c.getResponseCode();
            switch(status)
            {
                case 200:
                case 201:
                    BufferedReader br = new BufferedReader(new InputStreamReader(c.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while((line = br.readLine())!=null)
                    {
                        sb.append(line+ "\n");
                    }
                    br.close();
                    return sb.toString();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            if(c!=null)
            {
                try
                {
                    c.disconnect();
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
        return null;

    }

}
