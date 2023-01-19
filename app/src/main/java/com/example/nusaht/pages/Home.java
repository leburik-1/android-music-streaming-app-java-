package com.example.nusaht.pages;

import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.nusaht.R;
import com.example.nusaht.adapter.AudioAdapter;
import com.example.nusaht.models.AudioModel;
import com.example.nusaht.webservice.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Home extends Fragment {
    private View view;
    private AudioAdapter rvAdapter;
    RecyclerView rvAudios;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home2, container, false);
        rvAudios = (RecyclerView) view.findViewById(R.id.rvAudios);

        getAudios();

        // Inflate the layout for this fragment
        return view;
    }

    private void getAudios()
    {
       Call<List<AudioModel>> apicall = RetrofitClient.getInstance().getApis().getAudios();
       apicall.enqueue(new Callback<List<AudioModel>>() {
           @Override
           public void onResponse(Call<List<AudioModel>> call, Response<List<AudioModel>> response) {
               List<AudioModel> audioModels = response.body();
               System.out.println("RRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR");
               System.out.println(audioModels.size());
               Toast.makeText(getActivity(),"Got audios",Toast.LENGTH_SHORT).show();
               //setAdapter(audioModels);
           }

           @Override
           public void onFailure(Call<List<AudioModel>> call, Throwable t)
           {
               System.out.println("LLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLL");

               Toast.makeText(getActivity(),"Error",Toast.LENGTH_SHORT).show();
           }
       });
    }

    private void setAdapter(List<AudioModel> audioM)
    {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        //rvAudios.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvAudios.setLayoutManager(linearLayoutManager);
        rvAdapter = new AudioAdapter(getContext(),audioM);
        rvAudios.setAdapter(rvAdapter);
    }
}