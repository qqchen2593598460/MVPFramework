package com.fif.iclass.components;

import android.graphics.Bitmap;
import android.text.TextUtils;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.fif.baselib.base.BaseActivity;
import com.fif.baselib.config.PageStateConfig;
import com.fif.iclass.R;
import com.just.agentweb.AgentWeb;

import butterknife.BindView;
import butterknife.ButterKnife;
import plugin.ExtendFun;

/**
 * Created by chen on 2017-06-28  主界面其他应用跳转入口
 */

public class WebAppActivity extends BaseActivity {

    @BindView(R.id.state_content)
    LinearLayout llContent;
    @BindView(R.id.rl_web_app)
    RelativeLayout rlWebApp;
    private String url;
    private AgentWeb agentWeb;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_web_app);
        ButterKnife.bind(this);
        url = getIntent().getStringExtra("url");
        setStateLayout(llContent);
    }


    @Override
    protected void loadData() {

        if (TextUtils.isEmpty(url)) {
            sendHandlerMeg(PageStateConfig.NO_NET);
            return;
        }

        if (url != null && !TextUtils.isEmpty(url)) {
            agentWeb = AgentWeb.with(this)
                    .setAgentWebParent(rlWebApp, new RelativeLayout.LayoutParams(-1, -1))
                    .useDefaultIndicator()
                    .createAgentWeb()
                    .ready()
                    .go(url);
            agentWeb.getJsInterfaceHolder().addJavaObject("extendFun",new ExtendFun(this, agentWeb));
        }
    }

    @Override
    public void retryLoadData() {
        super.retryLoadData();
        sendHandlerMeg(PageStateConfig.LOADING);
        loadData();
    }

    /**
     * WebViewClient
     */
    private WebViewClient webViewClient = new WebViewClient() {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            //do you  work
        }
    };
    /**
     * WebChromeClient
     */
    private WebChromeClient webChromeClient = new WebChromeClient() {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            //do you work
        }
    };

    @Override
    protected void onPause() {
        agentWeb.getWebLifeCycle().onPause();
        super.onPause();

    }

    @Override
    protected void onResume() {
        agentWeb.getWebLifeCycle().onResume();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        agentWeb.getWebLifeCycle().onDestroy();
        super.onDestroy();
    }
}
