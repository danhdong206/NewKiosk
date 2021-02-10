package com.project.android.newkiosk.ui.main.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.project.android.newkiosk.R
import com.project.android.newkiosk.data.model.App
import com.project.android.newkiosk.utils.AppManagerHelper
import com.project.android.newkiosk.utils.Constants
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment(), MainAdapter.OnItemClickListener {

    private var mListener: OnFragmentInteractionListener? = null

    private var mAdapter: RecyclerView.Adapter<*>? = null
    private var mInstalledApps: ArrayList<App> = ArrayList()
    private var mAppManager: AppManagerHelper? = null

    private var mMainAdapter: MainAdapter? = null

    companion object {
        fun newInstance(someInt: Int, someTitle: String?): MainFragment? {
            val mainFragment = MainFragment()
            val args = Bundle()
            args.putInt("someInt", someInt)
            args.putString("someTitle", someTitle)
            mainFragment.arguments = args
            return mainFragment
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
        return inflater.inflate(R.layout.fragment_main, container, false)
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

        requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
    }

    private fun callParentMethod() {
        activity!!.onBackPressed()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // Disable back button
        callParentMethod()

        (activity as AppCompatActivity?)?.supportActionBar?.hide()

        displayApps()

    }

    private fun displayApps() {

        val gridLayoutManager = GridLayoutManager(context, 4)

        recycler_view_main.setHasFixedSize(true)
        recycler_view_main.layoutManager = gridLayoutManager
        mAppManager = AppManagerHelper(context!!)
        mInstalledApps = getSelectedApp(mAppManager!!.getApps()!!, retrieveSelectedAppPackage())
        mAdapter = MainAdapter(
            context!!,
            mInstalledApps,
            this
        )

        recycler_view_main.adapter = mAdapter
    }

    private fun getSelectedApp(listApp: ArrayList<App>, listString: List<String>): ArrayList<App> {
        val iterator = listApp.iterator()
        while (iterator.hasNext()) {
            val app = iterator.next()
            var isSelectedApp = false
            for (packageName in listString) {
                if (app.getAppPackageName().equals(packageName)) {
                    isSelectedApp = true
                    break
                }
            }
            if (!isSelectedApp) {
                iterator.remove()
            }
        }
        return listApp
    }

    private fun retrieveSelectedAppPackage(): List<String> {
        val sharedPreferences: SharedPreferences =
            context!!.getSharedPreferences(Constants.PREFERENCES, Context.MODE_PRIVATE)
        val appPackageName = sharedPreferences.getString(Constants.APP_CHECKED, null)

        val listAppPackageName: List<String>? = appPackageName?.split(",")?.map { it.trim() }
        listAppPackageName?.forEach {
            Log.d("RetrieveApp", it)
        }
        return listAppPackageName!!
    }

    override fun onItemClick(position: Int) {
        val intent: Intent? =
            context!!.packageManager.getLaunchIntentForPackage(mInstalledApps[position].getAppPackageName()!!)
        if (intent != null) {
            startActivity(intent)
        } else {
            Toast.makeText(context, "Error, Please Try Again...", Toast.LENGTH_SHORT).show()
        }

        mMainAdapter?.notifyItemChanged(position)
    }

}