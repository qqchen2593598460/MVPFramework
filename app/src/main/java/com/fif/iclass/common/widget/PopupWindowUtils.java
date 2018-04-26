package com.fif.iclass.common.widget;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.fif.baselib.utils.UIUtils;
import com.fif.baselib.widget.wheelpicker.popup.ConfirmPopup;
import com.fif.iclass.R;

/**
 * Created by chen pop工具类.
 */

public class PopupWindowUtils {

    public static PopupWindow createCenterPop(Context context , View view) {
        view.setFocusable(true);
        view.setFocusableInTouchMode(true);
        final PopupWindow popupWindow = new PopupWindow(view, UIUtils.getScreenWidth(context) - UIUtils.px2dip(context, 200), LinearLayout.LayoutParams.WRAP_CONTENT);
        WindowManager.LayoutParams params = ((Activity)context).getWindow().getAttributes();
        params.alpha=0.7f;
        ((Activity)context).getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        ((Activity)context).getWindow().setAttributes(params);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(false);
        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        view.setOnKeyListener((v, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                popupWindow.dismiss();
                return true;
            }
            return false;
        });
        popupWindow.setOnDismissListener(() -> {
            params.alpha=1f;
            ((Activity)context).getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            ((Activity)context).getWindow().setAttributes(params);
        });
        popupWindow.setAnimationStyle(R.style.pop_anim);
        popupWindow.showAtLocation(((Activity)context).getWindow().getDecorView(), Gravity.CENTER, 0, 0);
        return popupWindow;
    }

    public static PopupWindow showAsDropDown(Context context , View layout , View view){
        layout.setFocusable(true); // 这个很重要
        layout.setFocusableInTouchMode(true);
        final PopupWindow popupWindow = new PopupWindow(layout, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(false);
        layout.setOnKeyListener((v, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                popupWindow.dismiss();
                return true;
            }
            return false;
        });
        popupWindow.showAsDropDown(view,  Gravity.CENTER|Gravity.CENTER_HORIZONTAL, 0, 0);
        return popupWindow;
    }

    /**
     * 底部弹出popwindow
     */
    public static ConfirmPopup showBottomPop(Activity activity, View signView) {
        return new ConfirmPopup(activity) {
            @NonNull
            @Override
            protected View makeCenterView() {
                return signView;
            }
        };
    }
}
