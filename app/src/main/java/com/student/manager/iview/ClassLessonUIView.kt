package com.student.manager.iview

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
}
