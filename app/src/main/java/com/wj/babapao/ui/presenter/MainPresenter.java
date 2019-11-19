package com.wj.babapao.ui.presenter;

import android.content.Context;

import com.wj.babapao.base.BasePresenter;
import com.wj.babapao.db.UserEntity;
import com.wj.babapao.http.HttpRxObserver;
import com.wj.babapao.http.RequestParams;
import com.wj.babapao.http.ResultCallBack;
import com.wj.babapao.ui.view.IMainView;

import java.util.List;

/**
 * @author sunmingchuan
 * @name TomatoLiveSDK
 * @class name：com.tomatolive.library.ui.presenter
 * @class describe
 * @time 2018/9/1 16:39
 * @change
 * @chang time
 * @class describe
 */

public class MainPresenter extends BasePresenter<IMainView> {


    public MainPresenter(Context context, IMainView view) {
        super(context, view);
    }

    public void getUserList(String id) {
        addMapSubscription(mApiService.giftList(new RequestParams().getUserParams(id)),
                new HttpRxObserver(getContext(), new ResultCallBack<List<UserEntity>>() {

                    @Override
                    public void onSuccess(List<UserEntity> list) {
                        getView().onUserListSucess(list);
                    }

                    @Override
                    public void onError(int code, String errorMsg) {

                    }
                }, true));
    }


}
