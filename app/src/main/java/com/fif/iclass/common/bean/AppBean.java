package com.fif.iclass.common.bean;

import android.graphics.Bitmap;

/**
 * Created by chen on 2017/5/11.
 */

public class AppBean {

    private String appName;
    private boolean isAdded;
    private Bitmap appicon;

    public AppBean() {
    }

    public AppBean(String appName, boolean isAdded, Bitmap appicon) {

        this.appName = appName;
        this.isAdded = isAdded;
        this.appicon = appicon;
    }

    public boolean isAdded() {
        return isAdded;
    }

    public void setAdded(boolean added) {
        isAdded = added;
    }

    public Bitmap getAppicon() {
        return appicon;
    }

    public void setAppicon(Bitmap appicon) {
        this.appicon = appicon;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }
}
