package com.student.manager.base

import android.graphics.Color
import com.angcyo.uiview.base.UIContentView

/**
 * Created by angcyo on 2018/02/13 23:09
 */
abstract class BaseContentUIView : UIContentView() {
    override fun getDefaultBackgroundColor(): Int {
        return Color.WHITE
    }
}
