package com.example.homework3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.homework3.util.SharedPreferenceUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DiaryAddActivity extends AppCompatActivity {
    EditText title;
    EditText content;
    Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_add);
        init();
        setListener();
    }

    private void init() {
        title = findViewById(R.id.titleText);
        content = findViewById(R.id.contentText);
        submitButton = findViewById(R.id.diary_submit_button);
    }

    private void setListener() {
        submitButton.setOnClickListener(v -> {
            DiaryService diaryService = RetrofitUtil.retrofit.create(DiaryService.class);
            Call<DefaultResponse> call = diaryService.addDiary(
                    SharedPreferenceUtil.getString("token"),
                    new Diary(
                        title.getText().toString(),
                        content.getText().toString(),
                        SharedPreferenceUtil.getString("username")
                    )
            );
            call.enqueue(new Callback<DefaultResponse>() {
                @Override
                public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                    Log.d("error", response.body().getResult().getMessage());
                    if(response.body().getResult().getSuccess().equals("true")) {
                        Toast.makeText(getApplicationContext(), response.body().getResult().getMessage(), Toast.LENGTH_LONG).show();
                        finish();
                    }
                    Toast.makeText(getApplicationContext(), response.body().getResult().getMessage(), Toast.LENGTH_LONG).show();
                    Log.e("error","error1");
                }

                @Override
                public void onFailure(Call<DefaultResponse> call, Throwable t) {
                    Log.e("error","error");
                }
            });
        });
    }
}
