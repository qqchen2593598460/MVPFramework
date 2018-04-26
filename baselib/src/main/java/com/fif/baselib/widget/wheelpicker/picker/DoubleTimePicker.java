package com.fif.baselib.widget.wheelpicker.picker;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fif.baselib.R;
import com.fif.baselib.utils.DateUtils;
import com.fif.baselib.utils.UIUtils;
import com.fif.baselib.widget.wheelpicker.widget.WheelView;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;

/**
 * <p/>
 * Refactored by chen on 2017/08/17.
 */
public class DoubleTimePicker extends WheelPicker {
    /**
     * 不显示
     */
    public static final int NONE = -1;
    /**
     * 24小时
     */
    public static final int HOUR_24 = 3;
    /**
     * @deprecated use {@link #HOUR_24} instead
     */
    @Deprecated
    public static final int HOUR_OF_DAY = 3;
    /**
     * 12小时
     */
    public static final int HOUR_12 = 4;
    /**
     * @deprecated use {@link #HOUR_12} instead
     */
    @Deprecated
    public static final int HOUR = 4;

    private ArrayList<String> hours = new ArrayList<>();
    private ArrayList<String> minutes = new ArrayList<>();
    private String hourLabel = "时", minuteLabel = "分";
    private String selectedHour = "", selectedMinute = "",rightHour = "", rightMinute = "";
    private OnWheelListener onWheelListener;
    private OnTimePickListener onTimePickListener;
    private int timeMode = HOUR_24;
    private int startHour, startMinute = 0;
    private int endHour, endMinute = 59;
    private int textSize = WheelView.TEXT_SIZE;
    private boolean useWeight = false;
    private boolean resetWhileWheel = true;
    private Activity activty;
    @IntDef(value = {NONE, HOUR_24, HOUR_12})
    @Retention(RetentionPolicy.SOURCE)
    public @interface TimeMode {
    }

    /**
     * @see #HOUR_24
     * @see #HOUR_12
     */
    public DoubleTimePicker(Activity activity,@TimeMode int timeMode) {
        super(activity);
        this.activty = activity;
        //根据时间模式初始化小时范围
        if (timeMode == HOUR_12) {
            startHour = 1;
            endHour = 12;
        } else {
            startHour = 0;
            endHour = 23;
        }
        this.timeMode = timeMode;
    }

    /**
     * 是否使用比重来平分布局
     */
    public void setUseWeight(boolean useWeight) {
        this.useWeight = useWeight;
    }

    /**
     * 滚动时是否重置下一级的索引
     */
    public void setResetWhileWheel(boolean resetWhileWheel) {
        this.resetWhileWheel = resetWhileWheel;
    }


    /**
     * 设置范围：开始的时分
     */
    public void setTimeRangeStart(int startHour, int startMinute) {
        if (timeMode == NONE) {
            throw new IllegalArgumentException("Time mode invalid");
        }
        boolean illegal = false;
        if (startHour < 0 || startMinute < 0 || startMinute > 59) {
            illegal = true;
        }
        if (timeMode == HOUR_12 && (startHour == 0 || startHour > 12)) {
            illegal = true;
        }
        if (timeMode == HOUR_24 && startHour >= 24) {
            illegal = true;
        }
        if (illegal) {
            throw new IllegalArgumentException("Time out of range");
        }
        this.startHour = startHour;
        this.startMinute = startMinute;
        initHourData();
    }

    /**
     * 设置范围：结束的时分
     */
    public void setTimeRangeEnd(int endHour, int endMinute) {
        if (timeMode == NONE) {
            throw new IllegalArgumentException("Time mode invalid");
        }
        boolean illegal = false;
        if (endHour < 0 || endMinute < 0 || endMinute > 59) {
            illegal = true;
        }
        if (timeMode == HOUR_12 && (endHour == 0 || endHour > 12)) {
            illegal = true;
        }
        if (timeMode == HOUR_24 && endHour >= 24) {
            illegal = true;
        }
        if (illegal) {
            throw new IllegalArgumentException("Time out of range");
        }
        this.endHour = endHour;
        this.endMinute = endMinute;
        initHourData();
    }

    /**
     * 设置年月日时分的显示单位
     */
    public void setLabel(String hourLabel, String minuteLabel) {
        this.hourLabel = hourLabel;
        this.minuteLabel = minuteLabel;
    }

    /**
     * 设置默认选中的年月日时分
     */
    public void setSelectedItem(int hour, int minute) {
        if (timeMode != NONE) {
            selectedHour = DateUtils.fillZero(hour);
            selectedMinute = DateUtils.fillZero(minute);
            rightHour = DateUtils.fillZero(hour);
            rightMinute = DateUtils.fillZero(minute);
        }
    }

    public void setOnWheelListener(OnWheelListener onWheelListener) {
        this.onWheelListener = onWheelListener;
    }

    public void setOnDateTimePickListener(OnTimePickListener listener) {
        this.onTimePickListener = listener;
    }


    public String getSelectedHour() {
        if (timeMode != NONE) {
            return selectedHour;
        }
        return "";
    }

    public String getSelectedMinute() {
        if (timeMode != NONE) {
            return selectedMinute;
        }
        return "";
    }

    public String getRightHour() {
        if (timeMode != NONE) {
            return rightHour;
        }
        return "";
    }

    public String getRightMinute() {
        if (timeMode != NONE) {
            return rightMinute;
        }
        return "";
    }

    @SuppressLint("WrongConstant")
    @NonNull
    @Override
    protected View makeCenterView() {
        if (timeMode != NONE && hours.size() == 0) {
            initHourData();
        }
        if (timeMode != NONE && minutes.size() == 0) {
            changeMinuteData(DateUtils.trimZero(selectedHour));
        }
        if (timeMode != NONE && minutes.size() == 0) {
            changeMinuteData(DateUtils.trimZero(rightHour));
        }

        LinearLayout layout = new LinearLayout(activity);
        layout.setOrientation(LinearLayout.HORIZONTAL);
        layout.setGravity(Gravity.CENTER);
        final WheelView rightHourView = createWheelView();
        final WheelView rightMinuteView = createWheelView();
        final WheelView hourView = createWheelView();
        final WheelView minuteView = createWheelView();
        hourView.setTextSize(textSize);
        minuteView.setTextSize(textSize);
        rightHourView.setTextSize(textSize);
        rightMinuteView.setTextSize(textSize);
        hourView.setUseWeight(useWeight);
        minuteView.setUseWeight(useWeight);
        rightHourView.setUseWeight(useWeight);
        rightMinuteView.setUseWeight(useWeight);


        if (timeMode != NONE) {
            hourView.setLayoutParams(new LinearLayout.LayoutParams(0, WRAP_CONTENT, 1.0f));
            hourView.setItems(hours, selectedHour);
            //hourView.setLabel(hourLabel);
            hourView.setOnItemSelectListener(new WheelView.OnItemSelectListener() {
                @Override
                public void onSelected(int index) {
                    selectedHour = hours.get(index);
                    if (onWheelListener != null) {
                        onWheelListener.onHourWheeled(index, selectedHour);
                    }
                    changeMinuteData(DateUtils.trimZero(selectedHour));
                    minuteView.setItems(minutes, selectedMinute);
                }
            });
            layout.addView(hourView);
            if (!TextUtils.isEmpty(hourLabel)) {
                TextView labelView = createLabelView();
                labelView.setTextSize(textSize);
                labelView.setText(hourLabel);
                layout.addView(labelView);
            }

            minuteView.setLayoutParams(new LinearLayout.LayoutParams(0, WRAP_CONTENT, 1.0f));
            minuteView.setItems(minutes, selectedMinute);
            //minuteView.setLabel(minuteLabel);
            minuteView.setOnItemSelectListener(new WheelView.OnItemSelectListener() {
                @Override
                public void onSelected(int index) {
                    selectedMinute = minutes.get(index);
                    if (onWheelListener != null) {
                        onWheelListener.onMinuteWheeled(index, selectedMinute);
                    }
                }
            });
            layout.addView(minuteView);
            if (!TextUtils.isEmpty(minuteLabel)) {
                TextView labelView = createLabelView();
                labelView.setTextSize(textSize);
                labelView.setText(minuteLabel);
                layout.addView(labelView);
            }
        }
        //线条
        TextView textView = new TextView(activty);
        textView.setText("——");
        textView.setTextColor(ContextCompat.getColor(activty , R.color.main_color));
        textView.setPadding(UIUtils.dip2px(activty , 10) , 0 , 0 , 0);
        layout.addView(textView);
        if (timeMode != NONE) {
            rightHourView.setLayoutParams(new LinearLayout.LayoutParams(0, WRAP_CONTENT, 1.0f));
            rightHourView.setItems(hours, rightHour);
            //leftHourView.setLabel(hourLabel);
            rightHourView.setOnItemSelectListener(new WheelView.OnItemSelectListener() {
                @Override
                public void onSelected(int index) {
                    rightHour = hours.get(index);
                    if (onWheelListener != null) {
                        onWheelListener.onHourWheeled(index, rightHour);
                    }
                    changeMinuteData(DateUtils.trimZero(rightHour));
                    rightMinuteView.setItems(minutes, rightMinute);
                }
            });
            layout.addView(rightHourView);
            if (!TextUtils.isEmpty(hourLabel)) {
                TextView labelView = createLabelView();
                labelView.setTextSize(textSize);
                labelView.setText(hourLabel);
                layout.addView(labelView);
            }

            rightMinuteView.setLayoutParams(new LinearLayout.LayoutParams(0, WRAP_CONTENT, 1.0f));
            rightMinuteView.setItems(minutes, rightMinute);
            //leftMinuteView.setLabel(minuteLabel);
            rightMinuteView.setOnItemSelectListener(new WheelView.OnItemSelectListener() {
                @Override
                public void onSelected(int index) {
                    rightMinute = minutes.get(index);
                    if (onWheelListener != null) {
                        onWheelListener.onMinuteWheeled(index, rightMinute);
                    }
                }
            });
            layout.addView(rightMinuteView);
            if (!TextUtils.isEmpty(minuteLabel)) {
                TextView labelView = createLabelView();
                labelView.setTextSize(textSize);
                labelView.setText(minuteLabel);
                layout.addView(labelView);
            }
        }
        return layout;
    }

    @Override
    protected void onSubmit() {
        if (onTimePickListener == null) {
            return;
        }
        String hour = getSelectedHour().equals("")? "00":getSelectedHour();
        String minute = getSelectedMinute().equals("")? "00" : getSelectedMinute();
        String rightHour = getRightHour().equals("")? "00" : getRightHour();
        String rightMinute = getRightMinute().equals("")?"00":getRightMinute();
        onTimePickListener.onDateTimePicked(hour, minute , rightHour , rightMinute);
    }

    private int findItemIndex(ArrayList<String> items, int item) {
        //折半查找有序元素的索引
        int index = Collections.binarySearch(items, item, new Comparator<Object>() {
            @Override
            public int compare(Object lhs, Object rhs) {
                String lhsStr = lhs.toString();
                String rhsStr = rhs.toString();
                lhsStr = lhsStr.startsWith("0") ? lhsStr.substring(1) : lhsStr;
                rhsStr = rhsStr.startsWith("0") ? rhsStr.substring(1) : rhsStr;
                try {
                    return Integer.parseInt(lhsStr) - Integer.parseInt(rhsStr);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    return 0;
                }
            }
        });
        if (index < 0) {
            throw new IllegalArgumentException("Item[" + item + "] out of range");
        }
        return index;
    }



    @SuppressLint("WrongConstant")
    private void initHourData() {
        int currentHour = 0;
        if (!resetWhileWheel) {
            if (timeMode == HOUR_24) {
                currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
            } else {
                currentHour = Calendar.getInstance().get(Calendar.HOUR);
            }
        }
        for (int i = startHour; i <= endHour; i++) {
            String hour = DateUtils.fillZero(i);
            if (!resetWhileWheel) {
                if (i == currentHour) {
                    selectedHour = hour;
                }
            }
            hours.add(hour);
        }
        if (hours.indexOf(selectedHour) == -1) {
            //当前设置的小时不在指定范围，则默认选中范围开始的小时
            selectedHour = hours.get(0);
        }
        if (!resetWhileWheel) {
            selectedMinute = DateUtils.fillZero(Calendar.getInstance().get(Calendar.MINUTE));
        }
    }

    private void changeMinuteData(int selectedHour) {
        if (startHour == endHour) {
            if (startMinute > endMinute) {
                int temp = startMinute;
                startMinute = endMinute;
                endMinute = temp;
            }
            for (int i = startMinute; i <= endMinute; i++) {
                minutes.add(DateUtils.fillZero(i));
            }
        } else if (selectedHour == startHour) {
            for (int i = startMinute; i <= 59; i++) {
                minutes.add(DateUtils.fillZero(i));
            }
        } else if (selectedHour == endHour) {
            for (int i = 0; i <= endMinute; i++) {
                minutes.add(DateUtils.fillZero(i));
            }
        } else {
            for (int i = 0; i <= 59; i++) {
                minutes.add(DateUtils.fillZero(i));
            }
        }
        if (minutes.indexOf(selectedMinute) == -1) {
            //当前设置的分钟不在指定范围，则默认选中范围开始的分钟
            selectedMinute = minutes.get(0);
        }
    }

    public interface OnWheelListener {

        void onHourWheeled(int index, String hour);

        void onMinuteWheeled(int index, String minute);

    }


    public interface OnTimePickListener{

        void onDateTimePicked(String hour, String minute, String rightHour , String rightMinute);
    }

}
