# Android-PickerView
pickerview
### 这是一个兼容手机和电视(焦点选择)的高仿IOS风格的选择器。


### Gradle依赖

```
compile 'com.sunjian:android-pickview-lib:1.0.0'
```

示例图：

* 手机日期

![](https://github.com/crazysunj/Android-PickerView/blob/master/picture/phoneDate.png)

* 手机选项(根据集合显示，这里以地址为例)

![](https://github.com/crazysunj/Android-PickerView/blob/master/picture/optionsPhone .png)

* 电视日期(红色表示聚焦，颜色可修改)

![](https://github.com/crazysunj/Android-PickerView/blob/master/picture/TVDate.png)

* 电视选项

![](https://github.com/crazysunj/Android-PickerView/blob/master/picture/TVOptions.png)

#### 几个小用法

* 若继承已有对话框，想调整布局的位置，不改变布局的数量，只需要重写

```
	/**
     * 如果想调整布局可通过继承，重写该方法，但是id不能变
     * 若需求较为复杂，请自己自定义dialog
     */
    @LayoutRes
    protected int getResLayoutId() {
        return R.layout.pickerview_tv_options;
    }
```

* 若继承基类，请重写以下方法，当然id是固定的，具体自己看，哈哈。

```
	//关于wheelview的id都是规定的,year,month,day,hour,min
    @IdRes
    protected abstract int getPickerViewId();
```

* WheelView支持以下属性

```
//位置，支持左中右
<attr name="gravity">
     <enum name="center" value="17"/>
     <enum name="left" value="3"/>
     <enum name="right" value="5"/>
</attr>
//字体大小
<attr name="textSize" format="dimension"/>
//上下字体颜色
<attr name="textColorOut" format="color"/>
//中间字体颜色
<attr name="textColorCenter" format="color"/>
//聚焦字体颜色，用于聚焦类APP
<attr name="highLight_textColorCenter" format="color"/>
//分割线颜色
<attr name="dividerColor" format="color"/>
//是否用于聚焦
<attr name="isRequestFocus" format="boolean"/>
//是否循环
<attr name="isCyclic" format="boolean"/>
//单位
<attr name="lable" format="string"/>
```

* 初始化都以传bundle的形式传入，对话框都是继承于dialogfragment，方便管理。


### 好了，大家有问题直接在github上提问吧，也可以直接加我QQ387953660，喜欢的朋友赏颗星星吧！

### [博客地址：http://crazysunj.com/](http://crazysunj.com/)

### License

> ```
> Copyright 2016 Sun Jian
>
> Licensed under the Apache License, Version 2.0 (the "License");
> you may not use this file except in compliance with the License.
> You may obtain a copy of the License at
>
>    http://www.apache.org/licenses/LICENSE-2.0
>
> Unless required by applicable law or agreed to in writing, software
> distributed under the License is distributed on an "AS IS" BASIS,
> WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
> See the License for the specific language governing permissions and
> limitations under the License.
> ```
