package com.wj.babapao.ui.view;

import com.wj.babapao.base.BaseView;
import com.wj.babapao.db.UserEntity;

import java.util.List;

/**
 * @author sunmingchuan
 * @name TomatoLiveSDK
 * @class name：com.tomatolive.library.ui.view.iview
 * @class describe
 * @time 2018/9/1 16:42
 * @change
 * @chang time
 * @class describe
 */

public interface IMainView extends BaseView {

    void onUserListSucess(List<UserEntity> userEntityList);




}
