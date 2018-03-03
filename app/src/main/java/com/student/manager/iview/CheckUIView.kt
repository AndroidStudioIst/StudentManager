package com.student.manager.iview

import com.angcyo.uiview.model.TitleBarPattern
import com.angcyo.uiview.recycler.RBaseViewHolder
import com.student.manager.bean.CheckBean

/**
 * 考勤管理
 * Created by angcyo on 2018-03-03.
 */

class CheckUIView : BaseClassUIView<CheckBean>() {

    override fun getTitleBar(): TitleBarPattern {
        return super.getTitleBar().setTitleString("考勤管理")
    }

    override fun onBindFirstView(holder: RBaseViewHolder, position: Int, bean: CheckBean?) {
        super.onBindFirstView(holder, position, bean)
    }

    override fun onBindClassView(holder: RBaseViewHolder, position: Int, bean: CheckBean?) {
        super.onBindClassView(holder, position, bean)
    }

    override fun onUILoadData(page: Int, extend: String?) {
        super.onUILoadData(page, extend)
        showContentLayout()
    }
}
