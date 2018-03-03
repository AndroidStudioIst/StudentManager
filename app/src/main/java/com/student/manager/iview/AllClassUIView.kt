package com.student.manager.iview

import android.graphics.Canvas
import android.graphics.Rect
import android.text.TextPaint
import android.text.TextUtils
import android.view.View
import com.angcyo.bmob.RBmob
import com.angcyo.uiview.container.ContentLayout
import com.angcyo.uiview.model.TitleBarPattern
import com.angcyo.uiview.recycler.RBaseViewHolder
import com.angcyo.uiview.recycler.RExItemDecoration
import com.angcyo.uiview.recycler.RRecyclerView
import com.angcyo.uiview.recycler.adapter.RBaseSwipeAdapter
import com.angcyo.uiview.recycler.adapter.RExBaseAdapter
import com.student.manager.R
import com.student.manager.base.BaseSingleRecyclerUIView
import com.student.manager.bean.UserBean

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2018/02/24 17:43
 * 修改人员：Robi
 * 修改时间：2018/02/24 17:43
 * 修改备注：
 * Version: 1.0.0
 */
class AllClassUIView : BaseSingleRecyclerUIView<String>() {

    override fun getTitleBar(): TitleBarPattern {
        return super.getTitleBar().setTitleString("所有班级")
    }

    override fun createAdapter(): RExBaseAdapter<String, String, String> = object : RBaseSwipeAdapter<String, String, String>(mActivity) {
        override fun getItemLayoutId(viewType: Int): Int {
            if (isFooterItemType(viewType)) {
                return R.layout.item_button_layout
            } else {
                return R.layout.item_user_info_layout
            }
        }

        override fun onBindDataView(holder: RBaseViewHolder, posInData: Int, dataBean: String?) {
            super.onBindDataView(holder, posInData, dataBean)
            holder.tv(R.id.base_text_view).text = dataBean
        }
    }

    override fun initRecyclerView(recyclerView: RRecyclerView?, baseContentLayout: ContentLayout?) {
        super.initRecyclerView(recyclerView, baseContentLayout)
        recyclerView?.let {
            it.addItemDecoration(RExItemDecoration(object : RExItemDecoration.SingleItemCallback() {
                override fun getItemOffsets2(outRect: Rect, position: Int, edge: Int) {
                    super.getItemOffsets2(outRect, position, edge)
                    outRect.bottom = getDimensionPixelOffset(R.dimen.base_line)
                }

                override fun draw(canvas: Canvas, paint: TextPaint, itemView: View, offsetRect: Rect, itemCount: Int, position: Int) {
                    drawBottomLine(canvas, paint, itemView, offsetRect, itemCount, position)
                }
            }))
        }
    }

    override fun onUILoadData(page: Int) {
        super.onUILoadData(page)
        //查询所有学生
        val classList = mutableListOf<String>()
        RBmob.query<UserBean>(UserBean::class.java, "") {
            for (bean in it) {
                if (classList.contains(bean.className) || TextUtils.isEmpty(bean.className)) {

                } else {
                    classList.add(bean.className)
                }
            }
            onUILoadFinish(classList)
        }
    }

}
