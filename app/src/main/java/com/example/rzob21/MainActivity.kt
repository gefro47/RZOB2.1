package com.example.rzob21

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.rzob21.UI.fragments.CalendarFragment
import com.example.rzob21.UI.objects.AppDrawer
import com.example.rzob21.activites.RegisterActivity
import com.example.rzob21.databinding.ActivityMainBinding
import com.example.rzob21.models.User
import com.example.rzob21.utilits.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding
    lateinit var mAppDrawer: AppDrawer
    private lateinit var mToolbar: androidx.appcompat.widget.Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        APP_ACTIVITY = this
        initFields()
        initFirebase()
        initUser()
        initFunc()
    }


    private fun initFunc() {
//        if (true){
        if (AUTH.currentUser != null){
            setSupportActionBar(mToolbar)
            mAppDrawer.create()
            replaceFragment(CalendarFragment(),false)
        }else{
            replaceActivity(RegisterActivity())
        }

    }


    private fun initFields() {
        mToolbar = mBinding.mainToolbar
        mAppDrawer = AppDrawer(this, mToolbar)
    }

    private fun initUser() {
        REF_DATABASE_ROOT.child(NODE_USERS).child(UID)
            .addListenerForSingleValueEvent(AppValueEventListener{
                USER = it.getValue(User::class.java) ?: User()
            })
    }

    companion object {
        fun getLaunchIntent(from: Context) = Intent(from, MainActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }
    }
}

