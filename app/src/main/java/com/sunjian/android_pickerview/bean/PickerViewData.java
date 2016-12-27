package com.sunjian.android_pickerview.bean;


import com.sunjian.android_pickview_lib.model.IPickerViewData;

/**
 *
 * @author sunjian
 * create at 2016/12/24 下午3:58
 */
public class PickerViewData implements IPickerViewData {
    private String content;

    public PickerViewData(String content) {
        this.content = content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String getPickerViewText() {
        return content;
    }
}
