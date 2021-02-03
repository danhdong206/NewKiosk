package com.project.android.newkiosk.utils

import android.annotation.SuppressLint
import android.app.AppOpsManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.net.Uri
import android.os.Build
import android.provider.Settings
import com.project.android.newkiosk.utils.Constants.LAUNCHER_CLASS
import com.project.android.newkiosk.utils.Constants.LAUNCHER_PACKAGE


object AppUtils {

    internal enum class HomeState {
        GEL_IS_DEFAULT, OTHER_LAUNCHER_IS_DEFAULT, NO_DEFAULT
    }

    fun hasPermisson(context: Context): Boolean {
        return try {
            val packageManager = context.packageManager
            val applicationInfo = packageManager.getApplicationInfo(context.packageName, 0)
            val appOpsManager = context.getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager
            val mode = appOpsManager.checkOpNoThrow(
                AppOpsManager.OPSTR_GET_USAGE_STATS,
                applicationInfo.uid,
                applicationInfo.packageName
            )
            mode == AppOpsManager.MODE_ALLOWED
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
    }

    fun setLauncherDefault(context: Context): Boolean {
        var intent = Intent(Intent.ACTION_MAIN)
        intent.addCategory(Intent.CATEGORY_HOME)
        val resolveActivity = context.packageManager.resolveActivity(intent, 0)
        val homeState =
            if (LAUNCHER_PACKAGE == resolveActivity!!.activityInfo.applicationInfo.packageName && LAUNCHER_CLASS == resolveActivity.activityInfo.name) HomeState.GEL_IS_DEFAULT else if (resolveActivity == null || resolveActivity.activityInfo == null || !inResolveInfoList(
                    resolveActivity,
                    context.packageManager.queryIntentActivities(intent, 0)
                )
            ) HomeState.NO_DEFAULT else HomeState.OTHER_LAUNCHER_IS_DEFAULT
        return when (homeState) {
            HomeState.GEL_IS_DEFAULT, HomeState.NO_DEFAULT -> {
                intent = Intent(Intent.ACTION_MAIN)
                intent.addCategory(Intent.CATEGORY_HOME)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                context.startActivity(intent)
                true
            }
            else -> {
                showClearDefaultsDialog(resolveActivity, context)
                false
            }
        }
    }

    private fun showClearDefaultsDialog(resolveInfo: ResolveInfo, context: Context) {
        val loadLabel = resolveInfo.loadLabel(context.packageManager)
        val intent: Intent = if (Build.VERSION.SDK_INT < 21
            || context.packageManager
                .resolveActivity(Intent(Settings.ACTION_HOME_SETTINGS), 0) == null
        ) {
            Intent(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                Uri.fromParts("package", resolveInfo.activityInfo.packageName, null)
            )
        } else {
            Intent(Settings.ACTION_HOME_SETTINGS)
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }

    private fun inResolveInfoList(resolveInfo: ResolveInfo, list: List<ResolveInfo>): Boolean {
        for (resolveInfo2 in list) {
            if (resolveInfo2.activityInfo.name == resolveInfo.activityInfo.name && resolveInfo2.activityInfo.packageName ==
                resolveInfo.activityInfo.packageName
            ) {
                return true
            }
        }
        return false
    }

    @SuppressLint("WrongConstant")
    fun isMyLauncherDefault(context: Context): Boolean {
        val localPackageManager = context.packageManager
        val intent = Intent("android.intent.action.MAIN")
        intent.addCategory("android.intent.category.HOME")
        return localPackageManager.resolveActivity(
            intent,
            65536
        ).activityInfo.packageName == context.packageName
    }
}