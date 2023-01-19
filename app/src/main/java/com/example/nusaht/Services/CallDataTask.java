package com.example.nusaht.Services;

import com.example.nusaht.utils.InputValidator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.Callable;

public class CallDataTask implements Callable<String> {
    private final String baseUrl;
    private final String taskName;
    private HttpURLConnection uc;
    private static final String URL_PATTERN = "^(https?|ftp|file)://[A-z0-9+\\-&@#/%?=~_|!:,.;]*[A-z0-9+&\\-@#/%=~_|]";

    public CallDataTask(String baseUrl,String taskName)
    {
        if (!InputValidator.validateMe(URL_PATTERN,baseUrl))
            throw new IllegalArgumentException("Invalid Base URL");

        this.taskName = taskName;
        this.baseUrl = baseUrl;
    }

    @Override
    public String call()
    {
        String response = "";
        try
        {
            System.out.printf("%s running task : ",taskName);
            URL myUrl = new URL(baseUrl);
            uc = (HttpURLConnection) myUrl.openConnection();
            uc.setRequestMethod("GET");

            int responseCode = uc.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK)
            {
                StringBuilder builder = new StringBuilder();
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(uc.getInputStream())))
                {
                    String line;
                    while((line = reader.readLine()) != null)
                    {
                        builder.append(line);
                    }
                    System.out.println(builder.toString());
                    response = builder.toString();
                }
                catch (IOException ex)
                {
                    ex.printStackTrace();
                }
            }
        } catch (IOException ex){
            ex.printStackTrace();
        }
        return response;
    }

}
