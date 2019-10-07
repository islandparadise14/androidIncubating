package com.example.homework3;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface DiaryService {
    @POST("/diaries")
    Observable<DefaultResponse> addDiary(@Header("authorization") String token, @Body Diary diary);
}
