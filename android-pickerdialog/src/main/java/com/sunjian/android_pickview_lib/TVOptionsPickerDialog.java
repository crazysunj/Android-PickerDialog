package com.sunjian.android_pickview_lib;

import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

/**
 * 手机选项选择器
 * Created by sunjian on 2016/12/23.
 */

public class TVOptionsPickerDialog<A, B, C> extends BaseOptionsPickerDialog<A, B, C> {

    private View mBtnOK;

    public static <A, B, C> TVOptionsPickerDialog<A, B, C> newInstance() {
        return newInstance(null);
    }

    public static <A, B, C> TVOptionsPickerDialog<A, B, C> newInstance(Bundle bundle) {
        return newInstance(bundle, null);
    }

    public static <A, B, C> TVOptionsPickerDialog<A, B, C> newInstance(Bundle bundle, ArrayList<A> options1Items) {
        return newInstance(bundle, options1Items, null);
    }

    public static <A, B, C> TVOptionsPickerDialog<A, B, C> newInstance(
            Bundle bundle, ArrayList<A> options1Items, ArrayList<ArrayList<B>> options2Items) {
        return newInstance(bundle, options1Items, options2Items, null);
    }

    public static <A, B, C> TVOptionsPickerDialog<A, B, C> newInstance(
            ArrayList<A> options1Items, ArrayList<ArrayList<B>> options2Items,
            ArrayList<ArrayList<ArrayList<C>>> options3Items) {
        return newInstance(null, options1Items, options2Items, options3Items);
    }

    public static <A, B, C> TVOptionsPickerDialog<A, B, C> newInstance(
            Bundle bundle, ArrayList<A> options1Items, ArrayList<ArrayList<B>> options2Items,
            ArrayList<ArrayList<ArrayList<C>>> options3Items) {
        TVOptionsPickerDialog<A, B, C> dialog = new TVOptionsPickerDialog<>();
        dialog.setArguments(bundle == null ? new Bundle() : bundle);
        dialog.mOptions1Items = options1Items;
        dialog.mOptions2Items = options2Items;
        dialog.mOptions3Items = options3Items;
        return dialog;
    }

    //设置背景为透明样式，显示圆角
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.CircleDialogStyle);
    }

    @Override
    protected void requestFocus() {
        mWheelOptions.requestFocus();
    }

    @Override
    protected void initView(View v) {
        mBtnOK = v.findViewById(R.id.btnOK);
        mBtnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickSubmit(v);
            }
        });
    }

    @Override
    protected int getPickerViewId() {
        return R.id.tv_options_picker;
    }

    @Override
    protected int getResLayoutId() {
        return R.layout.pickerview_tv_options;
    }
}
