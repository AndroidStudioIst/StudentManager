package com.student.manager.iview

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Rect
import android.text.TextPaint
import android.view.View
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.FindListener
import com.angcyo.bmob.RBmob
import com.angcyo.uiview.container.ContentLayout
import com.angcyo.uiview.dialog.UIInputExDialog
import com.angcyo.uiview.model.TitleBarPattern
import com.angcyo.uiview.recycler.RBaseViewHolder
import com.angcyo.uiview.recycler.RExItemDecoration
import com.angcyo.uiview.recycler.RRecyclerView
import com.angcyo.uiview.recycler.adapter.RBaseSwipeAdapter
import com.angcyo.uiview.recycler.adapter.RExBaseAdapter
import com.angcyo.uiview.recycler.widget.MenuBuilder
import com.angcyo.uiview.utils.T_
import com.student.manager.R
import com.student.manager.base.BaseSingleRecyclerUIView
import com.student.manager.bean.LessonBean

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
class AddLessonUIView(val isSee: Boolean = false) : BaseSingleRecyclerUIView<LessonBean>() {

    override fun getTitleBar(): TitleBarPattern {
        return super.getTitleBar().setTitleString(if (isSee) "课程查看" else "添加课程")
    }

    override fun createAdapter(): RExBaseAdapter<String, LessonBean, String> = object : RBaseSwipeAdapter<String, LessonBean, String>(mActivity) {
        override fun getItemLayoutId(viewType: Int): Int {
            if (isFooterItemType(viewType)) {
                return R.layout.item_button_layout
            } else {
                return R.layout.item_text_layout
            }
        }

        override fun onBindDataView(holder: RBaseViewHolder, posInData: Int, dataBean: LessonBean?) {
            super.onBindDataView(holder, posInData, dataBean)
            holder.tv(R.id.base_text_view).text = dataBean!!.name
        }

        override fun onBindMenuView(menuBuilder: MenuBuilder, viewType: Int, position: Int) {
            super.onBindMenuView(menuBuilder, viewType, position)
            if (!isLast(position) && !isSee) {
                menuBuilder.addMenu("删除", Color.RED) {
                    //                    RBmob.delete<LessonBean>("name:${allDatas[position].name}") {
//                        T_.info("删除完成")
//                    }

                    RBmob.delete<LessonBean>(LessonBean::class.java, "name:${allDatas[position].name}") {
                        T_.info("删除完成")
                    }

                    deleteItem(position)

//                    RBmob.query<LessonBean>().apply {
//                        addWhereEqualTo("name1", allDatas[position].name)
//                        findObjects(object : FindListener<LessonBean>() {
//                            override fun done(resultList: MutableList<LessonBean>?, exception: BmobException?) {
//                                if (RUtils.isListEmpty(resultList)) {
//                                } else {
//
//                                }
//                            }
//                        })
//                    }
                }
            }
        }

        override fun onBindFooterView(holder: RBaseViewHolder, posInFooter: Int, footerBean: String?) {
            super.onBindFooterView(holder, posInFooter, footerBean)

            holder.tv(R.id.button).text = "添加新的课程"

            holder.click(R.id.button) {
                startIView(UIInputExDialog().apply {
                    inputDefaultString = ""
                    inputHintString = "添加新的课程"
                    maxInputLength = 40
                    onInputTextResult = {
                        if (it.isEmpty()) {
                        } else {
                            for (b in mAllDatas) {
                                if (b.name.equals(it, true)) {
                                    T_.error("重复的课程")
                                    break
                                }
                            }
                            addLastItem(LessonBean().apply {
                                name = it
                                save()
                                if (dataCount < 2) {
                                    mRecyclerView.scrollTo(0, true)
                                }
                            })
                        }
                    }
                })
            }
        }
    }

    override fun getRecyclerRootLayoutId(): Int {
        return R.layout.view_lesson_layout
    }

    override fun initRecyclerView(recyclerView: RRecyclerView?, baseContentLayout: ContentLayout?) {
        super.initRecyclerView(recyclerView, baseContentLayout)
        recyclerView?.let {
            it.addItemDecoration(RExItemDecoration(object : RExItemDecoration.SingleItemCallback() {
                override fun getItemOffsets2(outRect: Rect, position: Int, edge: Int) {
                    super.getItemOffsets2(outRect, position, edge)
                    if (isSee) {
                        outRect.bottom = getDimensionPixelOffset(R.dimen.base_line)
                    } else if (position < mExBaseAdapter.itemCount - 2) {
                        outRect.bottom = getDimensionPixelOffset(R.dimen.base_line)
                    }
                }

                override fun draw(canvas: Canvas, paint: TextPaint, itemView: View, offsetRect: Rect, itemCount: Int, position: Int) {
                    drawBottomLine(canvas, paint, itemView, offsetRect, itemCount, position)
                }
            }))
        }
    }

    override fun isUIHaveLoadMore(datas: MutableList<LessonBean>?): Boolean {
        return false
    }

    override fun isUIDataEmpty(page: Int, datas: MutableList<LessonBean>?): Boolean {
        return false
    }

    override fun onUILoadData(page: Int) {
        super.onUILoadData(page)
        RBmob.query<LessonBean>().apply {
            add(findObjects(object : FindListener<LessonBean>() {
                override fun done(p0: MutableList<LessonBean>?, p1: BmobException?) {
                    onUILoadFinish(p0)
                    if (page == 1 && !isSee) {
                        mExBaseAdapter.resetFooterData("")
                    }
                }
            }))
        }
    }

}
