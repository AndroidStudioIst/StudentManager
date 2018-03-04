package com.student.manager.iview

import android.graphics.Bitmap
import com.angcyo.rcode.RCode
import com.angcyo.uiview.base.Item
import com.angcyo.uiview.base.SingleItem
import com.angcyo.uiview.model.TitleBarPattern
import com.angcyo.uiview.net.RFunc
import com.angcyo.uiview.net.RSubscriber
import com.angcyo.uiview.net.Rx
import com.angcyo.uiview.recycler.RBaseViewHolder
import com.angcyo.uiview.utils.RUtils
import com.student.manager.R
import com.student.manager.base.BaseItemUIView
import com.student.manager.bean.StudentClassBean

/**
 * 考勤二维码显示界面
 * Created by angcyo on 2018-03-04.
 */
class CodeUIView(val studentClassBean: StudentClassBean, val lessonName: String, val position: String) : BaseItemUIView() {

    companion object {
        fun ww(position: Int): Array<Int> {
            //都是从0开始的索引
            val rowIndex = position / 6//横向第几行
            val rem = position.rem(6)
            return arrayOf(rem, rowIndex)
        }

        fun wws(position: Int): Array<String> {
            val ww = ww(position)
            val ws = when (ww[0]) {
                1 -> "周一"
                2 -> "周二"
                3 -> "周三"
                4 -> "周四"
                5 -> "周五"
                else -> ""
            }

            val wws = when (ww[1]) {
                1 -> "第1节"
                2 -> "第2节"
                3 -> "第3节"
                4 -> "第4节"
                5 -> "第5节"
                6 -> "第6节"
                7 -> "第7节"
                8 -> "第8节"
                9 -> "第9节"
                10 -> "第10节"
                else -> ""
            }

            return arrayOf(ws, wws)
        }

    }

    override fun getTitleBar(): TitleBarPattern {
        return super.getTitleBar().setTitleString("扫码考勤")
    }

    override fun createItems(items: MutableList<SingleItem>) {
        items.add(object : SingleItem() {
            override fun onBindView(holder: RBaseViewHolder, posInData: Int, dataBean: Item?) {
                val decoder: String = RUtils.encode("扫码考勤8888,${studentClassBean.name},${lessonName}:class_id,${studentClassBean.objectId}:pos,${position}")

                Rx.base(object : RFunc<Bitmap>() {
                    override fun onFuncCall(): Bitmap {
                        return RCode.syncEncodeQRCode(decoder, (300 * density()).toInt())
                    }
                }, object : RSubscriber<Bitmap>() {
                    override fun onSucceed(bean: Bitmap?) {
                        super.onSucceed(bean)
                        holder.imageView(R.id.code_image_view).setImageBitmap(bean)
                    }
                })

                holder.tv(R.id.code_tip_view).text = "班级:${studentClassBean.name}\n${wws(position.toInt())[0]} ${wws(position.toInt())[1]} $lessonName"
            }

            override fun getItemLayoutId(): Int {
                return R.layout.item_code_layout
            }

        })
    }

}
