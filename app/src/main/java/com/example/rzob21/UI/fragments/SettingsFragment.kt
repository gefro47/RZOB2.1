package com.example.rzob21.UI.fragments

import android.content.Context
import android.content.Intent
import com.example.rzob21.MainActivity
import com.example.rzob21.R
import com.example.rzob21.activites.RegisterActivity
import com.example.rzob21.utilits.*
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_settings.*

class SettingsFragment : BaseFragment(R.layout.fragment_settings) {

    var googleSingInClient : GoogleSignInClient? = null
    lateinit var mGoogleSignInClient: GoogleSignInClient
    lateinit var mGoogleSignInOptions: GoogleSignInOptions

    override fun onResume() {
        super.onResume()
        setHasOptionsMenu(true)
        initFields()
    }

    private fun initFields() {
        imageViewExit.setOnClickListener {
            startActivity(RegisterActivity.getLaunchIntent(APP_ACTIVITY.applicationContext))
            FirebaseAuth.getInstance().signOut()
            mGoogleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
            mGoogleSignInClient = GoogleSignIn.getClient(APP_ACTIVITY, mGoogleSignInOptions)
            mGoogleSignInClient.signOut()
            MainActivity().finish()
        }
        settings_position.text = USER.position
        settings_btn_change_position.setOnClickListener { replaceFragment(ChangePositionFragment())}
        settings_btn_change_salary.setOnClickListener { replaceFragment(ChangeSalaryFragment()) }
//        settings_btn_change_history.setOnClickListener { replaceFragment(HistoryOfIncomeFragment()) }
    }




}