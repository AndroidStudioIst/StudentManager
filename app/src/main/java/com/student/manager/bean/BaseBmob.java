package com.student.manager.bean;

import cn.bmob.v3.BmobObject;

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2018/02/24 17:41
 * 修改人员：Robi
 * 修改时间：2018/02/24 17:41
 * 修改备注：
 * Version: 1.0.0
 */
public class BaseBmob extends BmobObject {
    private String ex1 = "";
    private String ex2 = "";
    private String ex3 = "";

    private String name = "";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEx1() {
        return ex1;
    }

    public void setEx1(String ex1) {
        this.ex1 = ex1;
    }

    public String getEx2() {
        return ex2;
    }

    public void setEx2(String ex2) {
        this.ex2 = ex2;
    }

    public String getEx3() {
        return ex3;
    }

    public void setEx3(String ex3) {
        this.ex3 = ex3;
    }
}
