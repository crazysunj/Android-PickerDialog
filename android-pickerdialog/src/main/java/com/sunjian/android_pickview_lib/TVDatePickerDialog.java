package com.sunjian.android_pickview_lib;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

/**
 * 电视日期选择器
 * Created by sunjian on 2016/12/22.
 */

public class TVDatePickerDialog extends BaseDatePickerDialog {

    private TextView mBtnOK;

    public static TVDatePickerDialog newInstance() {
        return newInstance(null);
    }

    public static TVDatePickerDialog newInstance(Bundle bundle) {
        TVDatePickerDialog dialog = new TVDatePickerDialog();
        dialog.setArguments(bundle==null?new Bundle():bundle);
        return dialog;
    }

    //设置背景为透明样式，显示圆角
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.CircleDialogStyle);
    }

    @Override
    protected int getPickerViewId() {
        return R.id.timepicker;
    }

    @Override
    protected void initView(final View v) {
        mBtnOK = (TextView) v.findViewById(R.id.btnOK);

        mBtnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickSubmit(v);
            }
        });

        mBtnOK.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                mBtnOK.setTextColor(b ? Color.WHITE : Color.BLACK);
            }
        });
    }

    @Override
    protected void requestFocus() {
        mWheelTime.requestFocus();
    }
}
