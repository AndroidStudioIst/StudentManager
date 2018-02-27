package com.student.manager.bean;

import android.text.TextUtils;

import java.util.List;

import cn.bmob.v3.BmobObject;

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2018/02/23 16:27
 * 修改人员：Robi
 * 修改时间：2018/02/23 16:27
 * 修改备注：
 * Version: 1.0.0
 */
public class TeacherBean extends BmobObject {
    private String name = "";

    /*第一周课程安排 0b1111111111 总共10节课*/
    private int w1 = 0b0;
    private int w2 = 0b0;
    private int w3 = 0b0;
    private int w4 = 0b0;
    private int w5 = 0b0;

    //多少个老师
    private transient List<RequestClassBean> requestList;
    //选中了那个老师
    private transient RequestClassBean selectorRequest;
    //是否是第一次赋值
    private transient boolean isFirst = true;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getW1() {
        return w1;
    }

    public void setW1(int w1) {
        this.w1 = w1;
    }

    public int getW2() {
        return w2;
    }

    public void setW2(int w2) {
        this.w2 = w2;
    }

    public int getW3() {
        return w3;
    }

    public void setW3(int w3) {
        this.w3 = w3;
    }

    public int getW4() {
        return w4;
    }

    public void setW4(int w4) {
        this.w4 = w4;
    }

    public int getW5() {
        return w5;
    }

    public void setW5(int w5) {
        this.w5 = w5;
    }

    public List<RequestClassBean> getRequestList() {
        return requestList;
    }

    public void setRequestList(List<RequestClassBean> requestList) {
        this.requestList = requestList;
    }

    public RequestClassBean getSelectorRequest() {
        return selectorRequest;
    }

    public void setSelectorRequest(RequestClassBean selectorRequest) {
        this.selectorRequest = selectorRequest;
    }

    public boolean isFirst() {
        return isFirst;
    }

    public void setFirst(boolean first) {
        isFirst = first;
    }

    @Override
    public boolean equals(Object obj) {
        return TextUtils.equals(this.getObjectId(), ((TeacherBean) obj).getObjectId());
    }
}
