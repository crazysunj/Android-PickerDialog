package com.sunjian.android_pickerview;

import android.os.Bundle;
import android.view.View;

import com.sunjian.android_pickerview.bean.ProvinceBean;
import com.sunjian.android_pickview_lib.PhoneOptionsPickerDialog;
import com.sunjian.android_pickview_lib.model.IPickerViewData;

import java.util.ArrayList;

/**
 * description
 * <p>
 * Created by sunjian on 2017/6/23.
 */

public class MyOptionsDialog extends PhoneOptionsPickerDialog<ProvinceBean, String, IPickerViewData> {

    public static MyOptionsDialog get(
            Bundle bundle, ArrayList<ProvinceBean> options1Items, ArrayList<ArrayList<String>> options2Items,
            ArrayList<ArrayList<ArrayList<IPickerViewData>>> options3Items) {

        MyOptionsDialog dialog = new MyOptionsDialog();
        dialog.setArguments(bundle == null ? new Bundle() : bundle);
        dialog.mOptions1Items = options1Items;
        dialog.mOptions2Items = options2Items;
        dialog.mOptions3Items = options3Items;
        return dialog;
    }

    @Override
    protected void initView(View v) {
        super.initView(v);
        mTvTitle.setText("ABC");
    }
}
