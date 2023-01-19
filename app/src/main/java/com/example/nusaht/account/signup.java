package com.example.nusaht.account;

import static android.app.Activity.RESULT_OK;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.nusaht.R;
import com.example.nusaht.Services.SignUpService;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


public class signup extends Fragment {
    private View view;
    private static ArrayList<String> patterns;
    public static final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$";
    public static final String EMAIL_PATTERN = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-z]{2,6}$";
    private MaterialButton signup_btn_s,reset_btn_s;
    private TextInputEditText username,age,password,password2,email;
    private final String TASK_NAME = "SIGN UP SERVICE";
    private String profileImage;
    private static final String TAG = "Sign up";
    private static final String BASE_URL = "http://10.0.2.2:8000/";
    private TextInputLayout username_inputlayout,email_inputlayout,age_inputlayout,pwd_inputlayout,pwd2_inputlayout;
    private final int INPUT_FIELDS_NUM = 5;
    private final String PASSWORD_ERROR = "\nPassword must contain at least one digit [0-9]." +
            "\nPassword must contain at least one lowercase Latin character [a-z]." +
            "\nPassword must contain at least one uppercase Latin character [A-Z]." +
            "\nPassword must contain at least one special character like ! @ # & ( )." +
            "\nPassword must contain a length of at least 8 characters and a maximum of 20 characters.";
    private Hashtable<String,String> fieldValueMap;

//    private EditText error_s;

    public signup()
    {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_signup, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view,Bundle savedInstanceState)
    {
        super.onViewCreated(view,savedInstanceState);
        NavController navController = Navigation.findNavController(view);

        // initialize input fields
        initInput();

        signup_btn_s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("Sign up button clicked");
                initInput();
                if (!validateUserInput())
                {
                    System.out.println("Invalid user input ");
                }
                else
                {
                    Enumeration<String> keys = fieldValueMap.keys();
                    String fieldName;
                    String value;
                    // use string buffer over builder because it is thread safe
                    StringBuffer params = new StringBuffer();
                    while(keys.hasMoreElements())
                    {
                        fieldName = (String) keys.nextElement();
                        value = fieldValueMap.get(fieldName);
                        params.append(fieldName).append("=").append(value).append("&");
                    }
                    params.deleteCharAt(params.toString().length()-1);
                    try
                    {
                        System.out.printf("\nUser Input : %s\n",params.toString());
                        String res = signUp(params);
                        NavController navcontroller = Navigation.findNavController(view);
                        if (res.contains("success"))
                        {
                            Log.i(TAG, "sign up successful");
                            Bundle signupBundle = new Bundle();
                            signupBundle.putString("sups","t."+fieldValueMap.get("username"));
                            getParentFragmentManager().setFragmentResult("ksups",signupBundle);
                            navcontroller.navigate(R.id.action_signup_to_login);
                        }
                    } catch (ExecutionException | InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
                //navController.navigate(R.id.action_landing_to_login);
        });
//        uploadavatar.setOnClickListener(new View.OnClickListener() {
//           @Override
//           public void onClick(View view) {
//                String uploadFilePath = Environment.getExternalStorageState();
//                Intent intent = new Intent(Intent.ACTION_PICK);
//                intent.setType("image/*");
//               galleryActivityResultLauncher.launch(intent);
//           }
//        });

//         upload image from camera
//        uploadFromCamera.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                dispatchTakePictureIntent();
//            }
//        });

        reset_btn_s.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                System.out.println("Resseting signup fields.");
                resetForm();
            }
        });
    }

    public String signUp(StringBuffer urlparam) throws ExecutionException, InterruptedException
    {
        Callable<String> serviceSignUp = new SignUpService(BASE_URL+"account/signup/", TASK_NAME,urlparam.toString());
        ExecutorService es = Executors.newCachedThreadPool();
        Future<String> future = es.submit(serviceSignUp);
        return future.get();
    }

    public void initInput()
    {
        System.out.println("Initializing user input");
        // buttons
        signup_btn_s = view.findViewById(R.id.signup_btn_s);
        reset_btn_s = view.findViewById(R.id.reset_btn_s);
//        uploadavatar = view.findViewById(R.id.uploadavatar);
//        uploadFromCamera = view.findViewById(R.id.uploadFromCamera);

        username =  (TextInputEditText) getView().findViewById(R.id.username_s);
        email = (TextInputEditText) getView().findViewById(R.id.email_s);
        age = (TextInputEditText) getView().findViewById(R.id.age_s);
        password = (TextInputEditText) getView().findViewById(R.id.pwd_1);
        password2 = (TextInputEditText) getView().findViewById(R.id.pwd_2);

        username_inputlayout = view.findViewById(R.id.username_inputlayout);
        email_inputlayout = view.findViewById(R.id.email_inputlayout);
        age_inputlayout = view.findViewById(R.id.age_inputlayout);
        pwd2_inputlayout = view.findViewById(R.id.pwd2_inputlayout);
        pwd_inputlayout = view.findViewById(R.id.pwd_inputlayout);
    }

    public boolean validateUserInput()
    {
        System.out.println("Validating user input");
        boolean retval = true;

        // use hash table cuz it is synchronized
        fieldValueMap = new Hashtable<String,String>();
        fieldValueMap.put("username",username.getText().toString());
        fieldValueMap.put("email",email.getText().toString());
        fieldValueMap.put("pwd1",password.getText().toString());
        fieldValueMap.put("age",age.getText().toString());
        fieldValueMap.put("pwd2",password2.getText().toString());

        Enumeration<String> keys = fieldValueMap.keys();
        String fieldName;
        String value;

        while(keys.hasMoreElements())
        {
            fieldName = (String) keys.nextElement();
            value = fieldValueMap.get(fieldName);

                switch (fieldName) {
                    case "username":
                        if ((value == null || value.length() == 0)) {
                            retval = false;
                            username_inputlayout.setError("Invalid username.");
                        }
                        break;
                    case "email":
                        if (!value.matches(EMAIL_PATTERN)){
                            retval = false;
                            email_inputlayout.setError("Invalid email");
                        }
                        break;
                    case "age":
                        try{
                         int age = Integer.parseInt(value);
                        } catch (NumberFormatException ex)
                        {
                            retval = false;
                            age_inputlayout.setError("Invalid age number");
                            System.out.println("Invalid age.");
                        }
                        break;
                    case "pwd1":
                        if (!value.equals(fieldValueMap.get("pwd2")))
                        {
                            retval = false;
                          pwd_inputlayout.setError("passwords don't match.");
                       } else if (!value.matches(PASSWORD_PATTERN)){
                            retval = false;
                            pwd_inputlayout.setError(PASSWORD_ERROR);
                        }
                        break;
                }
        }
       return retval;
    }

    public void resetForm()
    {
        username_inputlayout.setError(null);
        pwd_inputlayout.setError(null);
        age_inputlayout.setError(null);
        email_inputlayout.setError(null);

        username.setText("");
        password.setText("");
        password2.setText("");
        email.setText("");
        age.setText("");
    }
}

//    private void dispatchTakePictureIntent()
//    {
//      Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//      if (takePicture.resolveActivity(getActivity().getPackageManager()) != null) {
//
//      }
//    }

//    private ActivityResultLauncher<Intent> galleryActivityResultLauncher = registerForActivityResult(
//            new ActivityResultContracts.StartActivityForResult(),
//            new ActivityResultCallback<ActivityResult>() {
//                @Override
//                public void onActivityResult(ActivityResult result) {
//                    if (result.getResultCode() == Activity.RESULT_OK) {
//                        Intent data = result.getData();
//                        Uri imageUri = data.getData();
//                        System.out.println(imageUri);
//                        //profileIv.setImageURI(imageUri);
//                    }
//                }
//            }
//    );