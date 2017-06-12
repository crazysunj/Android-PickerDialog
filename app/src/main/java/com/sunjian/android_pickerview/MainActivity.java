package com.sunjian.android_pickerview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.sunjian.android_pickerview.bean.PickerViewData;
import com.sunjian.android_pickerview.bean.ProvinceBean;
import com.sunjian.android_pickview_lib.BaseDatePickerDialog;
import com.sunjian.android_pickview_lib.BaseOptionsPickerDialog;
import com.sunjian.android_pickview_lib.PhoneDatePickerDialog;
import com.sunjian.android_pickview_lib.PhoneOptionsPickerDialog;
import com.sunjian.android_pickview_lib.TVDatePickerDialog;
import com.sunjian.android_pickview_lib.TVOptionsPickerDialog;
import com.sunjian.android_pickview_lib.model.IPickerViewData;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private ArrayList<ProvinceBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<IPickerViewData>>> options3Items = new ArrayList<>();
    private PhoneDatePickerDialog phoneDatePickerDialog;
    private TVDatePickerDialog tvDatePickerDialog;
    private PhoneOptionsPickerDialog<ProvinceBean, String, IPickerViewData> phoneDialog;
    private TVOptionsPickerDialog<ProvinceBean, String, IPickerViewData> tvDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //选项1
        options1Items.add(new ProvinceBean(0,"广东","广东省，以岭南东道、广南东路得名","其他数据"));
        options1Items.add(new ProvinceBean(1,"湖南","湖南省地处中国中部、长江中游，因大部分区域处于洞庭湖以南而得名湖南","芒果TV"));
        options1Items.add(new ProvinceBean(3,"广西","嗯～～",""));

        //选项2
        ArrayList<String> options2Items_01=new ArrayList<>();
        options2Items_01.add("广州");
        options2Items_01.add("佛山");
        options2Items_01.add("东莞");
        options2Items_01.add("阳江");
        options2Items_01.add("珠海");
        ArrayList<String> options2Items_02=new ArrayList<>();
        options2Items_02.add("长沙");
        options2Items_02.add("岳阳");
        ArrayList<String> options2Items_03=new ArrayList<>();
        options2Items_03.add("桂林");
        options2Items.add(options2Items_01);
        options2Items.add(options2Items_02);
        options2Items.add(options2Items_03);

        //选项3
        ArrayList<ArrayList<IPickerViewData>> options3Items_01 = new ArrayList<>();
        ArrayList<ArrayList<IPickerViewData>> options3Items_02 = new ArrayList<>();
        ArrayList<ArrayList<IPickerViewData>> options3Items_03 = new ArrayList<>();
        ArrayList<IPickerViewData> options3Items_01_01=new ArrayList<>();
        options3Items_01_01.add(new PickerViewData("天河"));
        options3Items_01_01.add(new PickerViewData("黄埔"));
        options3Items_01_01.add(new PickerViewData("海珠"));
        options3Items_01_01.add(new PickerViewData("越秀"));
        options3Items_01.add(options3Items_01_01);
        ArrayList<IPickerViewData> options3Items_01_02=new ArrayList<>();
        options3Items_01_02.add(new PickerViewData("南海"));
        options3Items_01_02.add(new PickerViewData("高明"));
        options3Items_01_02.add(new PickerViewData("禅城"));
        options3Items_01_02.add(new PickerViewData("桂城"));
        options3Items_01.add(options3Items_01_02);
        ArrayList<IPickerViewData> options3Items_01_03=new ArrayList<>();
        options3Items_01_03.add(new PickerViewData("其他"));
        options3Items_01_03.add(new PickerViewData("常平"));
        options3Items_01_03.add(new PickerViewData("虎门"));
        options3Items_01.add(options3Items_01_03);
        ArrayList<IPickerViewData> options3Items_01_04=new ArrayList<>();
        options3Items_01_04.add(new PickerViewData("其他的"));
        options3Items_01_04.add(new PickerViewData("其他的"));
        options3Items_01_04.add(new PickerViewData("其他的"));
        options3Items_01.add(options3Items_01_04);
        ArrayList<IPickerViewData> options3Items_01_05=new ArrayList<>();

        options3Items_01_05.add(new PickerViewData("其他1"));
        options3Items_01_05.add(new PickerViewData("其他2"));
        options3Items_01.add(options3Items_01_05);

        ArrayList<IPickerViewData> options3Items_02_01=new ArrayList<>();

        options3Items_02_01.add(new PickerViewData("长沙1"));
        options3Items_02_01.add(new PickerViewData("长沙2"));
        options3Items_02_01.add(new PickerViewData("长沙3"));
        options3Items_02_01.add(new PickerViewData("长沙4"));
        options3Items_02_01.add(new PickerViewData("长沙5"));




        options3Items_02.add(options3Items_02_01);
        ArrayList<IPickerViewData> options3Items_02_02=new ArrayList<>();

        options3Items_02_02.add(new PickerViewData("岳阳"));
        options3Items_02_02.add(new PickerViewData("岳阳1"));
        options3Items_02_02.add(new PickerViewData("岳阳2"));
        options3Items_02_02.add(new PickerViewData("岳阳3"));
        options3Items_02_02.add(new PickerViewData("岳阳4"));
        options3Items_02_02.add(new PickerViewData("岳阳5"));

        options3Items_02.add(options3Items_02_02);
        ArrayList<IPickerViewData> options3Items_03_01=new ArrayList<>();
        options3Items_03_01.add(new PickerViewData("好山水"));
        options3Items_03.add(options3Items_03_01);

        options3Items.add(options3Items_01);
        options3Items.add(options3Items_02);
        options3Items.add(options3Items_03);

        Bundle bundle=new Bundle();
        bundle.putString(BaseOptionsPickerDialog.LABEL_FIRST,"省");
        bundle.putString(BaseOptionsPickerDialog.LABEL_SECOND,"市");
        bundle.putString(BaseOptionsPickerDialog.LABEL_THIRD,"区");

        phoneDatePickerDialog = PhoneDatePickerDialog.newInstance();
        phoneDatePickerDialog.setOnTimeSelectListener(new BaseDatePickerDialog.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date) {
                Toast.makeText(MainActivity.this, getTime(date), Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.tvPhoneTime).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneDatePickerDialog.show(getFragmentManager(),"PhoneDatePickerDialog");
            }
        });

        Bundle dateBundle=new Bundle();
        dateBundle.putInt(BaseDatePickerDialog.BASE_TEXT_SIZE,4);

        tvDatePickerDialog = TVDatePickerDialog.newInstance(dateBundle);
        tvDatePickerDialog.setOnTimeSelectListener(new BaseDatePickerDialog.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date) {
                Toast.makeText(MainActivity.this, getTime(date), Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.tvTime).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvDatePickerDialog.show(getFragmentManager(),"TVDatePickerDialog");
            }
        });

        phoneDialog = PhoneOptionsPickerDialog.newInstance(bundle, options1Items, options2Items, options3Items);
        phoneDialog.setOnoptionsSelectListener(new BaseOptionsPickerDialog.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                String address = options1Items.get(options1).getPickerViewText()
                        + options2Items.get(options1).get(option2)
                        + options3Items.get(options1).get(option2).get(options3).getPickerViewText();
                Toast.makeText(MainActivity.this, "你选择的地址是：" + address, Toast.LENGTH_SHORT).show();
            }
        });
        findViewById(R.id.tvPhoneOptions).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneDialog.show(getFragmentManager(),"PhoneOptionsPickerDialog");
            }
        });

        Bundle optionsBundle=new Bundle();
        optionsBundle.putInt(BaseOptionsPickerDialog.TEXT_SIZE,20);
        optionsBundle.putString(BaseOptionsPickerDialog.LABEL_FIRST,"省");
        optionsBundle.putString(BaseOptionsPickerDialog.LABEL_SECOND,"市");
        optionsBundle.putString(BaseOptionsPickerDialog.LABEL_THIRD,"区");

        tvDialog = TVOptionsPickerDialog.newInstance(optionsBundle, options1Items, options2Items, options3Items);
        tvDialog.setOnoptionsSelectListener(new BaseOptionsPickerDialog.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                String address = options1Items.get(options1).getPickerViewText()
                        + options2Items.get(options1).get(option2)
                        + options3Items.get(options1).get(option2).get(options3).getPickerViewText();
                Toast.makeText(MainActivity.this, "你选择的地址是：" + address, Toast.LENGTH_SHORT).show();
            }
        });
        findViewById(R.id.tvOptions).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvDialog.show(getFragmentManager(),"TVOptionsPickerDialog");
            }
        });
    }

    public static String getTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return format.format(date);
    }
}
