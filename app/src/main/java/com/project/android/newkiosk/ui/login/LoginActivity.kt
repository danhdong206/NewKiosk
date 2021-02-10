package com.project.android.newkiosk.ui.login

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.project.android.newkiosk.R
import com.project.android.newkiosk.ui.login.view.LoginFragment
import com.project.android.newkiosk.ui.selectapp.SelectAppActivity
import com.project.android.newkiosk.utils.AppUtils

class LoginActivity : AppCompatActivity(), LoginFragment.OnFragmentInteractionListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val loginFragment = LoginFragment()
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.login_screen_fragment, loginFragment)
        fragmentTransaction.commit()

    }

    override fun onFragmentInteraction(uri: Uri?) {

    }
}