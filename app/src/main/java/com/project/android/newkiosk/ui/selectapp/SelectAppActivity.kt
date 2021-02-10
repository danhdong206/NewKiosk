package com.project.android.newkiosk.ui.selectapp

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.project.android.newkiosk.R
import com.project.android.newkiosk.ui.selectapp.view.SelectAppFragment

class SelectAppActivity : AppCompatActivity(), SelectAppFragment.OnFragmentInteractionListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selection_apps)

        val selectionAppsFragment = SelectAppFragment()
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.selection_apps_fragment, selectionAppsFragment)
        fragmentTransaction.commit()

    }

    override fun onFragmentInteraction(uri: Uri?) {

    }


}