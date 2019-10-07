package com.example.homework3;

import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.Observable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    private EditText idEditText;
    private EditText editPassword;
    private EditText editPasswordAgain;
    private CircleImageView profile;
    private Button button;
    private Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply);

        init();
        setListeners();
    }

    private void init() {
        idEditText = findViewById(R.id.editId);
        editPassword = findViewById(R.id.editPassword1);
        editPasswordAgain = findViewById(R.id.editPassword2);
        profile = findViewById(R.id.profileImageView);
        button = findViewById(R.id.registerButton);
    }

    private void setListeners() {
        profile.setOnClickListener(v -> requestImage());

        button.setOnClickListener(v -> {
            File file = new File(getRealPathFromUri(uri));
            UserService userService = RetrofitUtil.retrofit.create(UserService.class);
            userService.apply(new User(idEditText.getText().toString(), editPassword.getText().toString()), RetrofitUtil.createMultipartBody(file, "profile"))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(DefaultResponse ->{
                        if(DefaultResponse.getResult().getSuccess().equals("true")) {
                            Toast.makeText(getApplicationContext(), "회원가입 성공!", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }, t -> {
                        Log.e("error", t.getLocalizedMessage());
                    });
        });
    }

    private void requestImage() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 3001);
        } else{
            imageIntent();
        }
    }

    private void imageIntent(){
        Intent openGalleryIntent = new Intent(Intent.ACTION_PICK);
        openGalleryIntent.setType("image/*");
        startActivityForResult(openGalleryIntent, 3000);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch(requestCode){
            case 3001:{
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    imageIntent();
                break;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 3000 && resultCode == RESULT_OK){
            uri = data.getData();
            Glide.with(getApplicationContext()).load(uri).into(profile);
        }
    }

    private String getRealPathFromUri(Uri contentUri) { //uri -> 실제경로
        Cursor cursor = getContentResolver().query(contentUri, null, null, null, null);
        if(cursor == null)
            return contentUri.getPath();
        else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(idx);
        }
    }
}