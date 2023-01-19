package com.example.nusaht.Services;
import java.io.*;
import java.net.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class PostDataTask implements Runnable{
    private final String baseUrl;
    private final String taskName;
    private HttpURLConnection uc;
    private final String urlParams;

    public PostDataTask(String baseUrl,String taskName,String urlParams)
    {
        this.baseUrl = baseUrl;
        this.taskName = taskName;
        this.urlParams = urlParams;
    }

    public void run()
    {
        try
        {
            System.out.printf("%s running task : ",taskName);
            URL myUrl = new URL(baseUrl);
            uc = (HttpURLConnection) myUrl.openConnection();
            uc.setRequestMethod("POST");
            uc.setDoOutput(true);

            uc.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
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
    }
}
