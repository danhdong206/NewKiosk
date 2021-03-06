package com.project.android.newkiosk.ui.selectapp.view

import android.annotation.SuppressLint
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.PorterDuff
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.project.android.newkiosk.R
import com.project.android.newkiosk.data.model.App
import com.project.android.newkiosk.ui.main.MainActivity
import com.project.android.newkiosk.utils.AppManagerHelper
import com.project.android.newkiosk.utils.Constants
import kotlinx.android.synthetic.main.fragment_selection_apps.*


class SelectAppFragment : Fragment() {

    private var mListener: OnFragmentInteractionListener? = null

    private var mRecyclerView: RecyclerView? = null
    private var mAdapter: RecyclerView.Adapter<*>? = null
    private var mInstalledApps: ArrayList<App> = ArrayList()
    private var mAppManager: AppManagerHelper? = null

    private var mSelectAppAdapter: SelectAppAdapter? = null

    companion object {
        fun newInstance(someInt: Int, someTitle: String?): SelectAppFragment? {
            val selectionAppsFragment = SelectAppFragment()
            val args = Bundle()
            args.putInt("someInt", someInt)
            args.putString("someTitle", someTitle)
            selectionAppsFragment.arguments = args
            return selectionAppsFragment
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
        return inflater.inflate(R.layout.fragment_selection_apps, container, false)
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


    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //Action Bar
        val activity: AppCompatActivity = activity as AppCompatActivity
        activity.supportActionBar?.title = "Select app to use after locked"
        activity.supportActionBar?.setDisplayHomeAsUpEnabled(true)

        //Change back arrow
        val backArrow = resources.getDrawable(R.drawable.chevron_left)
        backArrow.setColorFilter(
            resources.getColor(R.color.white),
            PorterDuff.Mode.SRC_ATOP
        )
        activity.supportActionBar?.setHomeAsUpIndicator(backArrow)

        displayAppsInstalled()

        checkApps()
    }

    private fun checkApps() {
        mSelectAppAdapter = SelectAppAdapter(context!!, mInstalledApps)

        btn_lock.setOnClickListener {
            val stringBuilder: StringBuilder = StringBuilder()

            for(appInfo in mInstalledApps) {
                if (appInfo.isSelected()) {
                    stringBuilder.append(appInfo.getAppPackageName(), ",")
                }
            }

            stringBuilder.deleteCharAt(stringBuilder.length - 1)

            if (mInstalledApps.size > 0) {
                storeApp(stringBuilder.toString())
                val intent = Intent(context, MainActivity::class.java)
                startActivity(intent)
                activity?.finish()
            }
        }
    }

    private fun displayAppsInstalled() {
        mRecyclerView = activity?.findViewById(R.id.recycler_view) as RecyclerView
        val layoutManager = LinearLayoutManager(context)
        mRecyclerView?.layoutManager = layoutManager
        mAppManager = AppManagerHelper(context!!)
        mInstalledApps = mAppManager!!.getApps()!!

        mAdapter = SelectAppAdapter(
            context!!,
            mInstalledApps
        )

        mRecyclerView!!.adapter = mAdapter
    }

    private fun storeApp(string: String) {
        val sharedPreferences: SharedPreferences =
            context!!.getSharedPreferences(Constants.PREFERENCES, MODE_PRIVATE)

        val editor = sharedPreferences.edit()
        editor.putString(Constants.APP_CHECKED, string)
        editor.apply()
    }


}