package com.example.homework3;

import android.app.Application;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.homework3.util.SharedPreferenceUtil;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class LoginViewModel extends AndroidViewModel {
    public static final String TAG = "aaa";

    public SingleLiveEvent<Void> loginFinishCallBack = new SingleLiveEvent<>();
    public SingleLiveEvent<Void> onLoginClicked = new SingleLiveEvent<>();

    public LoginViewModel(@NonNull Application application) {
        super(application);
    }

    public void Login(String email, String password) {
        UserService userService = RetrofitUtil.retrofit.create(UserService.class);
        userService.signIn(new User(email, password))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(LoginResponse -> {
                    String token = LoginResponse.getAuth().getToken();
                    SharedPreferenceUtil.setStringValue("token", token);
                    loginFinishCallBack.call();
                }, t -> {
                    Log.e(TAG, "error type : "+t.getLocalizedMessage());
                });
    }

    public void onLoginButtonClick(View view) {
        onLoginClicked.call();
    }
}
