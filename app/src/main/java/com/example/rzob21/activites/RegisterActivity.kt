package com.example.rzob21.activites

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.rzob21.R
import com.example.rzob21.UI.fragments.EnterPhoneNumberFragment
import com.example.rzob21.UI.fragments.GoogleLoginFragment
import com.example.rzob21.databinding.ActivityRegisterBinding
import com.example.rzob21.utilits.initFirebase
import com.example.rzob21.utilits.replaceFragment


class RegisterActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityRegisterBinding
    private lateinit var mToolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityRegisterBinding.inflate(layoutInflater)
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//            WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(mBinding.root)
        replaceFragment(GoogleLoginFragment(), false)
        initFirebase()
    }

    override fun onStart() {
        super.onStart()
        mToolbar = mBinding.registerToolbar
        setSupportActionBar(mToolbar)
        title = getString(R.string.register_title_your_phone)
    }

    companion object {
        fun getLaunchIntent(from: Context) = Intent(from, RegisterActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }
    }
}