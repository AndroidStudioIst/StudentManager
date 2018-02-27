package com.student.manager.control

import cn.bmob.v3.BmobQuery
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.FindListener
import com.orhanobut.hawk.Hawk
import com.student.manager.bean.UserBean
import rx.Subscription

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2018/02/24 15:47
 * 修改人员：Robi
 * 修改时间：2018/02/24 15:47
 * 修改备注：
 * Version: 1.0.0
 */
object UserControl {

    /**登录成功后, 保存的用户数据Bean*/
    var loginUserBean: UserBean? = null

    fun isStudent() = if (loginUserBean == null) {
        false
    } else {
        loginUserBean!!.type == 1
    }

    fun isTeacher() = if (loginUserBean == null) {
        false
    } else {
        loginUserBean!!.type == 2
    }

    fun isAdmin() = if (loginUserBean == null) {
        false
    } else {
        loginUserBean!!.type == 3
    }

    /**判断用户是否存在*/
    fun isUserExist(name: String, result: (exist: Boolean) -> Unit): Subscription {
        val query = BmobQuery<UserBean>()
        query.addWhereEqualTo("name", name)
        return query.findObjects(object : FindListener<UserBean>() {
            override fun done(p0: MutableList<UserBean>?, p1: BmobException?) {
                result.invoke(p0 != null && p0.isNotEmpty())
            }
        })
    }

    fun loginUser(name: String, pw: String, result: (bean: UserBean?) -> Unit): Subscription {
        val query = BmobQuery<UserBean>()
        query.addWhereEqualTo("name", name)
        query.addWhereEqualTo("pw", pw)
        return query.findObjects(object : FindListener<UserBean>() {
            override fun done(p0: MutableList<UserBean>?, p1: BmobException?) {
                if (p0 != null && p0.isNotEmpty()) {
                    loginUserBean = p0.first()

                    Hawk.put("NAME", name)
                    Hawk.put("PW", pw)

                    result.invoke(loginUserBean)
                } else {
                    result.invoke(null)
                }
            }
        })
    }
}
