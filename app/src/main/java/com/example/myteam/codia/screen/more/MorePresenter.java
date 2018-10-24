package com.example.myteam.codia.screen.more;

import android.support.annotation.NonNull;

import com.example.myteam.codia.R;
import com.example.myteam.codia.data.model.User;
import com.example.myteam.codia.data.source.local.sharedprf.SharedPrefsImpl;
import com.example.myteam.codia.data.source.local.sharedprf.SharedPrefsKey;
import com.example.myteam.codia.data.source.remote.auth.AuthenicationRepository;
import com.example.myteam.codia.data.source.remote.auth.DataCallback;
import com.example.myteam.codia.utils.DateTimeUtils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MorePresenter implements MoreContract.Presenter {

    private MoreContract.ViewModel mViewModel;
    private AuthenicationRepository mRepository;
    private SharedPrefsImpl mSharedPrefs;
    private DataCallback<User> mUserDataCallback;
    private User mUser;

    public MorePresenter(MoreContract.ViewModel viewModel, AuthenicationRepository repository,
                         SharedPrefsImpl sharedPrefs, DataCallback<User> userDataCallback) {
        mViewModel = viewModel;
        mRepository = repository;
        mSharedPrefs = sharedPrefs;
        mUserDataCallback = userDataCallback;
    }

    @Override
    public void getProfile() {
        mViewModel.getProfileSuccessful(mUser);
    }

    @Override
    public void logout() {
        mViewModel.showDialog();
        mRepository.signOut();
        DatabaseReference userReference = FirebaseDatabase.getInstance()
                .getReference()
                .child(User.UserEntity.USERS).child(mSharedPrefs.get(SharedPrefsKey.PREF_USER_ID, String.class));
        userReference.child(User.UserEntity.ISONLINE).setValue(false);
        userReference.child(User.UserEntity.LASTLOGIN).setValue(DateTimeUtils.getCurrentTime());
        mSharedPrefs.put(SharedPrefsKey.PREF_KEEP_LOGIN, false);
        mViewModel.logoutSuccessful();
        mViewModel.dismissDialog();
    }

    @Override
    public void onStart() {
        DatabaseReference userReference = FirebaseDatabase.getInstance()
                .getReference();
        userReference.child(User.UserEntity.USERS)
                .child(mSharedPrefs.get(SharedPrefsKey.PREF_USER_ID, String.class))
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String id = dataSnapshot.getKey();
                        String avatar = (String) dataSnapshot.child(User.UserEntity.AVATAR).getValue();
                        String dateCreated = (String) dataSnapshot.child(User.UserEntity.DATECREATED).getValue();
                        String displayName = (String) dataSnapshot.child(User.UserEntity.DISPLAYNAME).getValue();
                        String email = (String) dataSnapshot.child(User.UserEntity.EMAIL).getValue();
                        String address = (String) dataSnapshot.child(User.UserEntity.ADDRESS).getValue();
                        String status = (String) dataSnapshot.child(User.UserEntity.STATUS).getValue();
                        mUser = new User.Builder().setId(id)
                                .setAvatar(avatar)
                                .setDateCreated(dateCreated)
                                .setDisplayName(displayName)
                                .setEmail(email)
                                .setAddress(address)
                                .setStatus(status)
                                .build();
                        mUserDataCallback.onGetDataSuccess(mUser);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        mViewModel.getProfileError(R.string.get_profile_error);
                    }
                });
    }

    @Override
    public void onStop() {

    }

}
