package com.student.manager.iview

import android.text.TextUtils
import android.view.Gravity
import android.view.View
import android.view.animation.Animation
import com.angcyo.uiview.base.Item
import com.angcyo.uiview.base.SingleItem
import com.angcyo.uiview.dialog.UIItemSelectorDialog
import com.angcyo.uiview.dialog.UILoading
import com.angcyo.uiview.model.AnimParam
import com.angcyo.uiview.model.TitleBarPattern
import com.angcyo.uiview.net.RLoadingSubscriber
import com.angcyo.uiview.recycler.RBaseViewHolder
import com.angcyo.uiview.resources.AnimUtil
import com.angcyo.uiview.utils.T_
import com.angcyo.uiview.utils.Tip
import com.angcyo.uiview.widget.ExEditText
import com.student.manager.R
import com.student.manager.base.BaseItemUIView
import com.student.manager.bean.UserBean
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
class RegisterUIView : BaseItemUIView() {

    val userBean = UserBean()

    override fun getTitleBar(): TitleBarPattern {
        return super.getTitleBar().setTitleGravity(Gravity.LEFT).setTitleString("欢迎注册")
    }

    override fun haveSoftInput(): Boolean {
        return true
    }

    override fun createItems(items: MutableList<SingleItem>) {
        items.add(object : SingleItem() {
            override fun onBindView(holder: RBaseViewHolder, posInData: Int, dataBean: Item?) {
                val name: ExEditText = holder.v(R.id.name_view)
                val pw: ExEditText = holder.v(R.id.pw_view)
                val pw2: ExEditText = holder.v(R.id.pw_view2)
                val cls: ExEditText = holder.v(R.id.class_view)

                holder.click(R.id.user_type) {
                    startIView(UIItemSelectorDialog(listOf("学生", "教师")).apply {
                        onItemSelector = { position, bean ->
                            userBean.type = position + 1
                            holder.tv(R.id.user_type).text = bean

                            if (position == 0) {
                                holder.visible(R.id.class_layout)
                            } else {
                                cls.setText("")
                                holder.gone(R.id.class_layout)
                            }
                        }
                    })
                }

                holder.v<View>(R.id.user_type).setOnLongClickListener {
                    startIView(UIItemSelectorDialog(listOf("学生", "教师", "管理员")).apply {
                        onItemSelector = { position, bean ->
                            userBean.type = position + 1
                            holder.tv(R.id.user_type).text = bean

                            if (position == 0) {
                                holder.visible(R.id.class_layout)
                            } else {
                                cls.setText("")
                                holder.gone(R.id.class_layout)
                            }
                        }
                    })
                    true
                }

                holder.click(R.id.register_button) {
                    if (name.checkEmpty() || pw.checkEmpty() || pw2.checkEmpty()) {
                        T_.error("请检查输入")
                    } else {
                        if (userBean.type == 1) {
                            if (cls.checkEmpty()) {
                                T_.error("请输入班级名")
                                return@click
                            }
                        }

                        if (TextUtils.equals(pw.string(), pw2.string())) {
                            UILoading.show2(mParentILayout).setLoadingTipText("正在注册")

                            add(UserControl.isUserExist(name.string()) {
                                if (it) {
                                    T_.error("用户已存在")
                                    UILoading.hide()
                                } else {
                                    userBean.apply {
                                        this.name = name.string()
                                        this.pw = pw.string()
                                        this.className = cls.string()
                                        add(saveObservable().subscribe(object : RLoadingSubscriber<String>(mParentILayout, "正在注册") {

                                            override fun onSucceed(bean: String?) {
                                                super.onSucceed(bean)
                                                Tip.tip("注册成功")
                                                UserControl.loginUserBean = this@apply
                                                mParentILayout.finishIView(LoginUIView::class.java)
                                                mParentILayout.replaceIView(AdminUIView())
                                            }

                                            override fun onError(code: Int, msg: String?) {
                                                super.onError(code, msg)
                                                Tip.tip("注册失败")
                                            }
                                        }))
                                    }
                                }
                            })
                        } else {
                            T_.error("两次密码不一致")
                        }
                    }
                }
            }

            override fun getItemLayoutId(): Int {
                return R.layout.item_register
            }

        })
    }

    override fun loadStartAnimation(animParam: AnimParam): Animation {
        return AnimUtil.translateStartAnimation()
    }

    override fun loadFinishAnimation(animParam: AnimParam): Animation {
        return AnimUtil.translateFinishAnimation()
    }

    override fun loadOtherEnterAnimation(animParam: AnimParam): Animation? {
        return null
    }


    override fun loadOtherExitAnimation(animParam: AnimParam): Animation? {
        return null
    }
}
