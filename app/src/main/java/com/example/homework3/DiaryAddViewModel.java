package com.example.homework3;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.homework3.util.SharedPreferenceUtil;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class DiaryAddViewModel extends AndroidViewModel {
    public DiaryAddViewModel(@NonNull Application application) {
        super(application);
    }

    SingleLiveEvent<Void> onSubmitButtonClicked = new SingleLiveEvent<>();
    SingleLiveEvent<Void> finishSubmitSuccessCallback = new SingleLiveEvent<>();
    SingleLiveEvent<Void> finishSubmitFailCallback = new SingleLiveEvent<>();
    MutableLiveData<String> mToastMessage = new MutableLiveData();

    public void Submit(String title, String content) {
        DiaryService diaryService = RetrofitUtil.retrofit.create(DiaryService.class);
        diaryService.addDiary(SharedPreferenceUtil.getString("token"), new Diary(title, content, SharedPreferenceUtil.getString("username")))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(DefaultResponse -> {
                    Log.d("error", DefaultResponse.getResult().getMessage());
                    if(DefaultResponse.getResult().getSuccess().equals("true")) {
                        mToastMessage.setValue(DefaultResponse.getResult().getMessage());
                        finishSubmitSuccessCallback.call();
                    }
                    mToastMessage.setValue(DefaultResponse.getResult().getMessage());
                    Log.e("error","error1");
                }, t -> {
                    Log.e("error","error");
                });
    }

    public void submitButtonClick() {
        onSubmitButtonClicked.call();
    }
}
