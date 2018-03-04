package com.student.manager.iview

import android.text.TextUtils
import com.angcyo.bmob.RBmob
import com.angcyo.uiview.base.Item
import com.angcyo.uiview.base.SingleItem
import com.angcyo.uiview.dialog.UILoading
import com.angcyo.uiview.kotlin.stringSize
import com.angcyo.uiview.model.TitleBarPattern
import com.angcyo.uiview.recycler.RBaseViewHolder
import com.angcyo.uiview.utils.RUtils
import com.angcyo.uiview.utils.Tip
import com.student.manager.R
import com.student.manager.base.BaseItemUIView
import com.student.manager.bean.CheckBean
import com.student.manager.control.UserControl

/**
 * 扫码后, 考勤成功界面
 * Created by angcyo on 2018-03-04.
 */
class ScanResultUIView(val codeString: String) : BaseItemUIView() {
    override fun getTitleBar(): TitleBarPattern {
        return super.getTitleBar().setTitleString("考勤完成")
    }

    private var isFirst = true

    private var number = 0

    private val numberString: String
        get() {
            return if (number <= 0) {
                ""
            } else {
                "\n\n打卡人数:${number}"
            }
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
                holder.tv(R.id.code_tip_view).text = "班级:${className}\n${CodeUIView.wws(position.toInt())[0]} ${CodeUIView.wws(position.toInt())[1]} $lessonName\n扫码考勤成功$numberString"
            }

            override fun getItemLayoutId(): Int {
                return R.layout.item_code_layout
            }

        })
        if (isFirst) {
            UILoading.progress(mParentILayout).setLoadingTipText("打卡中...")

            //查询所有班级打卡情况
            RBmob.query(CheckBean::class.java, "") {
                var checkBean: CheckBean = CheckBean().apply {
                    name = UserControl.loginUserBean!!.className
                }
                if (RUtils.isListEmpty(it)) {
                } else {
                    for (bean in it) {
                        if (TextUtils.equals(bean.name, className)) {
                            //找到需要打卡的班级
                            checkBean = bean
                            break
                        }
                    }
                }

                saveCheck(checkBean, position.toInt())
                notifyItemChanged(0)

                RBmob.update(CheckBean::class.java, checkBean, "name:$className") {
                    if (TextUtils.isEmpty(it)) {
                        Tip.tip("打卡失败")
                    } else {
                        Tip.tip("打卡成功")
                        number++
                        notifyItemChanged(0)
                    }
                    UILoading.hide()
                }
            }
        }
        isFirst = false
    }

    private fun saveCheck(checkBean: CheckBean, position: Int) {
        val ww = CodeUIView.ww(position)
        val rowIndex = ww[1] - 1

        when (ww[0]) {
            1 -> {//周一
                val list = checkBean.w1List()
                number = RUtils.split(list[rowIndex], ":").stringSize(true)
                list[rowIndex] = "${list[rowIndex]}:${UserControl.loginUserBean!!.objectId}"
                checkBean.w1 = RUtils.connect(list)
            }
            2 -> {
                val list = checkBean.w2List()
                number = RUtils.split(list[rowIndex], ":").stringSize(true)
                list[rowIndex] = "${list[rowIndex]}:${UserControl.loginUserBean!!.objectId}"
                checkBean.w2 = RUtils.connect(list)
            }
            3 -> {
                val list = checkBean.w3List()
                number = RUtils.split(list[rowIndex], ":").stringSize(true)
                list[rowIndex] = "${list[rowIndex]}:${UserControl.loginUserBean!!.objectId}"
                checkBean.w3 = RUtils.connect(list)
            }
            4 -> {
                val list = checkBean.w4List()
                number = RUtils.split(list[rowIndex], ":").stringSize(true)
                list[rowIndex] = "${list[rowIndex]}:${UserControl.loginUserBean!!.objectId}"
                checkBean.w4 = RUtils.connect(list)
            }
            5 -> {
                val list = checkBean.w5List()
                number = RUtils.split(list[rowIndex], ":").stringSize(true)
                list[rowIndex] = "${list[rowIndex]}:${UserControl.loginUserBean!!.objectId}"
                checkBean.w5 = RUtils.connect(list)
            }
        }
    }
}
