package com.fif.iclass.main;

import android.content.Intent;
import android.support.v4.app.FragmentTabHost;
import android.util.ArrayMap;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.fif.baselib.base.BaseActivity;
import com.fif.baselib.permissionutils.PermissionEnum;
import com.fif.baselib.permissionutils.PermissionManager;
import com.fif.baselib.utils.LogUtils;
import com.fif.baselib.widget.toasty.Toasty;
import com.fif.iclass.R;
import com.fif.iclass.common.utils.Constant;
import com.fif.iclass.discovery.view.DiscoveryFragment;
import com.fif.iclass.home.view.HomeFragment;
import com.fif.iclass.me.view.MeFragment;
import com.fif.iclass.message.view.MessageFragment;

/**
 * Created by chen on 2017/5/2.主界面
 */

public class MainActivity extends BaseActivity {

    private static final int REQUEST_CODE_UPGRADE = 10;
    private LayoutInflater layoutInflater ;
    private FragmentTabHost mTabHost;
    private final Class[] fragmentArray = {HomeFragment.class,DiscoveryFragment.class,MessageFragment.class,MeFragment.class};
    private static Boolean isExit = false;


    @Override
    protected void initView() {
        setContentView(R.layout.activity_main);
        //隐藏状态栏
        transStatusBar(false);
        layoutInflater = LayoutInflater.from(this);
        mTabHost = (FragmentTabHost)findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.fl_main_content);
        mTabHost.getTabWidget().setDividerDrawable(android.R.color.transparent);
        requestPermission();
    }

    @Override
    protected void loadData() {
        //检查版本更新
        checkVersion();
        int count = fragmentArray.length;
        if(!this.isFinishing()){
            //解决ft的commit抛出的异常错误
            for(int i = 0; i < count; i++){
                TabHost.TabSpec tabSpec = mTabHost.newTabSpec(Constant.ConValue.mAppText[i]+"").setIndicator(createTabView(i));
                // 将Tab按钮添加进Tab选项卡中
                mTabHost.addTab(tabSpec, fragmentArray[i], null);
            }
            mTabHost.setCurrentTab(0);
            for (int i =0; i < mTabHost.getTabWidget().getChildCount(); i++) {
                View v = mTabHost.getTabWidget().getChildAt(i);
                TextView tv=(TextView)v.findViewById(R.id.tv_tab_des);
                if(mTabHost.getCurrentTab()==i){
                    tv.setTextColor(getResources().getColor(R.color.text_main_color));
                }
                else {
                    tv.setTextColor(getResources().getColor(R.color.black_AAA));
                }
            }

            mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
                @Override
                public void onTabChanged(String arg0) {
                    for (int i =0; i < mTabHost.getTabWidget().getChildCount(); i++) {
                        View v = mTabHost.getTabWidget().getChildAt(i);
                        TextView tv=(TextView)v.findViewById(R.id.tv_tab_des);
                        if(mTabHost.getCurrentTab()==i){
                            tv.setTextColor(getResources().getColor(R.color.text_main_color));
                        }
                        else {
                            tv.setTextColor(getResources().getColor(R.color.black_AAA));
                        }
                    }
                    // 首页和我的界面状态栏字体默认是白色的
                    if (mTabHost.getCurrentTab() == 0 || mTabHost.getCurrentTab() == 3) {
                        transStatusBar(false);
                    } else {
                        transStatusBar(true);
                    }
                }
            });
        }
    }


    private View createTabView(int index){
        View view = layoutInflater.inflate(R.layout.bottom_tab_item_view, null);
        RelativeLayout rel_num = (RelativeLayout) view.findViewById(R.id.rl_message);
        rel_num.setVisibility(View.GONE);
        ImageView imageview = (ImageView) view.findViewById(R.id.iv_tab_icon);
        imageview.setBackgroundResource(Constant.ConValue.mImageViewArray[index]);
        TextView tv=(TextView)view.findViewById(R.id.tv_tab_des);
        tv.setText(Constant.ConValue.mTabText[index]);
        return view;
    }

    /**
     * 请求app需要用到的所有权限
     */
    private void requestPermission(){
        PermissionManager.Builder()
                .permission(PermissionEnum.WRITE_EXTERNAL_STORAGE ,
                        PermissionEnum.READ_EXTERNAL_STORAGE,
                        PermissionEnum.READ_PHONE_STATE,
                        PermissionEnum.ACCESS_COARSE_LOCATION,
                        PermissionEnum.CAMERA,
                        PermissionEnum.READ_CONTACTS,
                        PermissionEnum.RECORD_AUDIO)
                .callback(allPermissionsGranted -> {
                    if (!allPermissionsGranted) {
                        Toasty.info(MainActivity.this, "请在设置中开启权限", Toast.LENGTH_SHORT).show();
                    }
                }).ask(this);
    }



    //记录用户首次点击返回键的时间
    private long firstTime=0;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK && event.getAction()==KeyEvent.ACTION_DOWN){
            if (System.currentTimeMillis()-firstTime>2000){
                Toasty.info(MainActivity.this,"再按一次退出程序",Toast.LENGTH_SHORT).show();
                firstTime=System.currentTimeMillis();
            }else{
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 检查版本更新
     */
    private void checkVersion() {
       /* ArrayMap<String, String> map = new ArrayMap<>();
        //app类型（1Android，2Ios）
        map.put("appType", "1");
        NetWorkRequest.checkUpdate(new NetWorkSubscriber<UpdateBean>(){
            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }
            @Override
            public void onNext(UpdateBean updateBean) {
                super.onNext(updateBean);
                //当前版本小于最新版本，但大于等于强制更新版本，为提示更新至最新版本
                //当前版本小于最新版本，且小于强制更新版本，提示强制至最新版本
                //当前版本等于最新版本，提示已经是最新版本
                UpdateBean.DataBean.LatestVersionBean latestVer = updateBean.getData().getLatest_version();
                UpdateBean.DataBean.CoerceVersionBean coerceVer = updateBean.getData().getCoerce_version();
                if (AppUtils.getAppVersionCode() < latestVer.getVersionCode() && AppUtils.getAppVersionCode() >= coerceVer.getVersionCode()) {
                    Intent intent = new Intent(MainActivity.this, SoftwareUpdateActivity.class);
                    intent.putExtra("updateBean", updateBean);
                    intent.putExtra("is_must_upgrade", false);
                    MainActivity.this.startActivityForResult(intent, REQUEST_CODE_UPGRADE);
                } else if(AppUtils.getAppVersionCode() < latestVer.getVersionCode() && AppUtils.getAppVersionCode() < coerceVer.getVersionCode()) {
                    Intent intent = new Intent(MainActivity.this, SoftwareUpdateActivity.class);
                    intent.putExtra("updateBean", updateBean);
                    intent.putExtra("is_must_upgrade", true);
                    MainActivity.this.startActivityForResult(intent, REQUEST_CODE_UPGRADE);
                } else {
                    //Toasty.success(MainActivity.this, "已经是最新版本", Toast.LENGTH_SHORT).show();
                }
            }
        }, map);*/
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE_UPGRADE:
              if (resultCode == RESULT_OK) {
                  finish();
              } else if (resultCode == RESULT_CANCELED) {
                  //取消强制更新
                  //finish();
              } else if (resultCode == SoftwareUpdateActivity.RESULT_DISMISS) {
                  //非强制更新，取消
                  LogUtils.v("MainActivity", "cancel update");
              }
                  break;
        }
    }
}
