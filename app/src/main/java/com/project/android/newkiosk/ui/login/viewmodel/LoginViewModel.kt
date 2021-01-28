package com.project.android.newkiosk.ui.login.viewmodel

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.android.newkiosk.data.model.User

class LoginViewModel : ViewModel() {
    var userID = MutableLiveData<String>()
    var password = MutableLiveData<String>()


    private var userMutableLiveData: MutableLiveData<User>? = null

    fun getUser(): MutableLiveData<User>? {
        if (userMutableLiveData == null) {
            userMutableLiveData = MutableLiveData()
        }
        return userMutableLiveData
    }

    fun onClick(view: View?) {
        val user = User(userID.value, password.value)
        userMutableLiveData?.value = user
    }
}