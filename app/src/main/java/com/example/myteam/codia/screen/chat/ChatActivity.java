package com.example.myteam.codia.screen.chat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.example.myteam.codia.R;
import com.example.myteam.codia.data.model.Message;
import com.example.myteam.codia.data.model.User;
import com.example.myteam.codia.data.source.remote.auth.DataCallback;
import com.example.myteam.codia.databinding.ActivityChatBinding;
import com.example.myteam.codia.utils.navigator.Navigator;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by khanhjm on 23-10-2018.
 */
public class ChatActivity extends AppCompatActivity implements DataCallback<User>, View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    public static final String TAG = "ChatActivity";
    public static final int REQUEST_CODE_GALLERY = 1;
    public static final int REQUEST_CODE_CAMERA = 2;
    public static final int REQUEST_CODE_WRITE_EXTERNAL_STORAGE = 3;

    private ChatViewModel mViewModel;
    private ActivityChatBinding mBinding;
    private Navigator mNavigator;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        String userChatId = getIntent().getStringExtra(User.UserEntity.ID);
        mNavigator = new Navigator(this);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_chat);
        mViewModel = new ChatViewModel(this, userChatId);
        ChatPresenter presenter = new ChatPresenter(mViewModel, this);
        mViewModel.setPresenter(presenter);

        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_message_receive, null);
        initializeRecyclerView(view);

        mBinding.btnCall.setOnClickListener(this);
        mBinding.btnVideo.setOnClickListener(this);
        mBinding.btnImage.setOnClickListener(this);
        mBinding.btnEmoji.setOnClickListener(this);
        mBinding.btnInfo.setOnClickListener(this);
        mBinding.btnSend.setOnClickListener(this);
        mBinding.btnBack.setOnClickListener(this);
        mBinding.swipeRefreshLayout.setOnRefreshListener(this);

        mBinding.edtContent.addTextChangedListener(sendTextWatcher);
    }

    private void initializeRecyclerView(View view) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        ChatAdapter adapter = new ChatAdapter(view.getContext(), mViewModel.getMessageList());
        adapter.setListener(mListener);
        RecyclerView recyclerView = mBinding.rvMessage;
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        mViewModel.setAdapter(adapter);
        mViewModel.setLinearLayoutManager((LinearLayoutManager) recyclerView.getLayoutManager());
        mViewModel.LoadMessage();
    }

    @Override
    public void onGetDataSuccess(User data) {
        mBinding.textDisplayName.setText(data.getDisplayName());
        mBinding.textLastTime.setText(data.getLastLogin());
        String urlImage = data.getAvatar();
        if (urlImage == null || urlImage.isEmpty()) {
            mBinding.avatar.setImageResource(R.drawable.ic_profile);
        } else {
            Picasso.get().load(urlImage).into(mBinding.avatar);
        }
        mViewModel.getProfileSuccessful(data);
    }

    @Override
    public void onGetDataFailed(String msg) {

    }

    public void hideSoftKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.
                INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        mBinding.edtContent.clearFocus();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        hideSoftKeyboard();
        return true;
    }

    private Timer timer;
    private TextWatcher sendTextWatcher = new TextWatcher() {
        @Override
        public void afterTextChanged(Editable arg0) {
            // user typed: start the timer
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    // do your actual work here
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (mBinding.edtContent.getText().toString().isEmpty()) {
                                mBinding.btnSend.setImageResource(R.drawable.ic_like);
                            } else {
                                mBinding.btnSend.setImageResource(R.drawable.ic_send);
                            }
                        }
                    });
                }
            }, 1); // 500ms delay before the timer executes the "run" method from TimerTask
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            // nothing to do here
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // user is typing: reset already started timer (if existing)
            if (timer != null) {
                timer.cancel();
            }
        }
    };

    @Override
    public void onStart() {
        super.onStart();
        mViewModel.onStart();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnCall:
                hideSoftKeyboard();
                NotifyFucntionUnavailable();
                break;
            case R.id.btnVideo:
                hideSoftKeyboard();
                NotifyFucntionUnavailable();
                break;
            case R.id.btnImage:
                if (!CheckPermission()) return;
                Intent intentGallery = new Intent(Intent.ACTION_GET_CONTENT);
                intentGallery.setType("image/*");
                startActivityForResult(Intent.createChooser(intentGallery, "Select image"), REQUEST_CODE_GALLERY);
                break;
            case R.id.btnEmoji:
                if (!CheckPermission()) return;
                Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intentCamera, REQUEST_CODE_CAMERA);
                break;
            case R.id.btnInfo:
                hideSoftKeyboard();
                NotifyFucntionUnavailable();
                break;
            case R.id.btnSend:
                mViewModel.sendMessage(mBinding.edtContent.getText().toString().trim());
                mBinding.edtContent.setText("");
                ScrollRecycleView();
                break;
            case R.id.btnBack:
                hideSoftKeyboard();
                finish();
                break;
        }
    }

    public void NotifyFucntionUnavailable() {
        Toast.makeText(ChatActivity.this, "This feature will soon be released !", Toast.LENGTH_SHORT).show();
    }

    public void ScrollRecycleView() {
        mBinding.rvMessage.scrollToPosition(mBinding.rvMessage.getAdapter().getItemCount() - 1);
    }

    @Override
    public void onRefresh() {
        mViewModel.LoadMore();
    }

    public void StopRefresh() {
        mBinding.swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_GALLERY && resultCode == RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            mViewModel.upload(imageUri);
        } else if (requestCode == REQUEST_CODE_CAMERA && resultCode == RESULT_OK && data != null) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            Uri imageUri = getImageUri(this, bitmap);
            mViewModel.upload(imageUri);
        }
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, null, null);
        Uri uri = Uri.parse(path);
        return uri;
    }

    public void ShowImage(String link) {
        mNavigator.addFragment(R.id.chat_fragment, ImageChatFragment.newInstance(link), true,
                Navigator.NavigateAnim.FADED, ImageChatFragment.TAG);
    }

    private ChatContract.ChatListener mListener = new ChatContract.ChatListener() {
        @Override
        public void onSelect(String link) {
            ShowImage(link);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {

        }
    };

    private boolean CheckPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                Snackbar.make(findViewById(android.R.id.content), "Please Grant Permissions",
                        Snackbar.LENGTH_INDEFINITE).setAction("ENABLE",
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ActivityCompat.requestPermissions(ChatActivity.this,
                                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                        REQUEST_CODE_WRITE_EXTERNAL_STORAGE);
                            }
                        }).show();
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_CODE_WRITE_EXTERNAL_STORAGE);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_WRITE_EXTERNAL_STORAGE: {
                if ((grantResults.length > 0) && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Accepted permission", Toast.LENGTH_SHORT).show();
                } else {
                    Snackbar.make(findViewById(android.R.id.content), "Enable Permissions from settings",
                            Snackbar.LENGTH_INDEFINITE).setAction("ENABLE",
                            new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent();
                                    intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                    intent.addCategory(Intent.CATEGORY_DEFAULT);
                                    intent.setData(Uri.parse("package:" + getPackageName()));
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                                    startActivity(intent);
                                }
                            }).show();
                }
                return;
            }
        }
    }
}
