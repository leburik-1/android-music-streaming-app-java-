//package com.example.nusaht.webservice;
//
//import android.content.Context;
//import android.content.SharedPreferences;
//import android.preference.PreferenceManager;
//
//import com.example.nusaht.landing;
//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
//
//import java.io.IOException;
//import okhttp3.Interceptor;
//import okhttp3.OkHttpClient;
//import okhttp3.Request;
//import okhttp3.Response;
//import okhttp3.logging.HttpLoggingInterceptor;
//import retrofit2.Retrofit;
//import retrofit2.converter.gson.GsonConverterFactory;
//
//public class ServiceGenerator {
//    private static Retrofit retrofit = null;
//    private static Gson gson = new GsonBuilder().create();
//    public Context serviceAppContext = landing.getAppContext();
//    public SharedPreferences prefs;
//    public static String PREF_NAME = "NUSAH_SEC";
//
//    private static HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
//    private static OkHttpClient.Builder okhttpClientBuilder = new OkHttpClient.Builder()
//            .addInterceptor(httpLoggingInterceptor)
//            .addInterceptor(new Interceptor() {
//                @Override
//                public Response intercept(Interceptor.Chain chain) throws IOException {
//                    Request newRequest = chain.request().newBuilder()
//                            .addHeader("Authorization","Bearer " + this.getToken()).build();
//                    return chain.proceed(newRequest);
//                }
//            }).build();
//
//    private static OkHttpClient okHttpClient = okhttpClientBuilder.build();
//
//    public static<T> T createService(Class<T> serviceClass)
//    {
//        if (retrofit == null) {
//            retrofit = new Retrofit.Builder()
//                    .client(okHttpClient)
//                    .baseUrl("BASE_URL")
//                    .addConverterFactory(GsonConverterFactory.create(gson))
//                    .build();
//        }
//        return retrofit.create(serviceClass);
//    }
//    public String getToken()
//    {
//        prefs = serviceAppContext.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
//        //prefs = PreferenceManager.getDefaultSharedPreferences(serviceAppContext);
//        String token = prefs.getString("access",null);
//        return token;
//    }
//}
////Request newRequest  = chain.request().newBuilder()
////            .addHeader("Authorization", "Bearer " + token)
////            .build();
////        return chain.proceed(newRequest);
////
////                Retrofit retrofit = new Retrofit.Builder()
////                .client(client)
////                .baseUrl(/** your url **/)
////                .addConverterFactory(GsonConverterFactory.create())
////                .build();
//
