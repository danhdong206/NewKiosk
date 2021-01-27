package com.project.android.newkiosk.ui.allowpermisson

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.project.android.newkiosk.R
import com.project.android.newkiosk.ui.allowpermission.view.AllowPermissionFragment
import com.project.android.newkiosk.ui.login.LoginActivity
import com.project.android.newkiosk.utils.AppUtils


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

        if (AppUtils.isMyLauncherDefault(applicationContext)) {
            intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}