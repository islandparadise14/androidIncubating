package com.example.homework3;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface DiaryService {
    @POST("/diaries")
    Call<DefaultResponse> addDiary(@Header("authorization") String token, @Body Diary diary);
}
