package com.project.android.newkiosk.ui

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.project.android.newkiosk.R
import com.project.android.newkiosk.ui.allowpermission.view.AllowPermissionFragment

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
}