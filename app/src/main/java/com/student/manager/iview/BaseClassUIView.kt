package com.student.manager.iview

import android.content.pm.ActivityInfo
import android.graphics.Color
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import com.angcyo.uiview.container.ContentLayout
import com.angcyo.uiview.kotlin.onSizeChanged
import com.angcyo.uiview.recycler.RBaseItemDecoration
import com.angcyo.uiview.recycler.RBaseViewHolder
import com.angcyo.uiview.recycler.RRecyclerView
import com.angcyo.uiview.recycler.adapter.RExBaseAdapter
import com.angcyo.uiview.rsen.RefreshLayout
import com.angcyo.uiview.utils.UI
import com.student.manager.R
import com.student.manager.base.BaseSingleRecyclerUIView
import com.student.manager.control.UserControl

/**
 * Created by angcyo on 2018-02-25.
 */
abstract class BaseClassUIView<T> : BaseSingleRecyclerUIView<T>() {

    override fun initRefreshLayout(refreshLayout: RefreshLayout?, baseContentLayout: ContentLayout?) {
        super.initRefreshLayout(refreshLayout, baseContentLayout)
        refreshLayout?.isEnabled = false
    }

    override fun initRecyclerView(recyclerView: RRecyclerView?, baseContentLayout: ContentLayout?) {
        super.initRecyclerView(recyclerView, baseContentLayout)
        recyclerView?.let {
            it.overScrollMode = RecyclerView.OVER_SCROLL_NEVER
            it.layoutManager = GridLayoutManager(mActivity, 6)
            it.onSizeChanged { w, h, oldw, oldh ->
                if (w > 0 && h > 0) {
                    it.adapter?.notifyDataSetChanged()
                }
            }
            it.addItemDecoration(RBaseItemDecoration(getDimensionPixelOffset(R.dimen.base_line), getColor(R.color.base_chat_bg_color)))
        }
    }

    override fun getDefaultRequestedOrientation(): Int {
        return ActivityInfo.SCREEN_ORIENTATION_USER
    }

    override fun getDefaultLayoutState(): LayoutState {
        return LayoutState.LOAD
    }

    override fun showContentLayout() {
        super.showContentLayout()
        resetUI()
    }

    override fun createAdapter(): RExBaseAdapter<String, T, String> = object : RExBaseAdapter<String, T, String>(mActivity) {
        override fun getItemCount(): Int {
            return 66
        }

        override fun getItemLayoutId(viewType: Int): Int {
            return R.layout.item_add_teacher
        }

        override fun onBindCommonView(holder: RBaseViewHolder, position: Int, bean: T?) {
            super.onBindCommonView(holder, position, bean)
            this@BaseClassUIView.onBindCommonView(holder, position, bean)
        }
    }

    open fun onBindCommonView(holder: RBaseViewHolder, position: Int, bean: T?) {
        if (mRecyclerView.measuredHeight > 0) {
            UI.setViewHeight(holder.itemView, (mRecyclerView.measuredHeight - getDimensionPixelOffset(R.dimen.base_line) * 10) / 11)
        }

        holder.tv(R.id.text_view).setTextColor(getColor(R.color.base_text_color))
        holder.itemView.setBackgroundColor(Color.TRANSPARENT)

        val rowIndex = position / 6
        when (position) {
            0 -> {
                holder.itemView.setBackgroundColor(getColor(R.color.base_chat_bg_color))
                holder.tv(R.id.text_view).text = UserControl.loginUserBean!!.name

                onBindFirstView(holder, position, bean)
            }
            1 -> {
                holder.tv(R.id.text_view).text = "一"
            }
            2 -> {
                holder.tv(R.id.text_view).text = "二"
            }
            3 -> {
                holder.tv(R.id.text_view).text = "三"
            }
            4 -> {
                holder.tv(R.id.text_view).text = "四"
            }
            5 -> {
                holder.tv(R.id.text_view).text = "五"
            }
            1 * 6 -> {
                holder.tv(R.id.text_view).text = "$rowIndex"
            }
            2 * 6 -> {
                holder.tv(R.id.text_view).text = "$rowIndex"
            }
            3 * 6 -> {
                holder.tv(R.id.text_view).text = "$rowIndex"
            }
            4 * 6 -> {
                holder.tv(R.id.text_view).text = "$rowIndex"
            }
            5 * 6 -> {
                holder.tv(R.id.text_view).text = "$rowIndex"
            }
            6 * 6 -> {
                holder.tv(R.id.text_view).text = "$rowIndex"
            }
            7 * 6 -> {
                holder.tv(R.id.text_view).text = "$rowIndex"
            }
            8 * 6 -> {
                holder.tv(R.id.text_view).text = "$rowIndex"
            }
            9 * 6 -> {
                holder.tv(R.id.text_view).text = "$rowIndex"
            }
            10 * 6 -> {
                holder.tv(R.id.text_view).text = "$rowIndex"
            }
            else -> {
                //表格内容
                onBindClassView(holder, position, bean)
            }
        }
    }

    open fun onBindFirstView(holder: RBaseViewHolder, position: Int, bean: T?) {

    }

    open fun onBindClassView(holder: RBaseViewHolder, position: Int, bean: T?) {

    }
}
