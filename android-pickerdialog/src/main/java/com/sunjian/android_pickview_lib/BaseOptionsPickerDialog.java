package com.sunjian.android_pickview_lib;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.sunjian.android_pickview_lib.adapter.WheelOptions;

import java.util.ArrayList;

/**
 * 集合选项选择器基类
 * Created by sunjian on 2016/12/22.
 */

public abstract class BaseOptionsPickerDialog<A, B, C> extends DialogFragment {

    //单位
    public static final String LABEL_FIRST = "LABEL_FIRST";
    public static final String LABEL_SECOND = "LABEL_SECOND";
    public static final String LABEL_THIRD = "LABEL_THIRD";
    //循环
    public static final String CYCLIC_FIRST = "CYCLIC_FIRST";
    public static final String CYCLIC_SECOND = "CYCLIC_SECOND";
    public static final String CYCLIC_THIRD = "CYCLIC_THIRD";
    //挑选位置
    public static final String SELECT_FIRST = "SELECT_FIRST";
    public static final String SELECT_SECOND = "SELECT_SECOND";
    public static final String SELECT_THIRD = "SELECT_THIRD";
    //字体大小
    public static final String TEXT_SIZE = "TEXT_SIZE";

    //联动
    public static final String LINKAGE = "LINKAGE";

    protected WheelOptions<A, B, C> mWheelOptions;

    private OnOptionsSelectListener mOptionsSelectListener;

    protected ArrayList<A> mOptions1Items;
    protected ArrayList<ArrayList<B>> mOptions2Items;
    protected ArrayList<ArrayList<ArrayList<C>>> mOptions3Items;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(getResLayoutId(), container, false);
        init(v, savedInstanceState);
        return v;
    }

    private void init(View v, Bundle savedInstanceState) {

        Bundle bundle = getArguments();

        int selectFirst;
        int selectSecond;
        int selectThird;

        String firstLabel = bundle.getString(LABEL_FIRST);
        String secondLabel = bundle.getString(LABEL_SECOND);
        String thirdLabel = bundle.getString(LABEL_THIRD);

        boolean firstCyclic = bundle.getBoolean(CYCLIC_FIRST, false);
        boolean secondCyclic = bundle.getBoolean(CYCLIC_SECOND, false);
        boolean thirdCyclic = bundle.getBoolean(CYCLIC_THIRD, false);

        boolean linkage = bundle.getBoolean(LINKAGE, true);

        int textSize = bundle.getInt(TEXT_SIZE, 25);

        //恢复数据
        if (savedInstanceState != null) {
            selectFirst = savedInstanceState.getInt(SELECT_FIRST, 0);
            selectSecond = savedInstanceState.getInt(SELECT_SECOND, 0);
            selectThird = savedInstanceState.getInt(SELECT_THIRD, 0);
        } else {
            selectFirst = bundle.getInt(SELECT_FIRST, 0);
            selectSecond = bundle.getInt(SELECT_SECOND, 0);
            selectThird = bundle.getInt(SELECT_THIRD, 0);
        }

        initView(v);

        // ----转轮
        final View optionspicker = v.findViewById(getPickerViewId());
        mWheelOptions = new WheelOptions<A, B, C>(optionspicker, textSize);

        mWheelOptions.setPicker(mOptions1Items, mOptions2Items, mOptions3Items, linkage);

        //获取焦点
        requestFocus();

        mWheelOptions.setLabels(firstLabel, secondLabel, thirdLabel);

        mWheelOptions.setCyclic(firstCyclic, secondCyclic, thirdCyclic);

        if (selectFirst != 0 || selectSecond != 0 || selectThird != 0) {
            mWheelOptions.setCurrentItems(selectFirst, selectSecond, selectThird);
        }

    }

    protected void requestFocus() {

    }

    /**
     * @param v 初始化view
     */
    protected abstract void initView(View v);

    //信息保存
    @Override
    public void onSaveInstanceState(Bundle outState) {
        //保存数据
        int[] optionsCurrentItems = mWheelOptions.getCurrentItems();
        outState.putInt(SELECT_FIRST, optionsCurrentItems[0]);
        outState.putInt(SELECT_SECOND, optionsCurrentItems[1]);
        outState.putInt(SELECT_THIRD, optionsCurrentItems[2]);
        super.onSaveInstanceState(outState);
    }

    /**
     * 关于wheelview的id都是规定的, year, month, day, hour, min
     *
     * @return pickerview的id
     */
    @IdRes
    protected abstract int getPickerViewId();

    /**
     * 如果想调整布局可通过继承，重写该方法，但是id不能变
     * 若需求较为复杂，请自己自定义dialog
     *
     * @return 资源Id
     */
    @LayoutRes
    protected abstract int getResLayoutId();


    /**
     * 以下方法不能作为初始化使用，即创建页面后使用
     */
    public void setPicker(ArrayList<A> optionsItems) {
        if (mWheelOptions == null) {
            return;
        }
        mWheelOptions.setPicker(optionsItems, null, null, false);
    }

    public void setPicker(ArrayList<A> options1Items,
                          ArrayList<ArrayList<B>> options2Items, boolean linkage) {
        if (mWheelOptions == null) {
            return;
        }
        mWheelOptions.setPicker(options1Items, options2Items, null, linkage);
    }

    public void setPicker(ArrayList<A> options1Items,
                          ArrayList<ArrayList<B>> options2Items,
                          ArrayList<ArrayList<ArrayList<C>>> options3Items,
                          boolean linkage) {
        if (mWheelOptions == null) {
            return;
        }
        mWheelOptions.setPicker(options1Items, options2Items, options3Items,
                linkage);
    }

    /**
     * 设置选中的item位置
     */
    public void setSelectOptions(int option1) {
        if (mWheelOptions == null) {
            return;
        }
        mWheelOptions.setCurrentItems(option1, 0, 0);
    }

    /**
     * 设置选中的item位置
     */
    public void setSelectOptions(int option1, int option2) {
        if (mWheelOptions == null) {
            return;
        }
        mWheelOptions.setCurrentItems(option1, option2, 0);
    }

    /**
     * 设置选中的item位置
     */
    public void setSelectOptions(int option1, int option2, int option3) {
        if (mWheelOptions == null) {
            return;
        }
        mWheelOptions.setCurrentItems(option1, option2, option3);
    }

    public interface OnOptionsSelectListener {
        void onOptionsSelect(int options1, int option2, int options3);
    }

    public void setOnoptionsSelectListener(OnOptionsSelectListener optionsSelectListener) {
        this.mOptionsSelectListener = optionsSelectListener;
    }

    public void clickSubmit(View v) {
        if (mOptionsSelectListener != null) {
            int[] optionsCurrentItems = mWheelOptions.getCurrentItems();
            mOptionsSelectListener.onOptionsSelect(optionsCurrentItems[0], optionsCurrentItems[1], optionsCurrentItems[2]);
        }
        dismiss();
    }

    public void clickCancel(View v) {
        dismiss();
    }
}
