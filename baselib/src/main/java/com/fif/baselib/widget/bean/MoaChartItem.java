package com.fif.baselib.widget.bean;

import android.graphics.RectF;

/**
 * Created by chen
 */
public class MoaChartItem {

    protected int color;
    protected double value;
    public String remark;
    public int remarkTextColor;
    public float startAngle;//每个扇形的开始角度
    public float sweepAngle;//每个扇形的角度大小
    public RectF rectF;

    public MoaChartItem(double value, int color, int remarkTextColor) {
        this.color = color;
        this.value = value;
        this.remarkTextColor = remarkTextColor;
        if(value < 0){
            value = 0;
        }
    }

    public void setValue(double value) {
        this.value = value <= 0 ? 1f : value;
    }

    public double getValue() {
        return value;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
