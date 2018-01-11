package jasonxiang.com.doityourself.mvp.biz;

import jasonxiang.com.doityourself.mvp.bean.User;

/**
 * Created by JasonXiang on 2016/12/21.
 */

public interface onLoginListener {
    public void logSuccess(User user);

    public void logFail();

}
