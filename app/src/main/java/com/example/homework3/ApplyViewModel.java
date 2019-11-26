package com.example.homework3;

import android.app.Application;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import java.io.File;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ApplyViewModel extends AndroidViewModel {

    public SingleLiveEvent<Void> registerFinishCallBack = new SingleLiveEvent<>();
    public SingleLiveEvent<Void> onRegisterClicked = new SingleLiveEvent<>();
    public SingleLiveEvent<Void> onProfileClicked = new SingleLiveEvent<>();

    public ApplyViewModel(@NonNull Application application) {
        super(application);
    }

    public void Register(String id, String password, String uriPath) {
        File file = new File(uriPath);
        UserService userService = RetrofitUtil.retrofit.create(UserService.class);
        userService.apply(new User(id, password), RetrofitUtil.createMultipartBody(file, "profile"))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(DefaultResponse ->{
                    if(DefaultResponse.getResult().getSuccess().equals("true")) {
                        registerFinishCallBack.call();
                    }
                }, t -> {
                    Log.e("error", t.getLocalizedMessage());
                });
    }

    public void RegiserButtonClick(View view) {
        onRegisterClicked.call();
    }

    public void profileClick(View view) {
        onProfileClicked.call();
    }
}
