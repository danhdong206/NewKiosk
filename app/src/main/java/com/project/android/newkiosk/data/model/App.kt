package com.project.android.newkiosk.data.model

import android.graphics.drawable.Drawable


class App {
    private var appName: String? = null
    private var appIcon: Drawable? = null
    private var appPackageName: String? = null
    private var isSelected = false

    fun getAppIcon(): Drawable? {
        return appIcon
    }

    fun setAppIcon(appIcon: Drawable?) {
        this.appIcon = appIcon
    }

    fun isSelected(): Boolean {
        return isSelected
    }

    fun setSelected(selected: Boolean) {
        isSelected = selected
    }

    fun getAppName(): String? {
        return appName
    }

    fun setAppName(appName: String?) {
        this.appName = appName
    }

    fun getAppPackageName(): String? {
        return appPackageName
    }

    fun setAppPackageName(appPackageName: String?) {
        this.appPackageName = appPackageName
    }

}
