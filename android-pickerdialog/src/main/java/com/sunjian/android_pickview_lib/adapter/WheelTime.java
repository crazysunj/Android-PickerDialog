package com.sunjian.android_pickview_lib.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.IntDef;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.view.View;

import com.sunjian.android_pickview_lib.listener.OnItemSelectedListener;
import com.sunjian.android_pickview_lib.view.WheelView;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class WheelTime {

    public static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
    private View view;
    private WheelView wv_year;
    private WheelView wv_month;
    private WheelView wv_day;
    private WheelView wv_hours;
    private WheelView wv_mins;

    //    private Type type;
    private static final int DEFULT_START_YEAR = 1990;
    private static final int DEFULT_END_YEAR = 2100;
    private static final int DEFAULT_START_MONTH = 1;
    private static final int DEFAULT_END_MONTH = 12;
    private static final int DEFAULT_START_DAY = 1;
    private static final int DEFAULT_END_DAY = 31;


    private int startYear = DEFULT_START_YEAR;
    private int endYear = DEFULT_END_YEAR;
    private int startMonth = DEFAULT_START_MONTH;
    private int endMonth = DEFAULT_END_MONTH;
    private int startDay = DEFAULT_START_DAY;
    private int endDay = DEFAULT_END_DAY; //表示31天的
    private int currentYear;

    // 五种选择模式，年月日时分，年月日，时分，月日时分，年月
    public static final int ALL = 0;
    public static final int YEAR_MONTH_DAY = 1;
    public static final int HOURS_MINS = 2;
    public static final int MONTH_DAY_HOUR_MIN = 3;
    public static final int YEAR_MONTH = 4;

    @IntDef({ALL, YEAR_MONTH_DAY, HOURS_MINS, MONTH_DAY_HOUR_MIN, YEAR_MONTH})
    @Retention(RetentionPolicy.SOURCE)
    public @interface DateType {
    }

    @DateType
    private int type;

    private int textSize = 6;

    public WheelTime(View view) {
        this(view, ALL, 6);
    }

    public WheelTime(View view, @DateType int type) {
        this(view, type, 6);
    }

    public WheelTime(View view, @DateType int type, int textSize) {
        this.view = view;
        this.type = type;
        this.textSize = textSize;
        setView(view);
    }

    public void setPicker(int year, int month, int day) {
        this.setPicker(year, month, day, 0, 0);
    }

    public void setPicker(int year, int month, int day, int h, int m) {
        // 添加大小月月份并将其转换为list,方便之后的判断
        String[] months_big = {"1", "3", "5", "7", "8", "10", "12"};
        String[] months_little = {"4", "6", "9", "11"};

        final List<String> list_big = Arrays.asList(months_big);
        final List<String> list_little = Arrays.asList(months_little);

        Context context = view.getContext();
        Resources resources = context.getResources();
        String packageName = context.getPackageName();
        int yearId = resources.getIdentifier("year", "id", packageName);
        currentYear = year;
        // 年
        wv_year = (WheelView) view.findViewById(yearId);
        wv_year.setAdapter(new NumericWheelAdapter(startYear, endYear));// 设置"年"的显示数据
        wv_year.setCurrentItem(year - startYear);// 初始化时显示的数据

        int monthId = resources.getIdentifier("month", "id", packageName);
        // 月
        wv_month = (WheelView) view.findViewById(monthId);
        if (startYear == endYear) {//开始年等于终止年
            wv_month.setAdapter(new NumericWheelAdapter(startMonth, endMonth));
            wv_month.setCurrentItem(month + 1 - startMonth);
        } else if (year == startYear) {
            //起始日期的月份控制
            wv_month.setAdapter(new NumericWheelAdapter(startMonth, 12));
            wv_month.setCurrentItem(month + 1 - startMonth);
        } else if (year == endYear) {
            //终止日期的月份控制
            wv_month.setAdapter(new NumericWheelAdapter(1, endMonth));
            wv_month.setCurrentItem(month);
        } else {
            wv_month.setAdapter(new NumericWheelAdapter(1, 12));
            wv_month.setCurrentItem(month);
        }
        int dayId = resources.getIdentifier("day", "id", packageName);
        // 日
        wv_day = (WheelView) view.findViewById(dayId);
        if (startYear == endYear && startMonth == endMonth) {
            if (list_big.contains(String.valueOf(month + 1))) {
                if (endDay > 31) {
                    endDay = 31;
                }
                wv_day.setAdapter(new NumericWheelAdapter(startDay, endDay));
            } else if (list_little.contains(String.valueOf(month + 1))) {
                if (endDay > 30) {
                    endDay = 30;
                }
                wv_day.setAdapter(new NumericWheelAdapter(startDay, endDay));
            } else {
                // 闰年
                if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
                    if (endDay > 29) {
                        endDay = 29;
                    }
                    wv_day.setAdapter(new NumericWheelAdapter(startDay, endDay));
                } else {
                    if (endDay > 28) {
                        endDay = 28;
                    }
                    wv_day.setAdapter(new NumericWheelAdapter(startDay, endDay));
                }
            }
            wv_day.setCurrentItem(day - startDay);
        } else if (year == startYear && month + 1 == startMonth) {
            // 起始日期的天数控制
            if (list_big.contains(String.valueOf(month + 1))) {

                wv_day.setAdapter(new NumericWheelAdapter(startDay, 31));
            } else if (list_little.contains(String.valueOf(month + 1))) {

                wv_day.setAdapter(new NumericWheelAdapter(startDay, 30));
            } else {
                // 闰年
                if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {

                    wv_day.setAdapter(new NumericWheelAdapter(startDay, 29));
                } else {

                    wv_day.setAdapter(new NumericWheelAdapter(startDay, 28));
                }
            }
            wv_day.setCurrentItem(day - startDay);
        } else if (year == endYear && month + 1 == endMonth) {
            // 终止日期的天数控制
            if (list_big.contains(String.valueOf(month + 1))) {
                if (endDay > 31) {
                    endDay = 31;
                }
                wv_day.setAdapter(new NumericWheelAdapter(1, endDay));
            } else if (list_little.contains(String.valueOf(month + 1))) {
                if (endDay > 30) {
                    endDay = 30;
                }
                wv_day.setAdapter(new NumericWheelAdapter(1, endDay));
            } else {
                // 闰年
                if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
                    if (endDay > 29) {
                        endDay = 29;
                    }
                    wv_day.setAdapter(new NumericWheelAdapter(1, endDay));
                } else {
                    if (endDay > 28) {
                        endDay = 28;
                    }
                    wv_day.setAdapter(new NumericWheelAdapter(1, endDay));
                }
            }
            wv_day.setCurrentItem(day - 1);
        } else {
            // 判断大小月及是否闰年,用来确定"日"的数据
            if (list_big.contains(String.valueOf(month + 1))) {

                wv_day.setAdapter(new NumericWheelAdapter(1, 31));
            } else if (list_little.contains(String.valueOf(month + 1))) {

                wv_day.setAdapter(new NumericWheelAdapter(1, 30));
            } else {
                // 闰年
                if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {

                    wv_day.setAdapter(new NumericWheelAdapter(1, 29));
                } else {

                    wv_day.setAdapter(new NumericWheelAdapter(1, 28));
                }
            }
            wv_day.setCurrentItem(day - 1);
        }

        int hourId = resources.getIdentifier("hour", "id", packageName);
        wv_hours = (WheelView) view.findViewById(hourId);
        wv_hours.setAdapter(new NumericWheelAdapter(0, 23));
        wv_hours.setCurrentItem(h);

        int minId = resources.getIdentifier("min", "id", packageName);
        wv_mins = (WheelView) view.findViewById(minId);
        wv_mins.setAdapter(new NumericWheelAdapter(0, 59));
        wv_mins.setCurrentItem(m);

        // 添加"年"监听
        OnItemSelectedListener wheelListener_year = new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                int year_num = index + startYear;
                currentYear = year_num;
                int currentMonthItem = wv_month.getCurrentItem();//记录上一次的item位置
                // 判断大小月及是否闰年,用来确定"日"的数据
                if (startYear == endYear) {
                    //重新设置月份
                    wv_month.setAdapter(new NumericWheelAdapter(startMonth, endMonth));

                    if (currentMonthItem > wv_month.getAdapter().getItemsCount() - 1) {
                        currentMonthItem = wv_month.getAdapter().getItemsCount() - 1;
                        wv_month.setCurrentItem(currentMonthItem);
                    }

                    int monthNum = currentMonthItem + startMonth;

                    if (startMonth == endMonth) {
                        //重新设置日
                        setReDay(year_num, monthNum, startDay, endDay, list_big, list_little);
                    } else if (monthNum == startMonth) {
                        //重新设置日
                        setReDay(year_num, monthNum, startDay, 31, list_big, list_little);
                    } else {
                        //重新设置日
                        setReDay(year_num, monthNum, 1, 31, list_big, list_little);
                    }
                } else if (year_num == startYear) {//等于开始的年
                    //重新设置月份
                    wv_month.setAdapter(new NumericWheelAdapter(startMonth, 12));

                    if (currentMonthItem > wv_month.getAdapter().getItemsCount() - 1) {
                        currentMonthItem = wv_month.getAdapter().getItemsCount() - 1;
                        wv_month.setCurrentItem(currentMonthItem);
                    }

                    int month = currentMonthItem + startMonth;
                    if (month == startMonth) {

                        //重新设置日
                        setReDay(year_num, month, startDay, 31, list_big, list_little);
                    } else {
                        //重新设置日

                        setReDay(year_num, month, 1, 31, list_big, list_little);
                    }

                } else if (year_num == endYear) {
                    //重新设置月份
                    wv_month.setAdapter(new NumericWheelAdapter(1, endMonth));
                    if (currentMonthItem > wv_month.getAdapter().getItemsCount() - 1) {
                        currentMonthItem = wv_month.getAdapter().getItemsCount() - 1;
                        wv_month.setCurrentItem(currentMonthItem);
                    }
                    int monthNum = currentMonthItem + 1;

                    if (monthNum == endMonth) {
                        //重新设置日
                        setReDay(year_num, monthNum, 1, endDay, list_big, list_little);
                    } else {
                        //重新设置日
                        setReDay(year_num, monthNum, 1, 31, list_big, list_little);
                    }

                } else {
                    //重新设置月份
                    wv_month.setAdapter(new NumericWheelAdapter(1, 12));
                    //重新设置日
                    setReDay(year_num, wv_month.getCurrentItem() + 1, 1, 31, list_big, list_little);

                }

            }
        };
        // 添加"月"监听
        OnItemSelectedListener wheelListener_month = new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                int month_num = index + 1;

                if (startYear == endYear) {
                    month_num = month_num + startMonth - 1;
                    if (startMonth == endMonth) {
                        //重新设置日
                        setReDay(currentYear, month_num, startDay, endDay, list_big, list_little);
                    } else if (startMonth == month_num) {

                        //重新设置日
                        setReDay(currentYear, month_num, startDay, 31, list_big, list_little);
                    } else if (endMonth == month_num) {
                        setReDay(currentYear, month_num, 1, endDay, list_big, list_little);
                    } else {
                        setReDay(currentYear, month_num, 1, 31, list_big, list_little);
                    }
                } else if (currentYear == startYear) {
                    month_num = month_num + startMonth - 1;
                    if (month_num == startMonth) {
                        //重新设置日
                        setReDay(currentYear, month_num, startDay, 31, list_big, list_little);
                    } else {
                        //重新设置日
                        setReDay(currentYear, month_num, 1, 31, list_big, list_little);
                    }

                } else if (currentYear == endYear) {
                    if (month_num == endMonth) {
                        //重新设置日
                        setReDay(currentYear, wv_month.getCurrentItem() + 1, 1, endDay, list_big, list_little);
                    } else {
                        setReDay(currentYear, wv_month.getCurrentItem() + 1, 1, 31, list_big, list_little);
                    }

                } else {
                    //重新设置日
                    setReDay(currentYear, month_num, 1, 31, list_big, list_little);

                }


            }
        };
        wv_year.setOnItemSelectedListener(wheelListener_year);
        wv_month.setOnItemSelectedListener(wheelListener_month);

        // 根据屏幕密度来指定选择器字体的大小(不同屏幕可能不同)
        switch (type) {
            case ALL:
                textSize = textSize * 3;
                break;
            case YEAR_MONTH_DAY:
                textSize = textSize * 4;
                wv_hours.setVisibility(View.GONE);
                wv_mins.setVisibility(View.GONE);
                break;
            case HOURS_MINS:
                textSize = textSize * 4;
                wv_year.setVisibility(View.GONE);
                wv_month.setVisibility(View.GONE);
                wv_day.setVisibility(View.GONE);
                break;
            case MONTH_DAY_HOUR_MIN:
                textSize = textSize * 3;
                wv_year.setVisibility(View.GONE);
                break;
            case YEAR_MONTH:
                textSize = textSize * 4;
                wv_day.setVisibility(View.GONE);
                wv_hours.setVisibility(View.GONE);
                wv_mins.setVisibility(View.GONE);
        }
        wv_day.setTextSize(textSize);
        wv_month.setTextSize(textSize);
        wv_year.setTextSize(textSize);
        wv_hours.setTextSize(textSize);
        wv_mins.setTextSize(textSize);

    }

    private void setReDay(int year_num, int monthNum, int startD, int endD, List<String> list_big, List<String> list_little) {
        int currentItem = wv_day.getCurrentItem();

        int maxItem;
        if (list_big
                .contains(String.valueOf(monthNum))) {
            if (endD > 31) {
                endD = 31;
            }
            wv_day.setAdapter(new NumericWheelAdapter(startD, endD));
            maxItem = endD;
        } else if (list_little.contains(String.valueOf(monthNum))) {
            if (endD > 30) {
                endD = 30;
            }
            wv_day.setAdapter(new NumericWheelAdapter(startD, endD));
            maxItem = endD;
        } else {
            if ((year_num % 4 == 0 && year_num % 100 != 0)
                    || year_num % 400 == 0) {
                if (endD > 29) {
                    endD = 29;
                }
                wv_day.setAdapter(new NumericWheelAdapter(startD, endD));
                maxItem = endD;
            } else {
                if (endD > 28) {
                    endD = 28;
                }
                wv_day.setAdapter(new NumericWheelAdapter(startD, endD));
                maxItem = endD;
            }
        }

        if (currentItem > wv_day.getAdapter().getItemsCount() - 1) {
            currentItem = wv_day.getAdapter().getItemsCount() - 1;
            wv_day.setCurrentItem(currentItem);
        }

    }

    //获取焦点
    public void requestFocus() {
        wv_year.requestFocus();
    }

    /**
     * 设置是否循环滚动
     *
     * @param cyclic
     */
    public void setCyclic(boolean cyclic) {
        wv_year.setCyclic(cyclic);
        wv_month.setCyclic(cyclic);
        wv_day.setCyclic(cyclic);
        wv_hours.setCyclic(cyclic);
        wv_mins.setCyclic(cyclic);
    }

    public String getTime() {
        StringBuilder sb = new StringBuilder();
        if (currentYear == startYear) {
            if ((wv_month.getCurrentItem() + startMonth) == startMonth) {
                sb.append((wv_year.getCurrentItem() + startYear)).append("-")
                        .append((wv_month.getCurrentItem() + startMonth)).append("-")
                        .append((wv_day.getCurrentItem() + startDay)).append(" ")
                        .append(wv_hours.getCurrentItem()).append(":")
                        .append(wv_mins.getCurrentItem());
            } else {
                sb.append((wv_year.getCurrentItem() + startYear)).append("-")
                        .append((wv_month.getCurrentItem() + startMonth)).append("-")
                        .append((wv_day.getCurrentItem() + 1)).append(" ")
                        .append(wv_hours.getCurrentItem()).append(":")
                        .append(wv_mins.getCurrentItem());
            }
        } else {
            sb.append((wv_year.getCurrentItem() + startYear)).append("-")
                    .append((wv_month.getCurrentItem() + 1)).append("-")
                    .append((wv_day.getCurrentItem() + 1)).append(" ")
                    .append(wv_hours.getCurrentItem()).append(":")
                    .append(wv_mins.getCurrentItem());
        }

        return sb.toString();
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public int getStartYear() {
        return startYear;
    }

    public void setStartYear(int startYear) {
        this.startYear = startYear;
    }

    public int getEndYear() {
        return endYear;
    }

    public void setEndYear(int endYear) {
        this.endYear = endYear;
    }

    public void setEndDate(@IntRange(from = 0) long endTime) {

        Calendar endDate = Calendar.getInstance();
        endDate.setTime(new Date(endTime));
        int year = endDate.get(Calendar.YEAR);
        int month = endDate.get(Calendar.MONTH) + 1;
        int day = endDate.get(Calendar.DAY_OF_MONTH);

        if (year > startYear) {
            this.endYear = year;
            this.endMonth = month;
            this.endDay = day;
        } else if (year == startYear) {
            if (month > startMonth) {
                this.endYear = year;
                this.endMonth = month;
                this.endDay = day;
            } else if (month == startMonth) {
                if (day > startDay) {
                    this.endYear = year;
                    this.endMonth = month;
                    this.endDay = day;
                }
            }
        }
    }

    public void setEndDate(@NonNull Date endTime) {

        Calendar endDate = Calendar.getInstance();
        endDate.setTime(endTime);
        int year = endDate.get(Calendar.YEAR);
        int month = endDate.get(Calendar.MONTH) + 1;
        int day = endDate.get(Calendar.DAY_OF_MONTH);

        if (year > startYear) {
            this.endYear = year;
            this.endMonth = month;
            this.endDay = day;
        } else if (year == startYear) {
            if (month > startMonth) {
                this.endYear = year;
                this.endMonth = month;
                this.endDay = day;
            } else if (month == startMonth) {
                if (day > startDay) {
                    this.endYear = year;
                    this.endMonth = month;
                    this.endDay = day;
                }
            }
        }
    }

    public void setEndDate(@NonNull Calendar endDate) {

        int year = endDate.get(Calendar.YEAR);
        int month = endDate.get(Calendar.MONTH) + 1;
        int day = endDate.get(Calendar.DAY_OF_MONTH);

        if (year > startYear) {
            this.endYear = year;
            this.endMonth = month;
            this.endDay = day;
        } else if (year == startYear) {
            if (month > startMonth) {
                this.endYear = year;
                this.endMonth = month;
                this.endDay = day;
            } else if (month == startMonth) {
                if (day > startDay) {
                    this.endYear = year;
                    this.endMonth = month;
                    this.endDay = day;
                }
            }
        }
    }

    public void setStartDate(@IntRange(from = 0) long startTime) {

        Calendar startDate = Calendar.getInstance();
        startDate.setTime(new Date(startTime));
        int year = startDate.get(Calendar.YEAR);
        int month = startDate.get(Calendar.MONTH) + 1;
        int day = startDate.get(Calendar.DAY_OF_MONTH);

        if (year < endYear) {
            this.startYear = year;
            this.startMonth = month;
            this.startDay = day;
        } else if (year == endYear) {
            if (month < endMonth) {
                this.startYear = year;
                this.startMonth = month;
                this.startDay = day;
            } else if (month == endMonth) {
                if (day < endDay) {
                    this.startYear = year;
                    this.startMonth = month;
                    this.startDay = day;
                }
            }
        }
    }

    public void setStartDate(@NonNull Date startTime) {

        Calendar startDate = Calendar.getInstance();
        startDate.setTime(startTime);
        int year = startDate.get(Calendar.YEAR);
        int month = startDate.get(Calendar.MONTH) + 1;
        int day = startDate.get(Calendar.DAY_OF_MONTH);

        if (year < endYear) {
            this.startYear = year;
            this.startMonth = month;
            this.startDay = day;
        } else if (year == endYear) {
            if (month < endMonth) {
                this.startYear = year;
                this.startMonth = month;
                this.startDay = day;
            } else if (month == endMonth) {
                if (day < endDay) {
                    this.startYear = year;
                    this.startMonth = month;
                    this.startDay = day;
                }
            }
        }
    }

    public void setStartDate(@NonNull Calendar startDate) {

        int year = startDate.get(Calendar.YEAR);
        int month = startDate.get(Calendar.MONTH) + 1;
        int day = startDate.get(Calendar.DAY_OF_MONTH);

        if (year < endYear) {
            this.startYear = year;
            this.startMonth = month;
            this.startDay = day;
        } else if (year == endYear) {
            if (month < endMonth) {
                this.startYear = year;
                this.startMonth = month;
                this.startDay = day;
            } else if (month == endMonth) {
                if (day < endDay) {
                    this.startYear = year;
                    this.startMonth = month;
                    this.startDay = day;
                }
            }
        }
    }

    public void setRangeDate(@NonNull Date startTime, @NonNull Date endTime) {

        if (startTime.getTime() < endTime.getTime()) {
            Calendar startDate = Calendar.getInstance();
            startDate.setTime(startTime);

            Calendar endDate = Calendar.getInstance();
            endDate.setTime(endTime);

            this.startYear = startDate.get(Calendar.YEAR);
            this.endYear = endDate.get(Calendar.YEAR);
            this.startMonth = startDate.get(Calendar.MONTH) + 1;
            this.endMonth = endDate.get(Calendar.MONTH) + 1;
            this.startDay = startDate.get(Calendar.DAY_OF_MONTH);
            this.endDay = endDate.get(Calendar.DAY_OF_MONTH);
        }
    }

    public void setRangeDate(@NonNull Calendar startDate, @NonNull Calendar endDate) {

        if (startDate.getTimeInMillis() < endDate.getTimeInMillis()) {
            this.startYear = startDate.get(Calendar.YEAR);
            this.endYear = endDate.get(Calendar.YEAR);
            this.startMonth = startDate.get(Calendar.MONTH) + 1;
            this.endMonth = endDate.get(Calendar.MONTH) + 1;
            this.startDay = startDate.get(Calendar.DAY_OF_MONTH);
            this.endDay = endDate.get(Calendar.DAY_OF_MONTH);
        }
    }

    public void setRangeDate(@IntRange(from = 0) long startTime, @IntRange(from = 0) long endTime) {

        if (startTime < endTime) {
            Calendar startDate = Calendar.getInstance();
            startDate.setTime(new Date(startTime));

            Calendar endDate = Calendar.getInstance();
            endDate.setTime(new Date(endTime));

            this.startYear = startDate.get(Calendar.YEAR);
            this.endYear = endDate.get(Calendar.YEAR);
            this.startMonth = startDate.get(Calendar.MONTH) + 1;
            this.endMonth = endDate.get(Calendar.MONTH) + 1;
            this.startDay = startDate.get(Calendar.DAY_OF_MONTH);
            this.endDay = endDate.get(Calendar.DAY_OF_MONTH);
        }
    }
}
