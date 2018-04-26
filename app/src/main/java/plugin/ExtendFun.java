package plugin;

import android.content.Context;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import com.blankj.utilcode.util.SPUtils;
import com.fif.baselib.base.BaseActivity;
import com.just.agentweb.AgentWeb;

/**
 * Created by chen on 2017-06-28. js调用android接口类
 */

public class ExtendFun {

    private Context context;

    private AgentWeb agentWeb;

    public ExtendFun(Context context, AgentWeb agentWeb) {
        this.context = context;
        this.agentWeb = agentWeb;
    }

    /**
     * 返回键
     */
    @JavascriptInterface
    public void back(){
        ((BaseActivity)context).finish();
    }

    /**
     * 用户登录信息
     */
    @JavascriptInterface
    public String getUserInfo(){
        return SPUtils.getInstance().getString("login");
    }
}
