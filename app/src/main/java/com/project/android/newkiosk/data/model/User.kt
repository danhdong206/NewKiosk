package com.project.android.newkiosk.data.model

import android.text.TextUtils
import android.util.Patterns

data class User(private var userID: String?, private var password: String?) {

    fun isEmailValid(): Boolean {
        return !TextUtils.isEmpty(userID) && Patterns.EMAIL_ADDRESS.matcher(userID.toString())
            .matches();
    }

    fun isPasswordGreaterThan6(): Boolean {
        return password!!.length > 6
    }

    fun getUserID(): String? {
        return userID
    }

    fun getPassword(): String? {
        return password
    }
}