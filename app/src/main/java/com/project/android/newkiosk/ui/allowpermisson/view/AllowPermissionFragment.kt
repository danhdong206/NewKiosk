package com.project.android.newkiosk.ui.allowpermission.view

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.project.android.newkiosk.R
import com.project.android.newkiosk.utils.AppUtils.hasPermisson
import com.project.android.newkiosk.utils.AppUtils.setLauncherDefault
import kotlinx.android.synthetic.main.fragment_allow_permission.*


class AllowPermissionFragment : Fragment() {
    private var mListener: OnFragmentInteractionListener? = null


    companion object {
        fun newInstance(someInt: Int, someTitle: String?): AllowPermissionFragment? {
            val loginFragment = AllowPermissionFragment()
            val args = Bundle()
            args.putInt("someInt", someInt)
            args.putString("someTitle", someTitle)
            loginFragment.arguments = args
            return loginFragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_allow_permission, container, false)
    }

    interface OnFragmentInteractionListener {
        fun onFragmentInteraction(uri: Uri?)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mListener = if (context is OnFragmentInteractionListener) {
            context
        } else {
            throw RuntimeException(
                    context.toString()
                            + " must implement OnFragmentInteractionListener"
            )
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        (activity as AppCompatActivity?)?.supportActionBar?.hide()

        btn_got_it.setOnClickListener {
            if (!hasPermisson(context!!)) {
                val intent = Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS)
                startActivity(intent)
            } else {
                setLauncherDefault(context!!)
            }
        }

    }

    @SuppressLint("SetTextI18n")
    override fun onStart() {
        super.onStart()

        if (hasPermisson(context!!)) {
            radio_allow_permission.isChecked = true
            view_line.setBackgroundColor(Color.parseColor("#E24F5A"))
            txt_allow_permission.text = "Set this app as default launcher"
            img_view.setImageResource(R.drawable.default_launcher)
        }
    }


}