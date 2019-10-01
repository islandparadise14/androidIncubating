package com.example.homework3;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface UserService {
    @Multipart
    @POST("/users")
    Call<DefaultResponse> apply(@Part("data") User user, @Part MultipartBody.Part profile);

    @POST("/sign")
    Observable<LoginResponse> signIn(@Body User user);
}
