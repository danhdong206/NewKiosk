package com.project.android.newkiosk.utils


import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import com.project.android.newkiosk.R
import com.project.android.newkiosk.data.model.App
import java.util.*
import kotlin.collections.ArrayList


class AppManagerHelper(context: Context) {
    private var mContext: Context? = context
    private val myApps: ArrayList<App>? = ArrayList()

    fun getApps(): ArrayList<App>? {
        loadApps()
        return myApps
    }

    private fun loadApps() {
        val packages = mContext!!.packageManager.getInstalledApplications(0)
        for (packageInfo in packages) {
            if (mContext!!.packageManager.getLaunchIntentForPackage(packageInfo.packageName) != null) {
                val newApp = App()
                newApp.setAppName(getApplicationLabelByPackageName(packageInfo.packageName))
                newApp.setAppIcon(getAppIconByPackageName(packageInfo.packageName))
                newApp.setAppPackageName(packageInfo.packageName)
                myApps?.add(newApp)
            }

        }

        myApps?.sortWith(Comparator { s1, s2 -> s1.getAppName()!!.compareTo(s2.getAppName()!!, ignoreCase = true) })
    }

    private fun getAppIconByPackageName(packageName: String): Drawable? {
        val drawable: Drawable?
        drawable = try {
            mContext!!.packageManager.getApplicationIcon(packageName)
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
            ContextCompat.getDrawable(mContext!!, R.drawable.ic_launcher_background)
        }
        return drawable
    }

    private fun getApplicationLabelByPackageName(packageName: String): String? {
        val packageManager = mContext!!.packageManager
        val applicationInfo: ApplicationInfo
        var label = "Unknown"
        try {
            applicationInfo = packageManager.getApplicationInfo(packageName, 0)
            if (applicationInfo != null) {
                label = packageManager.getApplicationLabel(applicationInfo) as String
            }
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return label
    }



}