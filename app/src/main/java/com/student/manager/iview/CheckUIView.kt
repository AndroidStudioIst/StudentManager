package com.student.manager.iview

import android.graphics.Color
import android.text.TextUtils
import com.angcyo.bmob.RBmob
import com.angcyo.uiview.dialog.UIItemSelectorDialog
import com.angcyo.uiview.kotlin.stringSize
import com.angcyo.uiview.model.TitleBarPattern
import com.angcyo.uiview.recycler.RBaseViewHolder
import com.angcyo.uiview.skin.SkinHelper
import com.angcyo.uiview.utils.RUtils
import com.angcyo.uiview.utils.Tip
import com.student.manager.R
import com.student.manager.bean.CheckBean
import com.student.manager.bean.UserBean
import com.student.manager.control.UserControl

/**
 * 考勤管理
 * Created by angcyo on 2018-03-03.
 */

class CheckUIView : TeacherCheckUIView() {

    override fun getTitleBar(): TitleBarPattern {
        return super.getTitleBar().setTitleString(if (UserControl.loginUserBean!!.type == 1) {
            "个人考勤情况"
        } else {
            if (isStudentSeeCheckIt) {
                "${studentSeeBean!!.name} 考勤情况"
            } else {
                "考勤管理"
            }
        })
    }

    //所有用户
    val allUserList = mutableListOf<UserBean>()

    //班级考勤情况
    val allCheckList = mutableListOf<CheckBean>()

    override fun onShowContentData() {
        //super.onShowContentData()
        //查询所有学生
        RBmob.query<UserBean>(UserBean::class.java, "") {
            if (!RUtils.isListEmpty(it)) {
                allUserList.addAll(it)
            }

            //所有考勤情况
            RBmob.query<CheckBean>(CheckBean::class.java, "") {
                if (!RUtils.isListEmpty(it)) {
                    allCheckList.addAll(it)
                }
                super.onShowContentData()
            }
        }
    }

    /**根据用户id, 返回对应的用户名*/
    private fun getUserName(objectId: String): String {
        for (bean in allUserList) {
            if (TextUtils.equals(bean.objectId, objectId)) {
                return bean.name
            }
        }
        return ""
    }

    private fun getCurrentCheckBean(): CheckBean {
        for (bean in allCheckList) {
            if (TextUtils.equals(bean.name, selectorClassName)) {
                return bean
            }
        }
        return CheckBean()
    }

    override fun initStudent(holder: RBaseViewHolder, position: Int) {
        //super.initStudent(holder, position)
        if (checkEmptyClass() || TextUtils.isEmpty(selectorClassName)) {
            return
        }

        val rowIndex = position / 6 - 1//横向第几行

        /*设置背景色*/
        fun setBg(list: List<String>, count: Int, users: String /*那些学生打卡了*/) {
            if (list[rowIndex].isEmpty()) {
                holder.itemView.setBackgroundColor(Color.TRANSPARENT)
                holder.tv(R.id.text_view).text = ""
            } else {
                holder.tv(R.id.text_view).setTextColor(Color.WHITE)

                if (isStudentSeeCheck()) {
                    val objectId = if (isStudentSeeCheckIt) {
                        studentSeeBean!!.objectId
                    } else {
                        UserControl.loginUserBean!!.objectId
                    }

                    holder.tv(R.id.text_view).text = if (users.contains(objectId)) {
                        holder.itemView.setBackgroundColor(SkinHelper.getSkin().themeSubColor)
                        "已打卡 ${list[rowIndex]}"
                    } else {
                        holder.itemView.setBackgroundColor(getColor(R.color.base_dark_red_tran))
                        "缺席 ${list[rowIndex]}"
                    }
                } else {
                    holder.itemView.setBackgroundColor(SkinHelper.getSkin().themeSubColor)
                    holder.tv(R.id.text_view).text = "${count}人 ${list[rowIndex]}"
                }
            }
        }

        val checkBean = getCurrentCheckBean()

        /*显示打卡人数*/
        fun toCode(users: String) {
            //startIView(CodeUIView(studentClassBean, lessonName, "$position"))
            val userNameList = mutableListOf<String>()
            var list = RUtils.split(users, ":", false)
            for (u in list) {
                userNameList.add(getUserName(u))
            }

            if (userNameList.isEmpty()) {
                Tip.tip("暂无学生打卡")
            } else {
                startIView(UIItemSelectorDialog(userNameList).apply {
                    titleString = "以下学生已打卡"
                })
            }
        }

        when (position.rem(6)) {
            1 -> {//周一
                val wList = studentClassBean.w1List()
                val users = checkBean.w1List()[rowIndex]
                setBg(wList, RUtils.split(users, ":", false).stringSize(true), users)
                holder.clickItem {
                    if (isSeeClass || wList[rowIndex].isEmpty()) {
                        return@clickItem
                    }
                    toCode(users)
                }
            }
            2 -> {
                val wList = studentClassBean.w2List()
                val users = checkBean.w2List()[rowIndex]
                setBg(wList, RUtils.split(users, ":", false).stringSize(true), users)
                holder.clickItem {
                    if (isSeeClass || wList[rowIndex].isEmpty()) {
                        return@clickItem
                    }
                    toCode(users)
                }
            }
            3 -> {
                val wList = studentClassBean.w3List()
                val users = checkBean.w3List()[rowIndex]
                setBg(wList, RUtils.split(users, ":", false).stringSize(true), users)
                holder.clickItem {
                    if (isSeeClass || wList[rowIndex].isEmpty()) {
                        return@clickItem
                    }
                    toCode(users)
                }
            }
            4 -> {
                val wList = studentClassBean.w4List()
                val users = checkBean.w4List()[rowIndex]
                setBg(wList, RUtils.split(users, ":", false).stringSize(true), users)
                holder.clickItem {
                    if (isSeeClass || wList[rowIndex].isEmpty()) {
                        return@clickItem
                    }
                    toCode(users)
                }
            }
            5 -> {
                val wList = studentClassBean.w5List()
                val users = checkBean.w5List()[rowIndex]
                setBg(wList, RUtils.split(users, ":", false).stringSize(true), users)
                holder.clickItem {
                    if (isSeeClass || wList[rowIndex].isEmpty()) {
                        return@clickItem
                    }
                    toCode(users)
                }
            }
        }
    }

    /*是否是学生自己查看自己的考勤情况*/
    private fun isStudentSeeCheck(): Boolean {
        return (UserControl.isStudent() && isSeeClass) || isStudentSeeCheckIt
    }
}
