package com.student.manager.iview

import android.graphics.Color
import android.text.TextUtils
import com.angcyo.uiview.model.TitleBarPattern
import com.angcyo.uiview.recycler.RBaseViewHolder
import com.angcyo.uiview.skin.SkinHelper
import com.student.manager.R
import com.student.manager.control.UserControl

/**
 * 班级课程管理
 * Created by angcyo on 2018-03-03.
 */
class TeacherCheckUIView : ClassLessonUIView() {

    init {
    }

    override fun getTitleBar(): TitleBarPattern {
        return super.getTitleBar().setTitleString("开始考勤")
    }

    override fun initStudent(holder: RBaseViewHolder, position: Int) {
        //super.initStudent(holder, position)
        if (checkEmptyClass() || TextUtils.isEmpty(selectorClassName)) {
            return
        }

        val rowIndex = position / 6 - 1//横向第几行

        fun setBg(list: List<String>) {
            if (list[rowIndex].isEmpty()) {
                holder.itemView.setBackgroundColor(Color.TRANSPARENT)
                holder.tv(R.id.text_view).text = ""
            } else {
                holder.itemView.setBackgroundColor(SkinHelper.getSkin().themeSubColor)
                holder.tv(R.id.text_view).text = list[rowIndex]
                holder.tv(R.id.text_view).setTextColor(Color.WHITE)
            }
        }

        fun toCode(lessonName: String) {
            startIView(CodeUIView(studentClassBean, lessonName, "$position"))
        }

        when (position.rem(6)) {
            1 -> {//周一
                val wList = studentClassBean.w1List()
                setBg(wList)
                holder.clickItem {
                    if (isSeeClass || wList[rowIndex].isEmpty()) {
                        return@clickItem
                    }
                    toCode(wList[rowIndex])
                }
            }
            2 -> {
                val wList = studentClassBean.w2List()
                setBg(wList)
                holder.clickItem {
                    if (isSeeClass || wList[rowIndex].isEmpty()) {
                        return@clickItem
                    }
                    toCode(wList[rowIndex])
                }
            }
            3 -> {
                val wList = studentClassBean.w3List()
                setBg(wList)
                holder.clickItem {
                    if (isSeeClass || wList[rowIndex].isEmpty()) {
                        return@clickItem
                    }
                    toCode(wList[rowIndex])
                }
            }
            4 -> {
                val wList = studentClassBean.w4List()
                setBg(wList)
                holder.clickItem {
                    if (isSeeClass || wList[rowIndex].isEmpty()) {
                        return@clickItem
                    }
                    toCode(wList[rowIndex])
                }
            }
            5 -> {
                val wList = studentClassBean.w5List()
                setBg(wList)
                holder.clickItem {
                    if (isSeeClass || wList[rowIndex].isEmpty()) {
                        return@clickItem
                    }
                    toCode(wList[rowIndex])
                }
            }
        }
    }

    override fun onShowContentData() {
        if (UserControl.isStudent()) {
            selectorClassName = UserControl.loginUserBean!!.className
        }

        super.onShowContentData()
        uiTitleBarContainer.hideRightItem(0)
    }
}
