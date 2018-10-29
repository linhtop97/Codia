package com.example.myteam.codia.screen.post;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.example.myteam.codia.R;
import com.example.myteam.codia.data.source.local.sharedprf.SharedPrefsImpl;
import com.example.myteam.codia.databinding.FragmentBottomSheetPrivacyBinding;
import com.example.myteam.codia.utils.Constant;

import static com.example.myteam.codia.data.source.local.sharedprf.SharedPrefsKey.PREF_PRIVACY;

public class PrivacyBottomSheetFragment extends BottomSheetDialogFragment {

    private FragmentBottomSheetPrivacyBinding mBinding;
    private PrivacyBottomSheetListener mListener;
    private SharedPrefsImpl mSharedPrefs;
    private PostActivity mPostActivity;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_bottom_sheet_privacy, container, false);
        mSharedPrefs = new SharedPrefsImpl(getActivity());
        switch (mSharedPrefs.get(PREF_PRIVACY, String.class)) {
            case "":
                mBinding.radioPublic.setChecked(true);
                break;
            case Constant.PUBLIC:
                mBinding.radioPublic.setChecked(true);
                break;
            case Constant.FRIEND:
                mBinding.radioFriend.setChecked(true);
                break;
            case Constant.ONLY_ME:
                mBinding.radioOnlyMe.setChecked(true);
                break;
        }
        mBinding.radioGroupPrivacy.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radio_public:
                        mListener.setPrivacy(getString(R.string.Public));
                        mSharedPrefs.put(PREF_PRIVACY, Constant.PUBLIC);
                        dismiss();
                        break;
                    case R.id.radio_friend:
                        mListener.setPrivacy(getString(R.string.friend));
                        mSharedPrefs.put(PREF_PRIVACY, Constant.FRIEND);
                        dismiss();
                        break;
                    case R.id.radio_only_me:
                        mListener.setPrivacy(getString(R.string.only_me));
                        mSharedPrefs.put(PREF_PRIVACY, Constant.ONLY_ME);
                        dismiss();
                        break;
                }
            }
        });
        return mBinding.getRoot();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mListener = (PrivacyBottomSheetListener) context;
        mPostActivity = (PostActivity) context;
    }

    public interface PrivacyBottomSheetListener {
        void setPrivacy(String privacy);
    }
}
