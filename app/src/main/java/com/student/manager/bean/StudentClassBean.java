package com.student.manager.bean;

import android.text.TextUtils;

import com.angcyo.uiview.utils.RUtils;

import java.util.ArrayList;

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：班级课表
 * 创建人员：Robi
 * 创建时间：2018/02/24 17:36
 * 修改人员：Robi
 * 修改时间：2018/02/24 17:36
 * 修改备注：
 * Version: 1.0.0
 */
public class StudentClassBean extends BaseBmob {
    private String w1 = ",,,,,,,,,";
    private String w2 = ",,,,,,,,,";
    private String w3 = ",,,,,,,,,";
    private String w4 = ",,,,,,,,,";
    private String w5 = ",,,,,,,,,";


    public ArrayList<String> w1List() {
        return RUtils.split(w1, ",", true);
    }

    public ArrayList<String> w2List() {
        return RUtils.split(w2, ",", true);
    }

    public ArrayList<String> w3List() {
        return RUtils.split(w3, ",", true);
    }

    public ArrayList<String> w4List() {
        return RUtils.split(w4, ",", true);
    }

    public ArrayList<String> w5List() {
        return RUtils.split(w5, ",", true);
    }

    public boolean w1ListHave(int index) {
        return !TextUtils.isEmpty(RUtils.split(w1, ",", true).get(index));
    }

    public String getW1() {
        return w1;
    }

    public void setW1(String w1) {
        this.w1 = w1;
    }

    public String getW2() {
        return w2;
    }

    public void setW2(String w2) {
        this.w2 = w2;
    }

    public String getW3() {
        return w3;
    }

    public void setW3(String w3) {
        this.w3 = w3;
    }

    public String getW4() {
        return w4;
    }

    public void setW4(String w4) {
        this.w4 = w4;
    }

    public String getW5() {
        return w5;
    }

    public void setW5(String w5) {
        this.w5 = w5;
    }
}
