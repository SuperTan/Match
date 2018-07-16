package com.match.developer.ui.iView;

import com.match.developer.model.User;

/**
 * Author:Tanner
 * Time:2018/7/16 15:53
 * Description: This is ILoginView
 */
public interface ILoginView {

    String getName();

    String getPwd();

    boolean emptyName();
    boolean emptyPwd();

    void saveUserInfo(User user);

    void  loginSuccess();
    void loginFail(boolean flag);
}
