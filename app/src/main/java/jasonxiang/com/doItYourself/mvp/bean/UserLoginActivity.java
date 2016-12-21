package jasonxiang.com.doItYourself.mvp.bean;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import jasonxiang.com.doItYourself.R;
import jasonxiang.com.doItYourself.mvp.presenter.UserLoginPresenter;
import jasonxiang.com.doItYourself.mvp.view.IUserLoginView;
import jasonxiang.com.doItYourself.xj.base.BaseActivity;

/**
 * Created by JasonXiang on 2016/12/21.
 */

public class UserLoginActivity extends BaseActivity implements IUserLoginView {

    //http://blog.csdn.net/lmj623565791/article/details/46596109
    /**
     * MVP模式的好处：
     * 1 Model和View完全分离，我们可以修改View而不影响Model
     * 2 可以更高效的使用模型，因为所有的交互都是发生在--Presenter内部
     * 3 我们可以将一个Presenter用于多个视图，而不需要改变Presenter的逻辑，
     * 因为视图的变化总是比模型的变化要频繁
     * 4 如果我们将逻辑放在Presenter中，那么我们就可以脱离用户接口来测试这些逻辑（）
     * http://www.jcodecraeer.com/a/anzhuokaifa/androidkaifa/2015/0313/2599.html
     * <p>
     * MVP和MVC模式的本质区别：
     * MVC允许Model和View进行交互的
     * MVP Model与View之间的交互由Presenter完成
     */
    @BindView(R.id.login)
    Button login;
    @BindView(R.id.clear)
    Button clear;
    @BindView(R.id.edtUserName)
    EditText edtUserName;
    @BindView(R.id.edtPassWord)
    EditText edtPassWord;
    @BindView(R.id.loadingContainer)
    RelativeLayout loadingContainer;
    @BindView(R.id.loadingProgress)
    ProgressBar loadingProgress;
    @BindView(R.id.tvError)
    TextView tvError;

    private UserLoginPresenter userLoginPresenter;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_mvp;
    }

    @Override
    protected void init(Bundle savedInstanceState) {

        userLoginPresenter = new UserLoginPresenter(this);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLoginPresenter.login();
            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLoginPresenter.clear();
            }
        });
    }

    @Override
    public String getUserName() {
        return edtUserName.getText().toString();
    }

    @Override
    public String getPassword() {
        return edtPassWord.getText().toString();
    }

    @Override
    public void clear() {
        edtUserName.setText("");
        edtPassWord.setText("");
    }

    @Override
    public void showLoading() {
        loadingContainer.setVisibility(View.VISIBLE);
        loadingProgress.setVisibility(View.VISIBLE);
        tvError.setVisibility(View.GONE);
    }

    @Override
    public void hideLoading() {
        loadingContainer.setVisibility(View.GONE);
        loadingProgress.setVisibility(View.GONE);
        tvError.setVisibility(View.GONE);
    }

    @Override
    public void toMainActivity(User user) {
        Toast.makeText(this, "Login success", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showFailedError() {
        loadingContainer.setVisibility(View.VISIBLE);
        loadingProgress.setVisibility(View.GONE);
        tvError.setVisibility(View.VISIBLE);
    }
}
