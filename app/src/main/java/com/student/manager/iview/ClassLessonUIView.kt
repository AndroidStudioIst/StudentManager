package com.student.manager.iview

import com.angcyo.uiview.recycler.RBaseViewHolder
import com.student.manager.R
import com.student.manager.bean.TeacherBean
import com.student.manager.control.UserControl

/**
 * 班级课程管理
 * Created by angcyo on 2018-03-03.
 */
class ClassLessonUIView : AddTeacherUIView(false) {

    init {
        isSeeClass = UserControl.loginUserBean!!.type == 1
        isAddClass = UserControl.loginUserBean!!.type != 1
    }

    override fun onUILoadData(page: Int) {
        super.onUILoadData(page)
    }

    override fun checkEmptyClass(): Boolean {
        if (UserControl.isStudent()) {
            return false
        }
        return super.checkEmptyClass()
    }

    override fun onBindFirstView(holder: RBaseViewHolder, position: Int, bean: TeacherBean?) {
        super.onBindFirstView(holder, position, bean)
        if (UserControl.isStudent()) {
            holder.tv(R.id.text_view).text = selectorClassName
        }
    }

    override fun onBindStudentClassView(holder: RBaseViewHolder, position: Int) {
        super.onBindStudentClassView(holder, position)
        initStudent(holder, position)
    }

    override fun onShowContentData() {
        if (UserControl.isStudent()) {
            selectorClassName = UserControl.loginUserBean!!.className
        }

        super.onShowContentData()
    }
}
