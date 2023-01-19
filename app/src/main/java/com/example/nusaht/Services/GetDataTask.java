package com.example.nusaht.Services;
import java.io.*;
import java.net.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class GetDataTask implements Runnable {
    private final String baseUrl;
    private final String taskName;
    private HttpURLConnection uc;

    public GetDataTask(String baseUrl,String taskName)
    {
        this.taskName = taskName;
        this.baseUrl = baseUrl;
    }

    public void run()
    {
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

                    System.out.print(builder.toString());
                }
                catch (IOException ex)
                {
                    ex.printStackTrace();
                }
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
