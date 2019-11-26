package com.example.homework3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.example.homework3.databinding.ActivityLoginBinding;
import com.example.homework3.util.SharedPreferenceUtil;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class LoginActivity extends AppCompatActivity {



    public String TAG = "LoginError";

    private LoginViewModel mViewModel;
    private ActivityLoginBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        observeViewModel();
    }

    void init() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);

        SharedPreferenceUtil.init(this);
        mViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        mBinding.setViewModel(mViewModel);

        String token = SharedPreferenceUtil.getString("token");
        if(!token.equals("")){
            startActivity(new Intent(this, Main2Activity.class));
        }
    }

    private void observeViewModel() {
        mViewModel.loginFinishCallBack.observe(this, aVoid -> navigateToMain());
        mViewModel.onLoginClicked.observe(this, aVoid -> {
            String email = mBinding.loginId.getText().toString();
            String password = mBinding.loginPassword.getText().toString();
            mViewModel.Login(email, password);
        });
    }



    void navigateToMain(){
        Intent intent = new Intent(this, Main2Activity.class);
        startActivity(intent);
    }
}
