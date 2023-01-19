package com.example.nusaht.Services;

import java.io.*;
import java.net.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class SendRequest {
    private String url;
    private String requestMethod;
    private String params;
    private URL u;
    private HttpURLConnection uc;

    public SendRequest(String url, String requestMethod, String params)
    {
      this.url = url;
      this.requestMethod = requestMethod;
      this.params = params;


      setConnection();
    }

    public String setConnection() {
        try {
            u = new URL(this.url);
            uc = (HttpURLConnection) u.openConnection();

            // send POST request
            if (Objects.equals(this.url, "POST")) {
                uc.setRequestMethod("POST");
                uc.setDoOutput(true);
                byte[] postData = this.params.getBytes(StandardCharsets.UTF_8);
                int postDataLength = postData.length;
                uc.setRequestProperty("Content-Length", Integer.toString(postDataLength));
                uc.setRequestProperty("Accept", "application/json");
                try (DataOutputStream wr = new DataOutputStream(uc.getOutputStream())) {
                    wr.write(postData);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }

            StringBuilder builder = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(uc.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                }
                System.out.print(builder.toString());
                return builder.toString();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return "error";
    }
}
