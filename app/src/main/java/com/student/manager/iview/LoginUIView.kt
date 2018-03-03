package com.student.manager.iview

import android.view.Gravity
import com.angcyo.uiview.RCrashHandler
import com.angcyo.uiview.base.Item
import com.angcyo.uiview.base.SingleItem
import com.angcyo.uiview.dialog.UILoading
import com.angcyo.uiview.model.TitleBarPattern
import com.angcyo.uiview.recycler.RBaseViewHolder
import com.angcyo.uiview.utils.T_
import com.angcyo.uiview.widget.ExEditText
import com.orhanobut.hawk.Hawk
import com.student.manager.BuildConfig
import com.student.manager.R
import com.student.manager.base.BaseItemUIView
import com.student.manager.control.UserControl

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2018/02/23 18:23
 * 修改人员：Robi
 * 修改时间：2018/02/23 18:23
 * 修改备注：
 * Version: 1.0.0
 */
class LoginUIView : BaseItemUIView() {

    override fun getTitleBar(): TitleBarPattern {
        return super.getTitleBar()
                .setShowBackImageView(false)
                .setTitleGravity(Gravity.LEFT)
                .setTitleString("请先登录")
    }

    override fun createItems(items: MutableList<SingleItem>) {
        items.add(object : SingleItem() {
            override fun onBindView(holder: RBaseViewHolder, posInData: Int, dataBean: Item?) {
                val name: ExEditText = holder.v(R.id.name_view)
                val pw: ExEditText = holder.v(R.id.pw_view)

                name.setInputText(Hawk.get("NAME", ""))

                holder.click(R.id.register_button) {
                    startIView(RegisterUIView())
                }
                holder.click(R.id.login_button) {

                    //                    if (BuildConfig.DEBUG) {
//                        val i = 1 / 0
//                    }

                    if (name.checkEmpty() || pw.checkEmpty()) {
                        T_.error("请检查输入")
                    } else {
                        UILoading.show2(mParentILayout).setLoadingTipText("正在登录")
                        UserControl.loginUser(name.string(), pw.string()) {
                            UILoading.hide()
                            if (it == null) {
                                T_.error("用户不存在或密码错误")
                            } else {
                                Hawk.put("AUTO_LOGIN", holder.cV(R.id.auto_login_cb).isChecked)

                                mParentILayout.replaceIView(AdminUIView())
//                                when (it.type) {
//                                    3, 4 -> mParentILayout.replaceIView(AdminUIView())
//
//                                    else -> mParentILayout.replaceIView(MainUIView())
//                                }

                            }
                        }
                    }

                }
            }

            override fun getItemLayoutId(): Int {
                return R.layout.item_login
            }

        })
    }

    override fun initOnShowContentLayout() {
        super.initOnShowContentLayout()
        if (BuildConfig.SHOW_DEBUG) {
            RCrashHandler.checkCrash(mParentILayout)
        }
    }

}
