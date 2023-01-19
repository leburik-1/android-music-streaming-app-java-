package com.example.nusaht.webservice;

import com.example.nusaht.models.AudioModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APIs {
    String BASE_URL = "http://10.0.2.2:8000/audio/mood/";

    @GET("relax/")
    Call<List<AudioModel>> getAudios();
}
