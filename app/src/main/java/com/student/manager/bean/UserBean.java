package com.student.manager.bean;

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：用户表
 * 创建人员：Robi
 * 创建时间：2018/02/24 15:46
 * 修改人员：Robi
 * 修改时间：2018/02/24 15:46
 * 修改备注：
 * Version: 1.0.0
 */
public class UserBean extends BaseBmob {
    private String pw = "";
    private int level = 1; //用户等级, 用来做权限控制
    private int type = 1; //用户类型, 角色区分   1:学生 2:老师 3:管理 4:超级
    private String className = "";//班级名称

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }


}
