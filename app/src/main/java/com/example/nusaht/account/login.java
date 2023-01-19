package com.example.nusaht.account;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKey;
import androidx.security.crypto.MasterKeys;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;


import com.example.nusaht.pages.LandAct;
import com.example.nusaht.R;
import com.example.nusaht.Services.SignUpService;
import com.example.nusaht.utils.InputValidator;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONObject;

//import com.example.nusaht.models.LoginModel;
//import com.example.nusaht.models.LoginResponse;
//import okhttp3.Request;
//import okhttp3.ResponseBody;
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
public class login extends Fragment
{
    private TextInputEditText username,password;
    private TextInputLayout pwd_tinput_l;
    private MaterialButton login_l,cancel_button_l;
    private String data,passwordData,usernameData,usernamePre,accessToken,refreshToken;
    private InputValidator validator;
    private final String TAG = "LOGIN";
    private final String BASE_URL = "http://10.0.2.2:8000/";
    private final String TAG_LOGIN = "LOGIN TASK RESULT : \n";
    private static final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$";
    private final String PASSWORD_ERROR = "\nPassword must contain at least one digit [0-9]." +
            "\nPassword must contain at least one lowercase Latin character [a-z]." +
            "\nPassword must contain at least one uppercase Latin character [A-Z]." +
            "\nPassword must contain at least one special character like ! @ # & ( )." +
            "\nPassword must contain a length of at least 8 characters and a maximum of 20 characters.";
    private final String passwordErrorMessage = "Password is not correct";
    private final String SHARE_PREF_NAME = "NUSAH_SEC";
    public SharedPreferences preference;
    private SharedPreferences.Editor preferenceEditor;
    View view;
    //private LoginRepo logingRepo;


    public login()
    {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getParentFragmentManager().setFragmentResultListener("ksups", this,
                new FragmentResultListener() {
                    @Override
                    public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result)
                    {
                        System.out.println("Testing fragment manager on login object");
                        String bundleResult = result.getString("sups");
                        System.out.println(bundleResult);
                        if (bundleResult.contains("t."))
                        {
                            usernamePre = bundleResult.substring(bundleResult.indexOf(".") + 1);
                            String toastMessage = "Successfully registered " + usernamePre;
                            Toast toast = Toast.makeText(getActivity().getApplicationContext(),
                                    toastMessage,
                                    Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    }
                });
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,@NonNull ViewGroup container,@NonNull Bundle savedInstanceState)
    {
        super.onCreateView(inflater, container, savedInstanceState);
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_login, container, false);
        username = (TextInputEditText) view.findViewById(R.id.username_l);
        password = (TextInputEditText) view.findViewById(R.id.pwd_l);
        login_l = view.findViewById(R.id.login_l);
        cancel_button_l = view.findViewById(R.id.cancel_button_l);
        pwd_tinput_l = view.findViewById(R.id.pwd_tinput_l);

        login_l.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                pwd_tinput_l.setError(null);
                boolean retval = true;
                usernameData = username.getText().toString();
                passwordData = password.getText().toString();
                if (!passwordData.matches(signup.PASSWORD_PATTERN))
                {
                    Log.e(TAG,"Password match error (regexp)");
                    pwd_tinput_l.setError(passwordErrorMessage);
                }
                if (usernameData.length() >= 1)
                {
                    String param = "username="+usernameData+"&"+"password="+passwordData;

                    try {
                        loginUser(param);
                    } catch (ExecutionException | InterruptedException | GeneralSecurityException | IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        cancel_button_l.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                NavController navController = Navigation.findNavController(view);
                navController.navigate(R.id.action_login_to_landing);
            }
        });
        return view;
    }

    public void validate(InputValidator validator)
    {
        if (!validator.isPasswordValid(password.getText().toString()))
        {
            pwd_tinput_l.setError(null);
        }
    }

    public void startAct()
    {
        Intent intent = new Intent(getActivity(), LandAct.class);
        startActivity(intent);
        getActivity().finish();
    }

    public void loginUser(String loginParam) throws ExecutionException, InterruptedException, GeneralSecurityException, IOException
    {
        Callable<String> service = new SignUpService(BASE_URL+"account/login/token/","LOGIN TASK",loginParam);
        ExecutorService es = Executors.newCachedThreadPool();
        Future<String> future = es.submit(service);
        String result = future.get();

        if (result.contains("error") && result.contains("network"))
        {
             pwd_tinput_l.setError("Network Connection Error.Please try again.");
        }
        else if (result.contains("refresh"))
        {
             Log.i(TAG_LOGIN, "result");
             System.out.printf("\nResponse result from login request ---- %s",result);
             try
             {
                 JSONObject jsonObject = new JSONObject(result);
                 accessToken = jsonObject.getString("refresh");
                 refreshToken = jsonObject.getString("access");
                 System.out.printf("\n JSon String refresh - %s",jsonObject.getString("refresh"));
                 System.out.printf("\n JSon String access   - %s",jsonObject.getString("access"));
                 saveDataSharedPref(accessToken,refreshToken);
             } catch(Exception ex)
             {
                 ex.printStackTrace();
                 Log.i("JSONEx","Exception stack trace");
                 pwd_tinput_l.setError("Error in saving user credentials.Please try again.");
             }
             checkPreference();
             startAct();
        } else
        {
             pwd_tinput_l.setError("Invalid Login.Please try again.");
        }
    }

    public void createEncryptedPreference() throws GeneralSecurityException, IOException
    {
        //String masterKey =  MasterKey.Builder
//        String mk = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC);
        MasterKey ms = new MasterKey.Builder(getActivity().getApplicationContext()).setKeyScheme(MasterKey.KeyScheme.AES256_GCM).build();
        MasterKey masterKey = new MasterKey.Builder(getActivity().getApplicationContext(), MasterKey.DEFAULT_MASTER_KEY_ALIAS)
                .setKeyScheme(MasterKey.KeyScheme.AES256_GCM).build();
        preference = (EncryptedSharedPreferences) EncryptedSharedPreferences.create(getActivity().getApplicationContext(), SHARE_PREF_NAME, masterKey,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM);
    }

    public void saveDataSharedPref(String access,String refresh)
    {
        preference = getContext().getSharedPreferences(SHARE_PREF_NAME,0);
        SharedPreferences.Editor preferenceEditor = preference.edit();
        preferenceEditor.putString("name",usernameData);
        preferenceEditor.putString("access",access);
        preferenceEditor.putString("refresh",refresh);
        preferenceEditor.putBoolean("lg",true);
        boolean isUserSaved = preferenceEditor.commit();
        System.out.printf("\nSaved boolean ----%s",String.valueOf(isUserSaved));
        Log.i(TAG_LOGIN,"Token saved");
    }

    public void saveSharedPreference(String access,String refresh) throws GeneralSecurityException, IOException
    {
        createEncryptedPreference();
        preferenceEditor = preference.edit();
        preferenceEditor.putString("name",usernameData);
        preferenceEditor.putString("access",access);
        preferenceEditor.putString("refresh",refresh);
        preferenceEditor.putBoolean("lg",true);

        boolean isUserSaved = preferenceEditor.commit();
        System.out.printf("\nSaved boolean ----%s",String.valueOf(isUserSaved));
        Log.i(TAG_LOGIN,"Token saved");
    }

    public void checkPreference()
    {
        SharedPreferences pre = getContext().getSharedPreferences(SHARE_PREF_NAME,0);
        Log.i("PREFE",pre.getString("access", pre.getString("access","empty value")));
        Log.i("PREFE", pre.getString("refresh",pre.getString("refresh","empty value")));
    }

    public String getToken()
    {
        return preference.getString("token","n");
    }

    public String getName()
    {
        return preference.getString("name", "n");
    }


//    @Override
//    public void onViewCreated(View view,Bundle savedInstanceState)
//    {
//        super.onViewCreated(view,savedInstanceState);
//        NavController navController = Navigation.findNavController(view);
////        username = (EditText) view.findViewById(R.id.username_l);
////        password = (EditText) view.findViewById(R.id.pwd_l);
////        login_l = view.findViewById(R.id.login_l);
//        System.out.printf("Username -- %s\nPassword -- %s",username.getText().toString(),password.getText().toString());
//        String data = "username="+username.getText().toString()+"&password="+password.getText().toString();
//
//        System.out.printf("\nUser Data -- %s",data);
////        GetDataTask task1 = new GetDataTask(BASE_URL+"audio/name/ilo/","task1");
////        PostDataTask task2 = new PostDataTask(BASE_URL + "account/login/token/","tasl2",data);
////        ExecutorService es = Executors.newCachedThreadPool();
//        //logingRepo = logingRepo.getInstance();
//
//        login_l.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                //es.execute(task2);
//              //  es.shutdown();
////                logingRepo.getLoginService().loginUser(username.getText().toString(),password.getText().toString()).enqueue(new Callback<LoginResponse>() {
////
////                    @Override
////                    public void onResponse(@NonNull Call<LoginResponse> call,@NonNull Response<LoginResponse> response)
////                    {
////
////                        if (response.isSuccessful()) {
////                            if (response.body() != null) {
////                                System.out.print("sssssssssssssssssssssssssssssssssss");
////                                LoginResponse res = response.body();
////                                System.out.print("Access" + " --------  " + res.getAccess());
////                               }
////                            }
////
////                    }
////
////                    @Override
////                    public void onFailure(Call<LoginResponse> call, Throwable t)
////                    {
////                        t.printStackTrace();
////                          System.out.print(t.getMessage());
////                    }
////                });
//                //navController.navigate(R.id.action_landing_to_login);
//            }
//        });
//    }


}