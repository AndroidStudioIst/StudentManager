package com.student.manager.iview

import com.angcyo.uiview.base.Item
import com.angcyo.uiview.base.SingleItem
import com.angcyo.uiview.model.TitleBarPattern
import com.angcyo.uiview.recycler.RBaseViewHolder
import com.angcyo.uiview.utils.RUtils
import com.student.manager.R
import com.student.manager.base.BaseItemUIView

/**
 * 扫码后, 考勤成功界面
 * Created by angcyo on 2018-03-04.
 */
class ScanResultUIView(val codeString: String) : BaseItemUIView() {
    override fun getTitleBar(): TitleBarPattern {
        return super.getTitleBar().setTitleString("考勤完成")
    }

    override fun createItems(items: MutableList<SingleItem>) {
        val split = RUtils.split(codeString, ":")
        val className = split[0].split(",")[1]
        val lessonName = split[0].split(",")[2]
        val classId = split[1].split(",")[1]
        val position = split[2].split(",")[1]

        items.add(object : SingleItem() {
            override fun onBindView(holder: RBaseViewHolder, posInData: Int, dataBean: Item?) {
                holder.imageView(R.id.code_image_view).setImageResource(R.mipmap.success)
                holder.tv(R.id.code_tip_view).text = "班级:${className}\n${CodeUIView.wws(position.toInt())[0]} ${CodeUIView.wws(position.toInt())[1]} $lessonName\n扫码考勤成功"
            }

            override fun getItemLayoutId(): Int {
                return R.layout.item_code_layout
            }

        })
    }

}
