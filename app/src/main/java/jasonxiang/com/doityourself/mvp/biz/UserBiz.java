package jasonxiang.com.doityourself.mvp.biz;

import jasonxiang.com.doityourself.mvp.bean.User;

/**
 * Created by JasonXiang on 2016/12/21.
 */

public class UserBiz implements IUserBiz {

    @Override
    public void login(final String userName, final String passWord, final onLoginListener loginListener) {
        new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    if ((null != userName && "Xiangjian".equals(userName)) ||
                            (null != passWord && "123456".equals(passWord))) {
                        User user = new User();
                        user.setUsername(userName);
                        user.setPassword(userName);
                        loginListener.logSuccess(user);
                    } else {
                        loginListener.logFail();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
