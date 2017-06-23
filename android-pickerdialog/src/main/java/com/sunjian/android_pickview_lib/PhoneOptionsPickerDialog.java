package com.sunjian.android_pickview_lib;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * 手机选项选择器
 * Created by sunjian on 2016/12/23.
 */

public class PhoneOptionsPickerDialog<A, B, C> extends BaseOptionsPickerDialog<A, B, C> {

    private TextView mBtnSubmit, mBtnCancel, mTvTitle;
    private View mBgView;

    public static <A, B, C> PhoneOptionsPickerDialog<A, B, C> newInstance() {
        return newInstance(null);
    }

    public static <A, B, C> PhoneOptionsPickerDialog<A, B, C> newInstance(Bundle bundle) {
        return newInstance(bundle, null);
    }

    public static <A, B, C> PhoneOptionsPickerDialog<A, B, C> newInstance(Bundle bundle, ArrayList<A> options1Items) {
        return newInstance(bundle, options1Items, null);
    }

    public static <A, B, C> PhoneOptionsPickerDialog<A, B, C> newInstance(
            Bundle bundle, ArrayList<A> options1Items, ArrayList<ArrayList<B>> options2Items) {
        return newInstance(bundle, options1Items, options2Items, null);
    }

    public static <A, B, C> PhoneOptionsPickerDialog<A, B, C> newInstance(
            ArrayList<A> options1Items, ArrayList<ArrayList<B>> options2Items,
            ArrayList<ArrayList<ArrayList<C>>> options3Items) {
        return newInstance(null, options1Items, options2Items, options3Items);
    }

    public static <A, B, C> PhoneOptionsPickerDialog<A, B, C> newInstance(
            Bundle bundle, ArrayList<A> options1Items, ArrayList<ArrayList<B>> options2Items,
            ArrayList<ArrayList<ArrayList<C>>> options3Items) {
        PhoneOptionsPickerDialog dialog = new PhoneOptionsPickerDialog();
        dialog.setArguments(bundle == null ? new Bundle() : bundle);
        dialog.mOptions1Items = options1Items;
        dialog.mOptions2Items = options2Items;
        dialog.mOptions3Items = options3Items;
        return dialog;
    }

    //设置样式
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_TITLE, com.sunjian.android_pickview_lib.R.style.PickerDialogStyle);
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
        mBgView = v.findViewById(R.id.bg_view);
        mBtnSubmit = (TextView) v.findViewById(R.id.btnSubmit);
        mBtnCancel = (TextView) v.findViewById(R.id.btnCancel);
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
    protected int getPickerViewId() {
        return R.id.phone_options_picker;
    }

    @Override
    protected int getResLayoutId() {
        return R.layout.pickerview_phone_options;
    }

    public TextView getTitleText() {
        return mTvTitle;
    }

    public TextView getLeftText() {
        return mBtnCancel;
    }

    public TextView getRightText() {
        return mBtnSubmit;
    }

    public View getBgView() {
        return mBgView;
    }
}
