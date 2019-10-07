package com.example.homework3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.example.homework3.util.SharedPreferenceUtil;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class LoginActivity extends AppCompatActivity {

    private EditText loginId;
    private EditText loginPassword;
    private Button loginButton;

    public String TAG = "LoginError";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
        setListener();
    }

    void init() {
        loginId = findViewById(R.id.loginId);
        loginPassword = findViewById(R.id.loginPassword);
        loginButton = findViewById(R.id.loginButton);

        SharedPreferenceUtil.init(this);

        String token = SharedPreferenceUtil.getString("token");
        if(!token.equals("")){
            startActivity(new Intent(this, Main2Activity.class));
        }
    }

    void setListener() {
        loginButton.setOnClickListener(v -> {
            UserService userService = RetrofitUtil.retrofit.create(UserService.class);
            userService.signIn(new User(loginId.getText().toString(), loginPassword.getText().toString()))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(LoginResponse -> {
                        String token = LoginResponse.getAuth().getToken();
                        SharedPreferenceUtil.setStringValue("token", token);
                        navigateToMain();
                    }, t -> {
                        Log.e(TAG, "error type : "+t.getLocalizedMessage());
                    });
        });
    }

    void navigateToMain(){
        Intent intent = new Intent(this, Main2Activity.class);
        startActivity(intent);
    }
}
