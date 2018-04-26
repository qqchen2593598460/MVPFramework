package com.fif.iclass.main;

import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.SPUtils;
import com.bumptech.glide.Glide;
import com.fif.baselib.base.BaseActivity;
import com.fif.baselib.utils.NetUtils;
import com.fif.baselib.utils.StringUtils;
import com.fif.baselib.utils.WeakHandler;
import com.fif.baselib.widget.sweetdialog.SweetAlertDialog;
import com.fif.baselib.widget.toasty.Toasty;
import com.fif.iclass.R;
import com.fif.iclass.app.FIFApplication;
import com.fif.iclass.common.utils.Constant;
import com.fif.iclass.common.utils.DoLoginUtil;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import greendao.UserVO;
import greendao.UserVODao;

/**
 * Created by chen on 登录界面
 */

public class LoginActivity extends BaseActivity {

    @BindView(R.id.iv_login_finish)
    public ImageView ivFinish;
    @BindView(R.id.et_login_username)
    public EditText etUser;
    @BindView(R.id.et_login_password)
    public EditText etPsw;
    @BindView(R.id.tv_login)
    public TextView tvLogin;
    @BindView(R.id.tv_login_chooseschool)
    public TextView tvChooseSchool;
    @BindView(R.id.ll_login_loading)
    public LinearLayout llLoginLoading;
    @BindView(R.id.civ_login_face)
    ImageView ivFace;
    @BindView(R.id.iv_username_cancel)
    ImageView ivUserCancel;
    @BindView(R.id.iv_password_cancel)
    ImageView ivPwdCancel;
    private Unbinder unBind;
    private LoginHandler handler = new LoginHandler(LoginActivity.this) ;
    private String errorAcount ,userPassword ;
    private DoLoginUtil doLoginUtil;
    private SweetAlertDialog pDialog;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_login);
        unBind = ButterKnife.bind(this);
        //setStateLayout(llLoginLoading);
        initListener();
    }

    @Override
    protected void loadData() {
        etUser.setText(SPUtils.getInstance().getString("username", ""));
        etPsw.setText(SPUtils.getInstance().getString("userPwd", ""));
    }

    /**
     * 初始化监听
     */
    private void initListener() {
        ivFinish.setOnClickListener(v -> LoginActivity.this.finish());
        tvChooseSchool.setOnClickListener( v -> {

        });
        etUser.setOnFocusChangeListener((v, hasFocus) -> {
            if(!hasFocus){
                //输入完成时，查询数据库中是否还有这个用户的头像
                UserVODao userVODao = FIFApplication.getInstance().getDaoSession().getUserVODao();
                List<UserVO> userList = userVODao.queryBuilder()
                        .where(UserVODao.Properties.UserName
                        .eq(etUser.getText().toString().trim()))
                        .build()
                        .list();
                if(userList.size() == 1) {
                    UserVO entity = userList.get(0);
                    Glide.with(this)
                            .load(entity.getUserFace())
                            .error(R.mipmap.img_contact)
                            .into(ivFace);
                }
            }
        });
        tvLogin.setOnClickListener(v -> {
            Editable userName = etUser.getText();
            Editable userPsd = etPsw.getText();
            if (!NetUtils.isConnected(LoginActivity.this)) {
                Toasty.error(LoginActivity.this, "当前无网络，请检查网络连接", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(userPsd)) {
                Toasty.info(LoginActivity.this, "用户名或者密码不能为空", Toast.LENGTH_SHORT).show();
            } else {
                //TODO 检验用户名和密码
                login(userName.toString().trim() , userPsd.toString().trim());
            }
        });
        etUser.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                if (!TextUtils.isEmpty(etUser.getText().toString())) {
                    ivUserCancel.setVisibility(View.VISIBLE);
                } else {
                    ivUserCancel.setVisibility(View.GONE);
                }
            }
        });
        ivUserCancel.setOnClickListener(v -> {
            etUser.setText("");
            etPsw.setText("");
            SPUtils.getInstance().put("username", "");
            SPUtils.getInstance().put("userPwd", "");
        });
        etPsw.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                if (!TextUtils.isEmpty(etPsw.getText().toString())) {
                    ivPwdCancel.setVisibility(View.VISIBLE);
                } else {
                    ivPwdCancel.setVisibility(View.GONE);
                }
            }
        });
        ivPwdCancel.setOnClickListener(v -> {
            etPsw.setText("");
            SPUtils.getInstance().put("userPwd", "");
        });
        etUser.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s.toString().trim())) {
                    ivUserCancel.setVisibility(View.VISIBLE);
                } else {
                    ivUserCancel.setVisibility(View.GONE);
                    SPUtils.getInstance().put("username", "");
                }
                if (!TextUtils.isEmpty(etPsw.getText().toString())) etPsw.setText("");
            }
        });
        etPsw.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s.toString().trim())) {
                    ivPwdCancel.setVisibility(View.VISIBLE);
                } else {
                    ivPwdCancel.setVisibility(View.GONE);
                    SPUtils.getInstance().put("userPwd", "");
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unBind.unbind();
    }

    private void login(String userName , String UserPsd) {

        if (StringUtils.isNotBlank(userName)
                && StringUtils.isNotBlank(UserPsd)) {
            try {
                //登录
                //sendHandlerMeg(0);
                pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
                pDialog.setTitleText("登录中...");
                pDialog.setCancelable(true);
                pDialog.show();
                doLoginUtil = new DoLoginUtil(this , handler);
                doLoginUtil.authLogin(userName , UserPsd );
            } catch (SecurityException e) {
                e.printStackTrace();
            }

        }
    }


    private class LoginHandler extends WeakHandler<LoginActivity> {

        public LoginHandler(LoginActivity owner) {
            super(owner);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (pDialog.isShowing()) pDialog.dismiss();
            switch (msg.what) {
                case Constant.ConLineState.LOGINFRAME:
                    Toasty.error(LoginActivity.this, "当前无网络，请检查网络连接", Toast.LENGTH_SHORT).show();
                    break;
                case Constant.ConLineState.ERROR_INTERNET_TIMEOUT:
                    Toasty.error(LoginActivity.this, "当前无网络，请检查网络连接", Toast.LENGTH_SHORT).show();
                    break;
                case Constant.ConLineState.ERROR_USERANDPASSWORD:
                    Toasty.error(LoginActivity.this, "用户名或者密码错误", Toast.LENGTH_SHORT).show();
                    break;
                case Constant.ConLineState.ERROR_LOGINWRONG:
                    Toasty.error(LoginActivity.this, "当前无网络，请检查网络连接", Toast.LENGTH_SHORT).show();
                    break;
                case Constant.ConLineState.ERROR_CONNECT:
                    Toasty.error(LoginActivity.this, "当前无网络，请检查网络连接", Toast.LENGTH_SHORT).show();
                    break;
                case Constant.ConLineState.ERROR_OTHER:
                    Toasty.error(LoginActivity.this, "当前无网络，请检查网络连接", Toast.LENGTH_SHORT).show();
                    break;
                case Constant.ConLineState.SUCCESS:
                    Toasty.success(LoginActivity.this, "登陆成功", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }
}