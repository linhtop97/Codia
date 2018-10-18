package com.example.myteam.codia.data.source.remote.auth;

import android.os.AsyncTask;

import com.example.myteam.codia.MainApplication;
import com.example.myteam.codia.R;
import com.example.myteam.codia.data.model.GMailSender;
import com.example.myteam.codia.data.source.local.sharedprf.SharedPrefsImpl;

import java.util.Random;

import static com.example.myteam.codia.data.source.local.sharedprf.SharedPrefsKey.PREF_CODE;
import static com.example.myteam.codia.utils.Constant.EMAIL_SENDER;
import static com.example.myteam.codia.utils.Constant.PASSWORD_SENDER;
import static com.example.myteam.codia.utils.Constant.SUBJECT;

public class SendEmail extends AsyncTask<String, Void, Integer> {

    private DataCallback mDataCallback;

    public SendEmail(DataCallback<String> callback) {
        mDataCallback = callback;
    }

    @Override
    protected Integer doInBackground(String... strings) {
        String email = strings[0];
        try {
            int code = 1000 + new Random().nextInt(9000);
            GMailSender sender = new GMailSender(EMAIL_SENDER, PASSWORD_SENDER);
            sender.sendMail(SUBJECT, "Your confirm code is: " + code, EMAIL_SENDER, email);
            return code;
        } catch (Exception e) {
            mDataCallback.onGetDataFailed(e.getMessage());
        }

        return 0;
    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
        new SharedPrefsImpl(MainApplication.getInstance()).put(PREF_CODE, integer);
        mDataCallback.onGetDataSuccess("Send email successful");
    }
}
