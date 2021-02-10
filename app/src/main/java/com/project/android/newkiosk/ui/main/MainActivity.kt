package com.project.android.newkiosk.ui.main

import android.app.Dialog
import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.project.android.newkiosk.R
import com.project.android.newkiosk.ui.main.view.MainFragment
import com.project.android.newkiosk.utils.AppUtils.resetPreferredLauncherAndOpenChooser
import com.project.android.newkiosk.utils.Constants

class MainActivity : AppCompatActivity(), MainFragment.OnFragmentInteractionListener {

    private var count = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mainFragment = MainFragment()
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.main_fragment, mainFragment)
        fragmentTransaction.commit()
    }

    override fun onFragmentInteraction(uri: Uri?) {

    }

    override fun onBackPressed() {

    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event?.action == MotionEvent.ACTION_UP) {
            count++
            if (count == 7) {
                count = 0
                openDialog()
            }
            return true
        }
        return false
    }

    private fun openDialog() {
        val dialog = Dialog(this)

        val layoutInflater: LayoutInflater = this.layoutInflater
        val view: View = layoutInflater.inflate(R.layout.layout_dialog, null)

        val mEditTextPassword: EditText =
            view.findViewById<View>(R.id.edit_text_unlock_password) as EditText
        val mBtnOk: Button = view.findViewById<View>(R.id.btn_ok) as Button
        val mBtnCancel: Button = view.findViewById<View>(R.id.btn_cancel) as Button

        mBtnOk.setOnClickListener {
            when {
                retrievePasswordToUnlock() == mEditTextPassword.text.toString() -> {
                    resetPreferredLauncherAndOpenChooser(this)
                    resetData()
                }
                mEditTextPassword.text.toString() == "" -> {
                    Toast.makeText(
                        this,
                        "Please enter your password to unlock.",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
                else -> {
                    Toast.makeText(this, "Wrong password. Please try again.", Toast.LENGTH_SHORT)
                        .show()
                }
            }

            dialog.dismiss()
        }

        mBtnCancel.setOnClickListener {
            dialog.cancel()
        }

        dialog.setContentView(view)
        dialog.show()
    }

    private fun retrievePasswordToUnlock(): String {
        val sharedPreferences: SharedPreferences =
            getSharedPreferences(Constants.PREFERENCES, Context.MODE_PRIVATE)
        val password = sharedPreferences.getString(Constants.PASSWORD, null)

        return password!!
    }

    private fun resetData() {
        val sharedPreferences: SharedPreferences =
            getSharedPreferences(Constants.PREFERENCES, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()

        editor.clear().apply()
    }


}