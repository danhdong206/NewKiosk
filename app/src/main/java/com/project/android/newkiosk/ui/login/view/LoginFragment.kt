package com.project.android.newkiosk.ui.login.view

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.project.android.newkiosk.R
import com.project.android.newkiosk.databinding.FragmentLoginBinding
import com.project.android.newkiosk.ui.login.viewmodel.LoginViewModel
import com.project.android.newkiosk.ui.selectapp.SelectAppActivity
import com.project.android.newkiosk.utils.Constants
import kotlinx.android.synthetic.main.fragment_login.*


class LoginFragment : Fragment() {
    private var mListener: OnFragmentInteractionListener? = null

    private var binding: FragmentLoginBinding? = null


    companion object {
        fun newInstance(someInt: Int, someTitle: String?): LoginFragment? {
            val loginFragment = LoginFragment()
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
        return inflater.inflate(R.layout.fragment_login, container, false)
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

        val mLoginViewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)

        binding = DataBindingUtil.setContentView(activity!!, R.layout.fragment_login)

        binding?.lifecycleOwner = this

        binding?.loginViewModel = mLoginViewModel

        mLoginViewModel.getUser()?.observe(this, Observer {

            if (TextUtils.isEmpty(it.getUserID())) {
                binding?.editTextUserId?.requestFocus()
                binding?.editTextUserId?.error = getString(R.string.user_id_can_not_empty)
            } else if (TextUtils.isEmpty(it.getPassword())) {
                binding?.editTextPassword?.requestFocus()
                binding?.editTextPassword?.error = getString(R.string.password_can_not_empty)
            } else if (!it.isPasswordGreaterThan6()) {
                binding?.editTextPassword?.requestFocus()
                binding?.editTextPassword?.error = getString(R.string.invalid_password)
            } else if (!it.isEmailValid()) {
                binding?.editTextUserId?.requestFocus()
                binding?.editTextUserId?.error = getString(R.string.invalid_user_id)
            } else {
                val progressDialog: ProgressDialog?

                progressDialog = ProgressDialog(activity)
                progressDialog.setMessage("Loading...")
                progressDialog.show()

                storeUserIDAndPassword(
                    binding?.editTextUserId?.text.toString(),
                    binding?.editTextPassword?.text.toString()
                )
                loginSuccessfully()
            }
        })
    }

    //Share References
    private fun storeUserIDAndPassword(userID: String, password: String) {
        val sharedPreferences =
            context!!.getSharedPreferences(Constants.PREFERENCES, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(Constants.USERID, userID)
        editor.putString(Constants.PASSWORD, password)
        editor.apply()
    }

    private fun loginSuccessfully() {
        val intent = Intent(context, SelectAppActivity::class.java)
        startActivity(intent)
        activity?.finish()
    }
}