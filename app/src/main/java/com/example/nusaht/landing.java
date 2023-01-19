package com.example.nusaht;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.nusaht.pages.LandAct;
import com.google.android.material.button.MaterialButton;

public class landing extends Fragment {
    private View view;
    private MaterialButton login_btn_l;
    private MaterialButton signup_btn_l;
   // public static Context appContext;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // appContext = getActivity().getApplicationContext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_landing, container, false);
        return view;
    }

//    public static Context getAppContext()
//    {
//        return appContext;
//    }

    @Override
    public void onViewCreated(View view,Bundle savedInstanceState)
    {
        super.onViewCreated(view,savedInstanceState);

        NavController navController = Navigation.findNavController(view);

        login_btn_l = view.findViewById(R.id.login_btn_l);
        signup_btn_l = view.findViewById(R.id.signup_btn_l);

        login_btn_l.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                //methodTest();
                   navController.navigate(R.id.action_landing_to_login);
            }
        });

        signup_btn_l.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_landing_to_signup);
            }
        });
    }

    public void methodTest()
    {
        Intent intent = new Intent(getActivity(), LandAct.class);
        startActivity(intent);
        getActivity().finish();
    }
}