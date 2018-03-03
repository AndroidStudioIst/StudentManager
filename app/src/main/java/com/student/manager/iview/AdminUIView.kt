package com.student.manager.iview

import com.angcyo.uiview.base.Item
import com.angcyo.uiview.base.SingleItem
import com.angcyo.uiview.kotlin.clickIt
import com.angcyo.uiview.model.TitleBarPattern
import com.angcyo.uiview.recycler.RBaseViewHolder
import com.orhanobut.hawk.Hawk
import com.student.manager.R
import com.student.manager.base.BaseItemUIView
import com.student.manager.control.UserControl

/**
 * 管理员界面
 * Created by angcyo on 2018-03-03.
 */
class AdminUIView : BaseItemUIView() {

    override fun getTitleBar(): TitleBarPattern? {
        return null
    }

    override fun createItems(items: MutableList<SingleItem>) {
        items.add(object : SingleItem() {
            override fun onBindView(holder: RBaseViewHolder, posInData: Int, dataBean: Item?) {
                when (UserControl.loginUserBean!!.type) {
                    3, 4 -> loadAdminLayout(holder)
                }
            }

            override fun getItemLayoutId(): Int {
                return R.layout.view_admin_layout
            }

        })
    }

    private fun loadAdminLayout(holder: RBaseViewHolder) {
        holder.tv(R.id.button_11).apply {
            text = "考勤管理"
            clickIt {
                startIView(CheckUIView())
            }
        }
        holder.tv(R.id.button_21).apply {
            text = "学生管理"
            clickIt {
                startIView(AllStudentUIView())
            }
        }
        holder.tv(R.id.button_22).apply {
            text = "班级管理"
            clickIt {
                startIView(AllClassUIView())
            }
        }
        holder.tv(R.id.button_31).apply {
            text = "退出登录"
            clickIt {
                UserControl.loginUserBean = null
                Hawk.put("AUTO_LOGIN", false)
                replaceIView(LoginUIView())
            }
        }
    }

}
