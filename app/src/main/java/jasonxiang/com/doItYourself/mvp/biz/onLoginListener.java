package jasonxiang.com.doItYourself.mvp.biz;

import jasonxiang.com.doItYourself.mvp.bean.User;

/**
 * Created by JasonXiang on 2016/12/21.
 */

public interface OnLoginListener {
    public void logSuccess(User user);

    public void logFail();

}
