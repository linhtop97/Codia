package com.example.myteam.codia.screen.post;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.myteam.codia.MainApplication;
import com.example.myteam.codia.R;
import com.example.myteam.codia.data.model.Post;
import com.example.myteam.codia.data.source.local.sharedprf.SharedPrefsImpl;
import com.example.myteam.codia.data.source.local.sharedprf.SharedPrefsKey;
import com.example.myteam.codia.databinding.ActivityPostBinding;
import com.example.myteam.codia.utils.Constant;
import com.example.myteam.codia.utils.DateTimeUtils;
import com.example.myteam.codia.utils.ImageUtils;
import com.example.myteam.codia.utils.navigator.Navigator;

import java.io.IOException;

import static com.example.myteam.codia.data.source.local.sharedprf.SharedPrefsKey.PREF_PRIVACY;

public class PostActivity extends AppCompatActivity implements PrivacyBottomSheetFragment.PrivacyBottomSheetListener, View.OnClickListener, CreatePostCallBack {

    private ActivityPostBinding mBinding;
    private PostViewModel mViewModel;
    private static final int CAMERA_REQUEST = 999;
    private static final int GALLERY_REQUEST = 888;
    private static final int MY_CAMERA_PERMISSION_CODE = 111;
    private Navigator mNavigator = new Navigator(this);
    private ProgressDialog mDialog;
    private SharedPrefsImpl mSharedPrefs;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSharedPrefs = new SharedPrefsImpl(this);
        mDialog = new ProgressDialog(this);
        mDialog.setMessage(getString(R.string.up_loading));
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_post);
        SharedPrefsImpl sharedPrefs = new SharedPrefsImpl(this);
        switch (sharedPrefs.get(PREF_PRIVACY, String.class)) {
            case "":
                mBinding.textPrivacyContent.setText(Constant.PUBLIC);
                break;
            case Constant.PUBLIC:
                mBinding.textPrivacyContent.setText(Constant.PUBLIC);
                break;
            case Constant.FRIEND:
                mBinding.textPrivacyContent.setText(Constant.FRIEND);
                break;
            case Constant.ONLY_ME:
                mBinding.textPrivacyContent.setText(Constant.ONLY_ME);
                break;
        }

        mBinding.cameraButton.setOnClickListener(this);
        mBinding.imageButton.setOnClickListener(this);
        mBinding.removeImgButton.setOnClickListener(this);
        mBinding.postButton.setOnClickListener(this);
        mViewModel = new PostViewModel(this, new Navigator(this));
        mBinding.setViewModel(mViewModel);

    }

    @Override
    public void setPrivacy(String privacy) {
        mBinding.textPrivacyContent.setText(privacy);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.camera_button:
                if (checkCallingOrSelfPermission(Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(PostActivity.this, new String[]{Manifest.permission.CAMERA},
                            MY_CAMERA_PERMISSION_CODE);
                } else {
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, CAMERA_REQUEST);
                }
                break;
            case R.id.image_button:
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, GALLERY_REQUEST);
                break;
            case R.id.remove_img_button:
                mBinding.imgPostUpload.setImageBitmap(null);
                mBinding.imageContainer.setVisibility(View.GONE);
                break;
            case R.id.post_button:
                //validate input here and init Post
                if (TextUtils.isEmpty(mBinding.textContent.getText().toString())) {
                    Toast.makeText(this, "Bạn chưa nhập nội dung!", Toast.LENGTH_SHORT).show();
                } else {
                    Post post = new Post.Builder()
                            .setUidUser(mSharedPrefs.get(SharedPrefsKey.PREF_USER_ID, String.class))
                            .setEdited(false)
                            .setContent(mBinding.textContent.getText().toString())
                            .setComments(null)
                            .setPrivacy(mBinding.textPrivacyContent.getText().toString())
                            .setDateCreated(DateTimeUtils.getCurrentTime()+"")
                            .build();
                    try {
                        if (mBinding.imgPostUpload.getVisibility() == View.VISIBLE) {
                            Bitmap bitmap = ((BitmapDrawable) mBinding.imgPostUpload.getDrawable()).getBitmap();
                            String images = ImageUtils.BitMapToString(bitmap);
                            post.setImage(images);
                        }
                    } catch (Exception ex) {
                        //img not available
                    }
                    mViewModel.onPostClick(post, this);
                    showDialog();
                }

                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new
                        Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            } else {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK)
            switch (requestCode) {
                case GALLERY_REQUEST:
                    Uri selectedImage = data.getData();
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(MainApplication.getInstance().getContentResolver(), selectedImage);
                        mBinding.imgPostUpload.setImageBitmap(bitmap);
                        mBinding.imageContainer.setVisibility(View.VISIBLE);
                    } catch (IOException e) {
                        Log.i("TAG", "Some exception " + e);
                    }
                    break;
                case CAMERA_REQUEST:
                    Bitmap photo = (Bitmap) data.getExtras().get("data");
                    mBinding.imgPostUpload.setImageBitmap(photo);
                    mBinding.imageContainer.setVisibility(View.VISIBLE);
                    break;
            }
    }

    @Override
    public void onCreatePostSuccessful() {
        dismissDialog();
        Toast.makeText(this, "Đăng thành công", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onCreatePostFailed(int message) {
        dismissDialog();
        mNavigator.showToast(R.string.create_post_failed);
    }

    public void showDialog() {
        if (mDialog != null && !mDialog.isShowing()) {
            mDialog.show();
        }
    }

    public void dismissDialog() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }
}
