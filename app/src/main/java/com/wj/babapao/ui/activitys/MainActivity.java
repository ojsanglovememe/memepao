package com.wj.babapao.ui.activitys;

import android.os.Bundle;

import com.wj.babapao.R;
import com.wj.babapao.base.BaseActivity;
import com.wj.babapao.db.UserEntity;
import com.wj.babapao.ui.presenter.MainPresenter;
import com.wj.babapao.ui.view.IMainView;

import java.util.List;

public class MainActivity extends BaseActivity<MainPresenter> implements IMainView {


    @Override
    protected MainPresenter createPresenter() {
        return new MainPresenter(mContext, this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.statusBarDarkFont(false).init();
    }

    @Override
    public void initView(Bundle savedInstanceState) {

    }

    @Override
    public void initData() {
        super.initData();
    }

    @Override
    public void initListener() {
        super.initListener();
    }

    @Override
    public void onUserListSucess(List<UserEntity> userEntityList) {

    }

    @Override
    public void onResultError(int code) {

    }
}
