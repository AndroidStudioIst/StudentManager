package com.student.manager.iview

import android.text.TextUtils
import com.angcyo.uiview.RCrashHandler
import com.angcyo.uiview.base.Item
import com.angcyo.uiview.base.SingleItem
import com.angcyo.uiview.kotlin.clickIt
import com.angcyo.uiview.model.TitleBarPattern
import com.angcyo.uiview.recycler.RBaseViewHolder
import com.angcyo.uiview.utils.RUtils
import com.angcyo.uiview.utils.Tip
import com.orhanobut.hawk.Hawk
import com.student.manager.BuildConfig
import com.student.manager.R
import com.student.manager.base.BaseItemUIView
import com.student.manager.control.UserControl

/**
 * 管理员界面
 * Created by angcyo on 2018-03-03.
 */
class AdminUIView : BaseItemUIView() {

    override fun getTitleBar(): TitleBarPattern? {
        return null
    }

    override fun initOnShowContentLayout() {
        super.initOnShowContentLayout()
        if (BuildConfig.SHOW_DEBUG) {
            RCrashHandler.checkCrash(mParentILayout)
        }
    }

    override fun createItems(items: MutableList<SingleItem>) {
        items.add(object : SingleItem() {
            override fun onBindView(holder: RBaseViewHolder, posInData: Int, dataBean: Item?) {
                when (UserControl.loginUserBean!!.type) {
                    3, 4 -> loadAdminLayout(holder)
                    1 -> loadStudentLayout(holder)
                    2 -> loadTeacherLayout(holder)
                }

                holder.tv(R.id.button_32).apply {
                    text = "退出登录"
                    clickIt {
                        UserControl.loginUserBean = null
                        Hawk.put("AUTO_LOGIN", false)
                        replaceIView(LoginUIView())
                    }
                }
            }

            override fun getItemLayoutId(): Int {
                return R.layout.view_admin_layout
            }

        })
    }

    private fun loadStudentLayout(holder: RBaseViewHolder) {
        holder.tv(R.id.button_11).apply {
            text = "${UserControl.loginUserBean!!.name}\n${UserControl.loginUserBean!!.className}\n\n扫码考勤"
            clickIt {
                startIView(ScanUIView().apply {
                    onScanResult = {
                        val code = RUtils.decode(it)

                        if (code.contains("扫码考勤8888")) {
                            val split = RUtils.split(code, ":")
                            val className = split[0].split(",")[1]
                            if (TextUtils.equals(className, UserControl.loginUserBean!!.className)) {
                                startIView(ScanResultUIView(code))
                            } else {
                                Tip.tip("与考勤班级不匹配")
                            }
                        } else {
                            Tip.tip("无效的考勤码")
                        }
                    }
                })
            }
        }

        holder.tv(R.id.button_12).apply {
            text = "课程查看"
            clickIt {
                startIView(AddLessonUIView(true))
            }
        }

        holder.tv(R.id.button_13).apply {
            text = "班级课程查看"
            clickIt {
                startIView(ClassLessonUIView())
            }
        }

        holder.gone(R.id.layout_2)
    }

    private fun loadTeacherLayout(holder: RBaseViewHolder) {
        holder.tv(R.id.button_11).apply {
            text = "${UserControl.loginUserBean!!.name}\n\n开始考勤"
            clickIt {
                startIView(TeacherCheckUIView())
            }
        }
        holder.tv(R.id.button_12).apply {
            text = "学生管理"
            clickIt {
                startIView(AllStudentUIView())
            }
        }
        holder.tv(R.id.button_13).apply {
            text = "班级管理"
            clickIt {
                startIView(AllClassUIView())
            }
        }
        holder.gone(R.id.layout_2)
        holder.visible(R.id.layout__3)

        holder.click(R.id.button__31) {
            startIView(CheckUIView())
        }
    }


    private fun loadAdminLayout(holder: RBaseViewHolder) {
        holder.tv(R.id.button_11).apply {
            text = "管理员\n${UserControl.loginUserBean!!.name}\n\n考勤管理"
            clickIt {
                startIView(CheckUIView())
            }
        }
        holder.tv(R.id.button_12).apply {
            text = "学生管理"
            clickIt {
                startIView(AllStudentUIView())
            }
        }
        holder.tv(R.id.button_13).apply {
            text = "班级管理"
            clickIt {
                startIView(AllClassUIView())
            }
        }
//        holder.tv(R.id.button_31).apply {
//            text = "班级课程管理"
//            clickIt {
//                startIView(ClassLessonUIView())
//            }
//        }

        holder.tv(R.id.button_21).apply {
            text = "课程管理"
            clickIt {
                startIView(AddLessonUIView())
            }
        }

        holder.tv(R.id.button_22).apply {
            text = "班级课程管理"
            clickIt {
                startIView(ClassLessonUIView())
            }
        }
    }

}
