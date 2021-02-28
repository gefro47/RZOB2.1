package com.example.rzob21

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import com.example.rzob21.UI.fragments.CalendarFragment
import com.example.rzob21.UI.objects.AppDrawer
import com.example.rzob21.activites.RegisterActivity
import com.example.rzob21.databinding.ActivityMainBinding
import com.example.rzob21.models.User
import com.example.rzob21.utilits.*
import com.example.rzob21.viewmodel.UserLocalDBViewModel
import androidx.lifecycle.Observer
import com.example.rzob21.API.ApiRequests
import com.example.rzob21.models.UserLocalDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://raw.githubusercontent.com/"

class MainActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding
    lateinit var mAppDrawer: AppDrawer
    private lateinit var mToolbar: androidx.appcompat.widget.Toolbar
    private lateinit var mUserLocalDBViewModel: UserLocalDBViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        APP_ACTIVITY = this
        initFields()
        initFirebase()
        CoroutineScope(Dispatchers.IO).launch {
            initUser()
        }
        initDays()
        initFunc()
    }

    private fun initDays(){
        val api = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiRequests::class.java)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = api.getholidays().awaitResponse()
                if (response.isSuccessful) {
                    val data = response.body()!!
                    withContext(Dispatchers.Main) {
                        LIST_OF_HOLIDAYS = mutableListOf()
                        LIST_OF_PREHOLIDAYS = mutableListOf()
                        LIST_OF_NOTWORK2020 = mutableListOf()
                        data.holidays.forEach { holidays ->
                            LIST_OF_HOLIDAYS.add(holidays)
                        }
                        data.preholidays.forEach { preholidays ->
                            LIST_OF_PREHOLIDAYS.add(preholidays)
                        }
                        data.nowork2020.forEach { nowork2020 ->
                            LIST_OF_NOTWORK2020.add(nowork2020)
                        }
                        Log.d("kek1", LIST_OF_NOTWORK2020.toString())
                        Log.d("kek1", LIST_OF_HOLIDAYS.toString())
                        Log.d("kek1", LIST_OF_PREHOLIDAYS.toString())
                    }

                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main){
                    showToast("Выходные и празднечные дни не загрузились!")
                }
            }
        }
    }


    private fun initFunc() {
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

    private suspend fun initUser() {
        mUserLocalDBViewModel = ViewModelProvider(this).get(UserLocalDBViewModel::class.java)
//        val user = UserLocalDB(UID, "+7", "zuk", 8.0, 79.0)
        //ad data to database
//        mUserLocalDBViewModel.updateUser(user)
//        REF_DATABASE_ROOT.child(NODE_USERS).child(UID)
//            .addListenerForSingleValueEvent(AppValueEventListener{
////                USER = it.getValue(User::class.java) ?: User()
//            })
        mUserLocalDBViewModel.findByUID(UID).collect {
            it.let{
                val user: UserLocalDB = it[0]
                USER = user
                Log.d("kek", "$user")
            }
        }
    }
}

