package com.fif.iclass.common.utils;

import android.content.Context;
import android.os.Handler;
import android.util.ArrayMap;
import android.widget.Toast;

import com.blankj.utilcode.util.SPUtils;
import com.fif.baselib.utils.ActivityUtils;
import com.fif.baselib.utils.StringUtils;
import com.fif.baselib.widget.toasty.Toasty;
import com.fif.iclass.app.FIFApplication;
import com.fif.iclass.common.bean.LoginBean;
import com.fif.iclass.common.http.NetWorkRequest;
import com.fif.iclass.common.http.NetWorkSubscriber;
import com.fif.iclass.main.LoginActivity;
import com.fif.iclass.main.MainActivity;
import com.google.gson.Gson;

import java.util.List;

import greendao.UserVO;
import greendao.UserVODao;

public class DoLoginUtil {
	
	private static final String TAG = "DoLoginUtil";
	private Context context;
	private Handler handler;

	public DoLoginUtil(Context context){
		this.context = context;
	}
	public DoLoginUtil(Context context, Handler handler){
		this.context = context;
		this.handler = handler;
	}

	
	// the first login
	public void authLogin(String userName , String userPsd) {
    	doServerLogin(userName,userPsd);
	}

	public void doServerLogin(String name, String passWord) {
		if (StringUtils.isBlank(name) || StringUtils.isBlank(passWord)) {
			handler.sendEmptyMessage(Constant.ConLineState.LOGINFRAME);
		}
		ArrayMap<String, String> requestMap = new ArrayMap<>();
		requestMap.put("username", name);
		requestMap.put("password", passWord);
		NetWorkRequest.login(new NetWorkSubscriber<LoginBean>(){
			@Override
			public void onError(Throwable e) {
				super.onError(e);
				handler.sendEmptyMessage(Constant.ConLineState.LOGINFRAME);
			}
			@Override
			public void onNext(LoginBean loginBean) {
				super.onNext(loginBean);
				if (!loginBean.getStatus().equals("success")){
					handler.sendEmptyMessage(Constant.ConLineState.LOGINFRAME);
					Toasty.error(context, loginBean.getMessage(), Toast.LENGTH_SHORT).show();
					return;
				}
				//handler.sendEmptyMessage(Constant.ConLineState.SUCCESS);
				Toasty.success(context, loginBean.getMessage(), Toast.LENGTH_SHORT).show();
				//登录成功
				doSuccess(loginBean, passWord);
				Gson gson = new Gson();
				String loginJson = gson.toJson(loginBean);
				SPUtils.getInstance().put("login", loginJson);
				ActivityUtils.startActivity(context, MainActivity.class);
				((LoginActivity)context).finish();
			}
		} , requestMap);
	}

	/**
	 *
	 */
	private void doSuccess(LoginBean loginBean, String Pwd) {
		UserVO userVO = new UserVO();
		userVO.setUserId(loginBean.getData().getUserId());
		//因为app会在权限进行改变的时候重启app，当在设置界面修改权限再返回app的时候，因为没有走登录界面，userId为null
		//其他界面在获取用户的时候导致空指针，在登录完成之后保存临时用户id 具体参考http://www.jianshu.com/p/cb68ca511776
		SPUtils.getInstance().put("userId", loginBean.getData().getUserId());
		//TODO 验证密码是否正确 临时保存在SP中，后期进行加密处理
		SPUtils.getInstance().put("userPwd", Pwd);
		userVO.setLogin(loginBean.getData().getLogin());
		userVO.setName(loginBean.getData().getName());
		userVO.setSchoolName(loginBean.getData().getSchoolName());
		userVO.setToken(loginBean.getData().getToken());
		userVO.setUserName(loginBean.getData().getUserName());
		userVO.setUserId(loginBean.getData().getUserId());
		userVO.setUserNumber(loginBean.getData().getUserNumber());
		userVO.setUserType(loginBean.getData().getUserType());
		userVO.setUserFace(loginBean.getData().getUserFace());
		//将用户id保存在application里面
		FIFApplication.getInstance().setUserId(userVO.getUserId());
		//将用户信息保存在数据库中
		UserVODao userVODao = FIFApplication.getInstance().getDaoSession().getUserVODao();
		List<UserVO> userList = userVODao.queryBuilder()
				.where(UserVODao.Properties.UserId
				.eq(userVO.getUserId()))
				.build()
				.list();
		if(userList.size() > 0) {
			UserVO entity = userList.get(0);
			userVO.setId(entity.getId());
			userVODao.update(userVO);
		} else {
			userVODao.insert(userVO);
		}

	}
}
