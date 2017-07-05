package com.sunjian.android_pickview_lib;


import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.sunjian.android_pickview_lib.adapter.WheelTime;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

/**
 * 封装基类日期dialog为dialogfragment，方便生命周期的管理
 * Created by sunjian on 2016/12/22.
 */
public abstract class BaseDatePickerDialog extends DialogFragment {

    public static final String START_YEAR = "START_YEAR";//开始年份
    public static final String END_YEAR = "END_YEAR";//结束年份
    public static final String IS_CYCLIC = "IS_CYCLIC";//是否循环
    public static final String INIT_DATE = "INIT_DATE";//初始化日期
    public static final String SAVE_DATE = "SAVE_DATE";//保存日期
    public static final String DATE_TYPE = "DATE_TYPE";//日期类型
    //基础字体大小
    public static final String BASE_TEXT_SIZE = "BASE_TEXT_SIZE";
    public static final String START_TIME_MILLIS = "START_TIME_MILLIS";
    public static final String START_TIME_DATE = "START_TIME_DATE";
    public static final String START_TIME_CALENDAR = "START_TIME_CALENDAR";
    public static final String END_TIME_MILLIS = "END_TIME_MILLIS";
    public static final String END_TIME_DATE = "END_TIME_DATE";
    public static final String END_TIME_CALENDAR = "END_TIME_CALENDAR";

    protected WheelTime mWheelTime;
    protected OnTimeSelectListener mTimeSelectListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(getResLayoutId(), container, false);
        init(v, savedInstanceState);
        return v;
    }

    private void init(View v, Bundle savedInstanceState) {

        Bundle bundle = getArguments();

        int startYear = bundle.getInt(START_YEAR, -1);

        int endYear = bundle.getInt(END_YEAR, -1);

        boolean isCyclic = bundle.getBoolean(IS_CYCLIC, false);

        @WheelTime.DateType int type = bundle.getInt(DATE_TYPE, WheelTime.ALL);

        int baseTextSize = bundle.getInt(BASE_TEXT_SIZE, 6);

        Date date;

        if (savedInstanceState != null) {
            //恢复数据
            try {
                String dateStr = savedInstanceState.getString(SAVE_DATE);
                date = WheelTime.dateFormat.parse(dateStr);
            } catch (ParseException e) {
                date = null;
                e.printStackTrace();
            }
        } else {
            date = (Date) bundle.getSerializable(INIT_DATE);
        }

        initView(v);

        // ----时间转轮
        final View timepickerview = v.findViewById(getPickerViewId());
        mWheelTime = new WheelTime(timepickerview, type, baseTextSize);

        //设置时间范围
        if (startYear != -1) {
            mWheelTime.setStartYear(startYear);
        }

        if (endYear != -1) {
            mWheelTime.setEndYear(endYear);
        }

        long startTimeMillils = bundle.getLong(START_TIME_MILLIS, -1);
        if (startTimeMillils != -1) {
            mWheelTime.setStartDate(startTimeMillils);
        }

        long endTimeMillils = bundle.getLong(END_TIME_MILLIS, -1);
        if (endTimeMillils != -1) {
            mWheelTime.setEndDate(endTimeMillils);
        }

        Date startTimeDate = (Date) bundle.getSerializable(START_TIME_DATE);
        if (startTimeDate != null) {
            mWheelTime.setStartDate(startTimeDate);
        }

        Date endTimeDate = (Date) bundle.getSerializable(END_TIME_DATE);
        if (endTimeDate != null) {
            mWheelTime.setEndDate(endTimeDate);
        }

        Calendar startTimeCal = (Calendar) bundle.getSerializable(START_TIME_CALENDAR);
        if (startTimeCal != null) {
            mWheelTime.setStartDate(startTimeCal);
        }

        Calendar endTimeCal = (Calendar) bundle.getSerializable(END_TIME_CALENDAR);
        if (endTimeCal != null) {
            mWheelTime.setEndDate(endTimeCal);
        }

        //设置选中时间
        Calendar calendar = Calendar.getInstance();
        if (date == null) {
            calendar.setTimeInMillis(System.currentTimeMillis());
        } else {
            calendar.setTime(date);
        }
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hours = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        mWheelTime.setPicker(year, month, day, hours, minute);

        //获取焦点
        requestFocus();

        //设置是否循环
        mWheelTime.setCyclic(isCyclic);
    }

    protected void requestFocus() {

    }

    protected void initView(View v) {

    }

    @IdRes
    protected abstract int getPickerViewId();

    //信息保存
    @Override
    public void onSaveInstanceState(Bundle outState) {
        //保存数据
        outState.putString(SAVE_DATE, mWheelTime.getTime());
        super.onSaveInstanceState(outState);
    }

    /**
     * 如果想调整布局可通过继承，重写该方法
     * 若需求较为复杂，请自己自定义dialog
     */
    @LayoutRes
    protected abstract int getResLayoutId();

    public interface OnTimeSelectListener {
        void onTimeSelect(Date date);
    }

    public void setOnTimeSelectListener(OnTimeSelectListener timeSelectListener) {
        this.mTimeSelectListener = timeSelectListener;
    }

    public void clickSubmit(View v) {
        if (mTimeSelectListener != null) {
            try {
                Date date = WheelTime.dateFormat.parse(mWheelTime.getTime());
                mTimeSelectListener.onTimeSelect(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        dismiss();
    }

    public void clickCancel(View v) {
        dismiss();
    }
}
