package com.student.manager.bean;

import com.angcyo.uiview.dialog.UIItemSelectorDialog;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by angcyo on 2018/05/06 09:22
 * 班级
 */
public class ClassBean extends BaseBmob implements UIItemSelectorDialog.IGetString {

    /**
     * 班级中所有的学生
     */
    private transient List<UserBean> mUserBeanList = new ArrayList<>();

    public List<UserBean> getUserBeanList() {
        return mUserBeanList;
    }

    public void setUserBeanList(List<UserBean> userBeanList) {
        mUserBeanList = userBeanList;
    }

    @NotNull
    @Override
    public String getString() {
        return getName();
    }
}
