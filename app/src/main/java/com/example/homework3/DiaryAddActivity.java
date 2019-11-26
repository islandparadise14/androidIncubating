package com.example.homework3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.homework3.databinding.ActivityDiaryAddBinding;
import com.example.homework3.util.SharedPreferenceUtil;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DiaryAddActivity extends AppCompatActivity {
    DiaryAddViewModel mViewmodel;
    ActivityDiaryAddBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        observeViewModel();
    }

    private void init() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_diary_add);
        mViewmodel = ViewModelProviders.of(this).get(DiaryAddViewModel.class);
        mBinding.setViewModel(mViewmodel);
    }

    private void observeViewModel() {
        mViewmodel.onSubmitButtonClicked.observe(this, aVoid ->
                mViewmodel.Submit(mBinding.titleText.getText().toString(), mBinding.contentText.getText().toString())
        );
        mViewmodel.finishSubmitSuccessCallback.observe(this, aVoid -> {
            finish();
        });
        mViewmodel.mToastMessage.observe(this, s -> Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show());
        mViewmodel.finishSubmitFailCallback.observe(this, aVoid -> {
            finish();
        });
    }
}
