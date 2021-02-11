package com.project.android.newkiosk.ui.allowpermisson

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.project.android.newkiosk.R
import com.project.android.newkiosk.ui.allowpermission.view.AllowPermissionFragment
import com.project.android.newkiosk.ui.login.LoginActivity
import com.project.android.newkiosk.ui.main.MainActivity
import com.project.android.newkiosk.ui.selectapp.SelectAppActivity
import com.project.android.newkiosk.utils.AppUtils
import com.project.android.newkiosk.utils.Constants


class AllowPermissionActivity : AppCompatActivity(),
    AllowPermissionFragment.OnFragmentInteractionListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_allow_permission)

        val allowPermissionFragment = AllowPermissionFragment()
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.allow_permission_fragment, allowPermissionFragment)
        fragmentTransaction.commit()
    }

    override fun onFragmentInteraction(uri: Uri?) {

    }

    override fun onResume() {
        super.onResume()

        val intent: Intent

        if (!hasLogin()) {
            if (AppUtils.isMyLauncherDefault(applicationContext)) {
                intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        } else if (!hasSelectedApps()) {
            if (AppUtils.isMyLauncherDefault(applicationContext)) {
                intent = Intent(this, SelectAppActivity::class.java)
                startActivity(intent)
                finish()
            }
        } else {
            if (AppUtils.isMyLauncherDefault(applicationContext)) {
                intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    private fun hasLogin(): Boolean {
        val sharedPreferences: SharedPreferences =
            getSharedPreferences(Constants.PREFERENCES, Context.MODE_PRIVATE)
        val login = sharedPreferences.getString(Constants.USERID, null)
        return login != null
    }

    private fun hasSelectedApps(): Boolean {
        val sharedPreferences: SharedPreferences =
            getSharedPreferences(Constants.PREFERENCES, Context.MODE_PRIVATE)
        val selectedApps = sharedPreferences.getString(Constants.APP_CHECKED, null)
        return selectedApps != null
    }
}