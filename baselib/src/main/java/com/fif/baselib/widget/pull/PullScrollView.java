package com.fif.baselib.widget.pull;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;

/**
 * Created by chen on 2017-09-05.
 */

public class PullScrollView extends ScrollView {
    private ScrollViewListener scrollViewListener = null;

    private int lastY = 0;
    private int touchEventId = -9983761;
    private float downY;

    public PullScrollView(Context context) {
        super(context);
    }

    public PullScrollView(Context context, AttributeSet attrs,
                        int defStyle) {
        super(context, attrs, defStyle);
    }

    public PullScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setScrollViewListener(ScrollViewListener scrollViewListener) {
        this.scrollViewListener = scrollViewListener;
    }
    public interface ScrollViewListener {
        void openAnim();
        void closeAnim();
    }
    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            View scroller = (View) msg.obj;

            if (msg.what == touchEventId) {
                if (lastY == scroller.getScrollY()) {
                    //停止了，此处你的操作业务
                    if (scroller.getScrollY() == 0) {
                        if (scrollViewListener != null) scrollViewListener.openAnim();
                    } else {
                        if (scrollViewListener != null) scrollViewListener.closeAnim();
                    }
                } else {
                    handler.sendMessageDelayed(handler.obtainMessage(touchEventId, scroller), 1);
                    lastY = scroller.getScrollY();
                }
            }
        }
    };
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downY = ev.getRawY();
                Log.e("down" , ev.getRawY() + "");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.e("move" , ev.getRawY() + "");
                break;
            case MotionEvent.ACTION_UP:
                Log.e("up" , ev.getRawY() + "");
                handler.sendMessageDelayed(handler.obtainMessage(touchEventId, this), 5);
                break;
        }
        return super.onTouchEvent(ev);
    }
}
