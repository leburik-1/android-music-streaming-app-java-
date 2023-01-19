package com.example.nusaht.webservice;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
        private static RetrofitClient instance = null;
        private APIs apis;

        private RetrofitClient()
        {
                HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
                interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

            Retrofit retrofit = new Retrofit.Builder().baseUrl(APIs.BASE_URL).
                    addConverterFactory(GsonConverterFactory.create()).
                    client(client).build();
            apis = retrofit.create(APIs.class);
        }

        public static synchronized RetrofitClient getInstance() {
                if (instance == null) {
                        instance = new RetrofitClient();
                }
                return instance;
        }

        public APIs getApis()
        {
                return apis;
        }

}
