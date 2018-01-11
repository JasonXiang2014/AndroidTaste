package jasonxiang.com.doityourself.mvp.presenter;

import android.os.Handler;

import jasonxiang.com.doityourself.mvp.bean.User;
import jasonxiang.com.doityourself.mvp.biz.onLoginListener;
import jasonxiang.com.doityourself.mvp.biz.UserBiz;
import jasonxiang.com.doityourself.mvp.view.IUserLoginView;

/**
 * Created by JasonXiang on 2016/12/21.
 */

public class UserLoginPresenter {

    private UserBiz userBiz;
    private IUserLoginView iUserLoginView;
    private Handler mHandler = new Handler();

    public UserLoginPresenter(IUserLoginView iUserLoginView) {
        this.iUserLoginView = iUserLoginView;
        userBiz = new UserBiz();
    }

    public void login() {
        iUserLoginView.showLoading();
        userBiz.login(iUserLoginView.getUserName(), iUserLoginView.getPassword(), new onLoginListener() {

            @Override
            public void logSuccess(final User user) {
                //需要在UI线程执行
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        iUserLoginView.hideLoading();
                        iUserLoginView.toMainActivity(user);
                    }
                });

            }

            @Override
            public void logFail() {
                //需要在UI线程执行
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        iUserLoginView.hideLoading();
                        iUserLoginView.showFailedError();
                    }
                });

            }
        });
    }

    public void clear() {
        iUserLoginView.clear();
    }

}
