package com.match.developer.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.match.developer.R;
import com.match.developer.model.User;
import com.match.developer.ui.fragment.FriendFragment;
import com.match.developer.ui.fragment.MyFragment;
import com.match.developer.ui.fragment.RecordFragment;
import com.match.developer.util.MyUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    RecordFragment mRecordFragment;
    FriendFragment mFriendFragment;
    MyFragment mMyFragment;
    @BindView(R.id.id_bottom_record_ll)
    LinearLayout mRecordLl;
    @BindView(R.id.id_bottom_friend_ll)
    LinearLayout mFriendLl;
    @BindView(R.id.id_bottom_my_ll)
    LinearLayout mMyLl;

    @BindView(R.id.id_main_content)
    FrameLayout mContent;
    public static User  user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mRecordLl.performClick();

    }

    @OnClick(R.id.id_bottom_record_ll)
    public void selectRecord(){
        if (mRecordFragment == null) {
            mRecordFragment = new RecordFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.id_main_content,mRecordFragment);
            transaction.commit();
        }
        showFragment(mRecordFragment);
    }
    @OnClick(R.id.id_bottom_friend_ll)
    public void selectFriend(){
        if (mFriendFragment == null) {
            mFriendFragment = new FriendFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.id_main_content,mFriendFragment);
            transaction.commit();
        }
        showFragment(mFriendFragment);
    }
    @OnClick(R.id.id_bottom_my_ll)
    public void selectMy(){
        if (mMyFragment == null) {
            mMyFragment = new MyFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.id_main_content,mMyFragment);
            transaction.commit();
        }
        showFragment(mMyFragment);
    }

    private void showFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        hideAllFragment(transaction);
        transaction.show(fragment);
        transaction.commit();
    }

    /**
     * 隐藏其他布局
     * @param transaction
     */
    private void hideAllFragment(FragmentTransaction transaction) {
        if(mRecordFragment!=null){
            transaction.hide(mRecordFragment);
        }
        if(mFriendFragment!=null){
            transaction.hide(mFriendFragment);
        }
        if(mMyFragment!=null){
            transaction.hide(mMyFragment);
        }
    }

    long currentTime=0L;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK&&System.currentTimeMillis() - currentTime < 2000){
            MainActivity.this.finish();
            System.exit(0);
            return true;
        }else{
            currentTime=System.currentTimeMillis();
            MyUtils.showToast(getResources().getString(R.string.app_exit));
            return false;
        }
    }
}
