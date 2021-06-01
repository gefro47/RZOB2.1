package com.example.rzob21.UI.objects

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.example.rzob21.R
import com.example.rzob21.UI.fragments.HistoryOfIncomeFragment
import com.example.rzob21.UI.fragments.MonthInfoFragment
import com.example.rzob21.UI.fragments.SettingsFragment
import com.example.rzob21.utilits.APP_ACTIVITY
import com.example.rzob21.utilits.replaceFragment
import com.example.rzob21.utilits.showToast
import com.mikepenz.materialdrawer.AccountHeader
import com.mikepenz.materialdrawer.AccountHeaderBuilder
import com.mikepenz.materialdrawer.Drawer
import com.mikepenz.materialdrawer.DrawerBuilder
import com.mikepenz.materialdrawer.model.DividerDrawerItem
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem
import com.mikepenz.materialdrawer.model.ProfileDrawerItem
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem

class AppDrawer (val mainActivity: AppCompatActivity,private val toolbar: androidx.appcompat.widget.Toolbar){
    private lateinit var mDrawer: Drawer
//    private lateinit var mHeader: AccountHeader
    private lateinit var mDrawerLayout: DrawerLayout
//    private lateinit var mCurrentProfile: ProfileDrawerItem

    fun create() {
//        createHeader()
        createDrawer()
        mDrawerLayout = mDrawer.drawerLayout
    }

    fun disableDrawer(){
        mDrawer.actionBarDrawerToggle?.isDrawerIndicatorEnabled = false
        mainActivity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        toolbar.setNavigationOnClickListener {
            mainActivity.supportFragmentManager.popBackStack()
        }
    }

    fun enableDrawer(){
        mainActivity.supportActionBar?.setDisplayHomeAsUpEnabled(false)
        mDrawer.actionBarDrawerToggle?.isDrawerIndicatorEnabled = true
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
        toolbar.setNavigationOnClickListener {
            mDrawer.openDrawer()
        }
    }

    private fun createDrawer() {
        mDrawer = DrawerBuilder()
            .withActivity(mainActivity)
            .withToolbar(toolbar)
            .withActionBarDrawerToggle(true)
            .withSelectedItem(-1)
//            .withAccountHeader(mHeader)
            .addDrawerItems(
                PrimaryDrawerItem().withIdentifier(100)
                    .withName("Переработка за день")
                    .withSelectable(false)
                    .withIcon(R.drawable.ic_menu_pererab_day),
                PrimaryDrawerItem().withIdentifier(101)
                    .withName("Инфо за месяц")
                    .withSelectable(false)
                    .withIcon(R.drawable.ic_menu_info_month),
                PrimaryDrawerItem().withIdentifier(102)
                    .withName("История доходов")
                    .withSelectable(false)
                    .withIcon(R.drawable.ic_history_drawer),
                PrimaryDrawerItem().withIdentifier(103)
                    .withName("Отпуск")
                    .withSelectable(false)
                    .withIcon(R.drawable.ic_menu_otpusc),
                PrimaryDrawerItem().withIdentifier(104)
                    .withName("Больничный")
                    .withSelectable(false)
                    .withIcon(R.drawable.ic_menu_bolnich),
                PrimaryDrawerItem().withIdentifier(105)
                    .withName("Командировка")
                    .withSelectable(false)
                    .withIcon(R.drawable.ic_menu_komandir),
                DividerDrawerItem(),
                PrimaryDrawerItem().withIdentifier(106)
                    .withName("Настройки")
                    .withSelectable(false)
                    .withIcon(R.drawable.ic_menu_settings),
            ).withOnDrawerItemClickListener(object : Drawer.OnDrawerItemClickListener{
                override fun onItemClick(
                    view: View?,
                    position: Int,
                    drawerItem: IDrawerItem<*>
                ): Boolean {
                    when(position){
                        1 -> mainActivity.replaceFragment(MonthInfoFragment())
                        2 -> APP_ACTIVITY.replaceFragment(HistoryOfIncomeFragment())
                        7 -> mainActivity.replaceFragment(SettingsFragment())
                    }
//                    showToast(position.toString())
                    return false
                }

            }).build()
    }

//    private fun createHeader() {
//        mHeader = AccountHeaderBuilder()
//            .withActivity(mainActivity)
//            .withHeaderBackground(R.drawable.header)
////            .addProfiles(
////                ProfileDrawerItem()
////                    .withName("Dima")
////                    .withEmail("@gmail.com")
////            )
//            .build()
//    }

}