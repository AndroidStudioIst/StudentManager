package com.student.manager.iview

import android.content.pm.ActivityInfo
import android.graphics.Color
import android.view.LayoutInflater
import com.angcyo.uiview.RCrashHandler
import com.angcyo.uiview.container.ContentLayout
import com.angcyo.uiview.kotlin.clickIt
import com.angcyo.uiview.model.TitleBarPattern
import com.orhanobut.hawk.Hawk
import com.student.manager.BuildConfig
import com.student.manager.R
import com.student.manager.base.BaseContentUIView
import com.student.manager.control.UserControl

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2018/02/23 11:12
 * 修改人员：Robi
 * 修改时间：2018/02/23 11:12
 * 修改备注：
 * Version: 1.0.0
 */
class MainUIView : BaseContentUIView() {

    override fun getTitleBar(): TitleBarPattern {
        return super.getTitleBar()
                .setShowBackImageView(false)
                .setFloating(true)
                .setTitleBarBGColor(Color.TRANSPARENT)
                .setTitleString(when (UserControl.loginUserBean!!.type) {
                    2 -> UserControl.loginUserBean!!.name
                    3 -> "管理员 ${UserControl.loginUserBean!!.name}"
                    4 -> "超级管理员 ${UserControl.loginUserBean!!.name}"
                    else -> "${UserControl.loginUserBean!!.className} ${UserControl.loginUserBean!!.name}"
                })
//                .addRightItem(TitleBarItem("+") {
//                    startIView(MainUIView())
//                })
    }

    override fun getDefaultRequestedOrientation(): Int {
        return ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

    override fun inflateContentLayout(baseContentLayout: ContentLayout?, inflater: LayoutInflater?) {
        inflate(R.layout.activity_main)
    }

    override fun initOnShowContentLayout() {
        super.initOnShowContentLayout()

        if (BuildConfig.SHOW_DEBUG) {
            RCrashHandler.checkCrash(mParentILayout)
        }

        UserControl.loginUserBean?.let {
            when (it.type) {
                1 -> {
                    //学生
                    mViewHolder.gone(R.id.add_lesson)
                    mViewHolder.tv(R.id.add_teacher).text = "查看课程表"
                    click(R.id.add_teacher) {
                        startIView(AddTeacherUIView(false))
                    }
                }
                2 -> {
                    //老师
                    mViewHolder.tv(R.id.add_teacher).text = "添加课表"
                    click(R.id.add_teacher) {
                        startIView(AddTeacherUIView())
                    }


                    mViewHolder.tv(R.id.add_lesson).text = "查看班级课表"
                    mViewHolder.visible(R.id.add_lesson).clickIt {
                        startIView(VerifyClassUIView().apply {
                            isSeeClass = true
                            isAddClass = true
                        })
                    }

                    //申请实验课室
                    mViewHolder.visible(R.id.request_class).clickIt {
                        startIView(RequestClassUIView())
                    }
                }
                3, 4 -> {
                    //管理, 超级管理
                    mViewHolder.tv(R.id.verify_class).text = "审核课室"
                    mViewHolder.visible(R.id.verify_class).clickIt {
                        startIView(VerifyClassUIView())
                    }

                    mViewHolder.tv(R.id.add_teacher).text = "添加班级课程表"
                    click(R.id.add_teacher) {
                        val teacherUIView = AddTeacherUIView(false)
                        teacherUIView.isAddClass = true
                        startIView(teacherUIView)
                    }

                    //添加课程
                    mViewHolder.visible(R.id.add_lesson).clickIt {
                        startIView(AddLessonUIView())
                    }
                }
            }
        }

        //退出登录
        click(R.id.quit_button) {
            UserControl.loginUserBean = null
            Hawk.put("AUTO_LOGIN", false)
            replaceIView(LoginUIView())
        }
    }
}
