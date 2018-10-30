package com.example.myteam.codia.data.source.remote.auth;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.myteam.codia.MainApplication;
import com.example.myteam.codia.R;
import com.example.myteam.codia.data.model.User;
import com.example.myteam.codia.data.source.local.sharedprf.SharedPrefsImpl;
import com.example.myteam.codia.data.source.local.sharedprf.SharedPrefsKey;
import com.example.myteam.codia.screen.authentication.confirm.CreateUserCallback;
import com.example.myteam.codia.screen.authentication.register.EmailExistsCallback;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.example.myteam.codia.data.source.local.sharedprf.SharedPrefsKey.PREF_EMAIL_REGISTER;
import static com.example.myteam.codia.data.source.local.sharedprf.SharedPrefsKey.PREF_PASSWORD_REGISTER;

public class AuthenicationRemoteDataSource implements AuthenicationDataSource.RemoteDataSource {

    private static final String TAG = "AuthenicationRemoteData";
    protected FirebaseAuth mFirebaseAuth;
    private DatabaseReference mDatabaseReference;
    private SharedPrefsImpl mSharedPrefs;

    public AuthenicationRemoteDataSource() {
        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();
        mSharedPrefs = new SharedPrefsImpl(MainApplication.getInstance());
    }

    @Override
    public void getUserCodia(String userId, final DataCallback<User> callback) {
        DatabaseReference userReference = FirebaseDatabase.getInstance()
                .getReference();
        userReference.child(User.UserEntity.USERS)
                .child(mSharedPrefs.get(SharedPrefsKey.PREF_USER_ID, String.class))
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String id = dataSnapshot.getKey();
                        String avatar = (String) dataSnapshot.child(User.UserEntity.AVATAR).getValue();
                        String cover = (String) dataSnapshot.child(User.UserEntity.COVER).getValue();
                        String dateCreated = (String) dataSnapshot.child(User.UserEntity.DATECREATED).getValue();
                        String displayName = (String) dataSnapshot.child(User.UserEntity.DISPLAYNAME).getValue();
                        String email = (String) dataSnapshot.child(User.UserEntity.EMAIL).getValue();
                        String address = (String) dataSnapshot.child(User.UserEntity.ADDRESS).getValue();
                        String description = (String) dataSnapshot.child(User.UserEntity.DESCRIPTION).getValue();
                        String relationship = (String) dataSnapshot.child(User.UserEntity.RELATIONSHIP).getValue();
                        String status = (String) dataSnapshot.child(User.UserEntity.STATUS).getValue();
                        User user = new User.Builder().setId(id)
                                .setAvatar(avatar)
                                .setCover(cover)
                                .setDateCreated(dateCreated)
                                .setDisplayName(displayName)
                                .setEmail(email)
                                .setAddress(address)
                                .setDescription(description)
                                .setRelationship(relationship)
                                .setStatus(status)
                                .build();
                        callback.onGetDataSuccess(user);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        callback.onGetDataFailed(MainApplication.getInstance().getString(R.string.get_profile_error));
                    }
                });
    }

    /**
     * check email is used for another account yet?
     *
     * @param email
     * @param callback
     */
    @Override
    public void checkEmailIsUsed(String email, final EmailExistsCallback callback) {
        mFirebaseAuth.fetchSignInMethodsForEmail(email)
                .addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                    @Override
                    public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                        boolean check = !task.getResult().getSignInMethods().isEmpty();
                        callback.onEmailExists(check);
                    }
                });
    }

    /**
     * send confirm code to email register acc
     *
     * @param email
     * @param callback
     */
    @Override
    public void sendConfirmCode(String email, DataCallback<String> callback) {
        new SendEmail(callback).execute(email);
    }

    /**
     * register account Firebase with email, password
     *
     * @param email
     * @param password
     * @param callBack
     */
    @Override
    public void register(String email, String password, final DataCallback<FirebaseUser> callBack) {
        mFirebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        getResponse(task, callBack);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        callBack.onGetDataFailed(e.getMessage());
                    }
                });
    }

    @Override
    public void registerUser(String email, String displayName, FirebaseUser user, final CreateUserCallback callback) {
        //register user for codia is here
        final String uid = user.getUid();
        //init user
        User userCodia = new User.Builder().setEmail(email)
                .setDisplayName(displayName).build();
        mDatabaseReference.child("Users").child(uid).setValue(userCodia,
                new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                        if (databaseError == null) {
                            callback.onCreateUserSuccessful();
                        } else {
                            callback.onCreateUserFailed(R.string.create_account_failed);
                            //remove account firebase if register codia account failed.
                            deleteUserFB(uid);
                        }
                    }
                });

    }


    @Override
    public void getCurrentUser(final DataCallback<FirebaseUser> callBack) {
        FirebaseUser user = mFirebaseAuth.getCurrentUser();
        if (user == null) {
            callBack.onGetDataFailed(null);
        } else {
            callBack.onGetDataSuccess(user);
        }
    }

    @Override
    public void signIn(String email, String password, final DataCallback<FirebaseUser> callBack) {
        mFirebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        getResponse(task, callBack);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        callBack.onGetDataFailed(e.getMessage());
                    }
                });
    }

    @Override
    public void signOut() {
        mFirebaseAuth.signOut();
    }

    @Override
    public void resetPassword(String email, DataCallback callback) {

    }

    @Override
    public void updateProfile(String userName, Uri photo, DataCallback callback) {

    }

    @Override
    public void changePassword(FirebaseUser firebaseUser, String newPassword, ChangePasswordCallBack callback) {

    }

    @Override
    public void deleteUserFireBase(String uid) {
        //xóa ở đây
        deleteUserFB(uid);
    }

    private void getResponse(Task<AuthResult> authResultTask, DataCallback callback) {
        if (authResultTask == null) {
            callback.onGetDataFailed(null);
            return;
        }
        if (!authResultTask.isSuccessful()) {
            String message = authResultTask.getException().getMessage();
            callback.onGetDataFailed(message);
            return;
        }
        callback.onGetDataSuccess(authResultTask.getResult().getUser());
    }

    private void deleteUserFB(String uid) {
        //login then delete account register
        mFirebaseAuth.signInWithEmailAndPassword(mSharedPrefs.get(PREF_EMAIL_REGISTER, String.class),
                mSharedPrefs.get(PREF_PASSWORD_REGISTER, String.class))
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        user.delete()
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Log.d(TAG, "User account deleted.");
                                        }
                                    }
                                });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "Log in failed.");
                    }
                });

    }
}
