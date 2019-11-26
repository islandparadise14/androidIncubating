package com.example.homework3;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.example.homework3.databinding.ActivityApplyBinding;

public class ApplyActivity extends AppCompatActivity {

    private ApplyViewModel mViewModel;
    private ActivityApplyBinding mBinding;
    private Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init();
        observeViewModel();
    }

    private void init() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_apply);

        mViewModel = ViewModelProviders.of(this).get(ApplyViewModel.class);
        mBinding.setViewModel(mViewModel);
    }

    private void observeViewModel() {
        mViewModel.onProfileClicked.observe(this, aVoid -> requestImage());

        mViewModel.onRegisterClicked.observe(this, aVoid -> {
            mViewModel.Register(mBinding.editId.getText().toString(), mBinding.editPassword1.getText().toString(), getRealPathFromUri(uri));
        });

        mViewModel.registerFinishCallBack.observe(this, aVoid -> successApply());
    }

    public void successApply() {
        Toast.makeText(getApplicationContext(), "회원가입 성공!", Toast.LENGTH_SHORT).show();
        finish();
    }

    public void requestImage() {
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
            Glide.with(getApplicationContext()).load(uri).into(mBinding.profileImageView);
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