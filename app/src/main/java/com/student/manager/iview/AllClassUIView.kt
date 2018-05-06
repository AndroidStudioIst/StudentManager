package com.student.manager.iview

import android.graphics.Canvas
import android.graphics.Rect
import android.text.TextPaint
import android.text.TextUtils
import android.view.View
import com.angcyo.bmob.RBmob
import com.angcyo.uiview.container.ContentLayout
import com.angcyo.uiview.dialog.UIInputExDialog
import com.angcyo.uiview.model.TitleBarPattern
import com.angcyo.uiview.recycler.RBaseViewHolder
import com.angcyo.uiview.recycler.RExItemDecoration
import com.angcyo.uiview.recycler.RRecyclerView
import com.angcyo.uiview.recycler.adapter.RBaseSwipeAdapter
import com.angcyo.uiview.recycler.adapter.RExBaseAdapter
import com.angcyo.uiview.utils.T_
import com.student.manager.R
import com.student.manager.base.BaseSingleRecyclerUIView
import com.student.manager.bean.ClassBean
import com.student.manager.bean.UserBean
import com.student.manager.control.UserControl

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
class AllClassUIView : BaseSingleRecyclerUIView<ClassBean>() {

    val classList = mutableListOf<ClassBean>()

    override fun getTitleBar(): TitleBarPattern {
        return super.getTitleBar().setTitleString("所有班级")
    }

    override fun createAdapter(): RExBaseAdapter<String, ClassBean, String> = object : RBaseSwipeAdapter<String, ClassBean, String>(mActivity) {
        override fun getItemLayoutId(viewType: Int): Int {
            if (isFooterItemType(viewType)) {
                return R.layout.item_button_layout
            } else {
                return R.layout.item_user_info_layout
            }
        }

        override fun onBindDataView(holder: RBaseViewHolder, posInData: Int, dataBean: ClassBean?) {
            super.onBindDataView(holder, posInData, dataBean)

            dataBean?.let {
                holder.tv(R.id.base_text_view).text = "${it.name} (${it.userBeanList.size}人)"
            }
        }

        override fun onBindFooterView(holder: RBaseViewHolder, posInFooter: Int, footerBean: String?) {
            super.onBindFooterView(holder, posInFooter, footerBean)

            holder.tv(R.id.button).text = "添加班级"

            holder.click(R.id.button) {
                startIView(UIInputExDialog().apply {
                    inputDefaultString = ""
                    inputHintString = "添加新班级"
                    maxInputLength = 40
                    onInputTextResult = {
                        if (it.isEmpty()) {
                        } else {
                            for (b in mAllDatas) {
                                if (b.name.equals(it, true)) {
                                    T_.error("重复的班级")
                                    break
                                }
                            }
                            addLastItem(ClassBean().apply {
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

    override fun isUIDataEmpty(page: Int, datas: MutableList<ClassBean>?): Boolean {
        return false
    }


    companion object {
        fun getAllClass(onResult: (List<ClassBean>) -> Unit) {
            val classList = mutableListOf<ClassBean>()
            RBmob.query<ClassBean>(ClassBean::class.java, "") {

                if (it != null) {
                    classList.addAll(it)
                }

                //查询所有学生
                RBmob.query<UserBean>(UserBean::class.java, "") {
                    for (bean in it) {
                        if (TextUtils.isEmpty(bean.className)) {

                        } else {

                            var haveClass = false //手动填写班级, 是否在班级表中
                            for (classBean in classList) {
                                if (classBean.name == bean.className) {
                                    haveClass = true
                                    classBean.userBeanList.add(bean)
                                }
                            }

                            if (haveClass) {
                            } else {
                                classList.add(ClassBean().apply {
                                    name = bean.className
                                    userBeanList.add(bean)
                                })
                            }
                        }
                    }

                    onResult.invoke(classList)
                }
            }
        }
    }

    override fun onUILoadData(page: Int, extend: String?) {
        super.onUILoadData(page, extend)

        getAllClass {
            classList.addAll(it)

            onUILoadFinish(classList)

            if (page == 1 && UserControl.isAdmin()) {
                mExBaseAdapter.resetFooterData("")
            }
        }
    }

}
