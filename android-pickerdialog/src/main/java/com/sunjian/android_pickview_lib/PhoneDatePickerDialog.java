package com.sunjian.android_pickview_lib;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

/**
 * 手机定制版日期选择器
 * Created by sunjian on 2016/12/22.
 */

public class PhoneDatePickerDialog extends BaseDatePickerDialog {

    protected TextView mBtnSubmit, mBtnCancel, mTvTitle;
    protected View mBgView;

    public static PhoneDatePickerDialog newInstance() {
        return newInstance(null);
    }

    public static PhoneDatePickerDialog newInstance(Bundle bundle) {
        PhoneDatePickerDialog dialog = new PhoneDatePickerDialog();
        dialog.setArguments(bundle == null ? new Bundle() : bundle);
        return dialog;
    }

    //设置样式
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_TITLE, R.style.PickerDialogStyle);
    }

    //实现从底部弹出
    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.BOTTOM;
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(params);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    @Override
    protected void initView(View v) {
        mBtnSubmit = (TextView) v.findViewById(R.id.btnSubmit);
        mBtnCancel = (TextView) v.findViewById(R.id.btnCancel);
        mBgView = v.findViewById(R.id.bg_view);
        mBtnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickSubmit(v);
            }
        });
        mBtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickCancel(v);
            }
        });
        //顶部标题
        mTvTitle = (TextView) v.findViewById(R.id.tvTitle);
    }

    @Override
    protected int getResLayoutId() {
        return R.layout.pickerview_phone_date;
    }

    @Override
    protected int getPickerViewId() {
        return R.id.phone_time_picker;
    }
}
