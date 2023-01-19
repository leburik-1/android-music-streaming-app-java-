package com.example.nusaht.pages;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.nusaht.R;
import com.example.nusaht.databinding.ActLandBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class LandAct extends AppCompatActivity{

//    private ActLandBinding binding;
    private BottomNavigationView navc_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_land);
        navc_view = findViewById(R.id.navc_view);
        navc_view.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id = item.getItemId();

//                if (id == R.id.navigation_home){
//                   loadFragment(new Home(),false);
//                } else
                if (id == R.id.navigation_search) {
                    item.setChecked(true);
                    loadFragment(new Search(), false);
                } else if (id == R.id.navigation_library) {
                    item.setChecked(true);
                    loadFragment(new Library(), false);
                } else if (id == R.id.navigation_VR) {
                    item.setChecked(true);
                    loadFragment(new NushahVR(), false);
                } else {
                    item.setChecked(true);
                    loadFragment(new Home(), true);
                }
                return false;
            }
        });

        if (savedInstanceState == null) {
            navc_view.setSelectedItemId(R.id.navigation_home);
        }
        // navc_view.setSelectedItemId(R.id.navigation_home);
//        binding = ActLandBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());
        //BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
//        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.navigation_home, R.id.navigation_library, R.id.navigation_search,R.id.navigation_VR)
//                .build();
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main2);
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
//        NavigationUI.setupWithNavController(navView, navController);
    }//                if (id == R.id.navigation_home){
//                   loadFragment(new Home(),false);
//                } else

    public void loadFragment(Fragment frag,boolean flag)
    {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ts = fm.beginTransaction();

        if (flag)
           ts.add(R.id.fragment_container,frag);
        else
           ts.replace(R.id.fragment_container,frag);
        ts.commit();
    }

}