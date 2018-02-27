package com.student.manager

import android.content.Intent
import com.angcyo.uiview.base.UILayoutActivity
import com.orhanobut.hawk.Hawk
import com.student.manager.control.UserControl
import com.student.manager.iview.LoginUIView
import com.student.manager.iview.MainUIView

class MainActivity : UILayoutActivity() {
    override fun onLoadView(intent: Intent?) {
        //startIView(MainUIView(), false)
        checkPermissions()
        if (Hawk.get("AUTO_LOGIN", false)) {
            UserControl.loginUser(Hawk.get("NAME", ""), Hawk.get("PW", "")) {
                if (it == null) {
                    startIView(LoginUIView(), false)
                } else {
                    startIView(MainUIView(), false)
                }
            }
        } else {
            startIView(LoginUIView(), false)
        }
    }
}
