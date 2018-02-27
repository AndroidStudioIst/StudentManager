package com.student.manager.base

import android.graphics.Color
import com.angcyo.uiview.base.UIRecyclerUIView

/**
 * Created by angcyo on 2018/02/13 23:10
 */
abstract class BaseRecyclerUIView<H, T, F> : UIRecyclerUIView<H, T, F>() {
    override fun getDefaultBackgroundColor(): Int {
        return Color.WHITE
    }
}
