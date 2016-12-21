package jasonxiang.com.doItYourself.mvp.view;

import jasonxiang.com.doItYourself.mvp.bean.User;

/**
 * Created by JasonXiang on 2016/12/21.
 */

public interface IUserLoginView {
    String getUserName();

    String getPassword();

    void clear();

    void showLoading();

    void hideLoading();

    void toMainActivity(User user);

    void showFailedError();
}
