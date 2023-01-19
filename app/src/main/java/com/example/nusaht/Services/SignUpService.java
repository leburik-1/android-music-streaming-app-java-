package com.example.nusaht.Services;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Callable;

public class SignUpService implements Callable<String>
{
    private final String baseUrl;
    private final String taskName;
    private HttpURLConnection uc;
    private final String urlParams;
    private String response;

    public SignUpService(String baseUrl,String taskName,String urlParams)
    {
        this.baseUrl = baseUrl;
        this.taskName = taskName;
        this.urlParams = urlParams;
    }

    @Override
    public String call()
    {
        response = "";
       try
       {
           System.out.printf("%s running signup task ... : ",taskName);
           URL myUrl = new URL(baseUrl);
           uc = (HttpURLConnection) myUrl.openConnection();
           uc.setRequestMethod("POST");
           uc.setDoOutput(true);

           byte[] postData = urlParams.getBytes(StandardCharsets.UTF_8);
           int postDataLength = postData.length;
           uc.setRequestProperty("Content-Length", Integer.toString(postDataLength));
           uc.setRequestProperty("Accept", "application/json");
           uc.setUseCaches( false );

           try (DataOutputStream wr = new DataOutputStream(uc.getOutputStream()))
           {
               wr.write(postData);
           }
           catch (IOException ex)
           {
               ex.printStackTrace();
               return response = "error:network";
           }
           StringBuilder builder = new StringBuilder();
           try (BufferedReader reader = new BufferedReader(new InputStreamReader(uc.getInputStream())))
           {
               String line;
               while((line = reader.readLine()) != null)
               {
                   builder.append(line);
               }
               System.out.println("Response accepted ");
               response = builder.toString();
               System.out.print(builder.toString());
           }
           catch (IOException ex)
           {
               ex.printStackTrace();
           }
       }
       catch (Exception e)
        {
            e.printStackTrace();
       }
       return response;
    }
}
