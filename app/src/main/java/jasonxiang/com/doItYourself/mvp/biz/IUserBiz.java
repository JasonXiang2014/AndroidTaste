package jasonxiang.com.doItYourself.mvp.biz;

/**
 * Created by JasonXiang on 2016/12/21.
 */

public interface IUserBiz {

    public void login(String userName, String passWord, OnLoginListener loginListener);
}
