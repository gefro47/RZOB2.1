package com.example.rzob21.UI.fragments

import com.example.rzob21.MainActivity
import com.example.rzob21.R
import com.example.rzob21.activites.RegisterActivity
import com.example.rzob21.utilits.AUTH
import com.example.rzob21.utilits.USER
import com.example.rzob21.utilits.replaceActivity
import com.example.rzob21.utilits.replaceFragment
import kotlinx.android.synthetic.main.fragment_settings.*

class SettingsFragment : BaseFragment(R.layout.fragment_settings) {

    override fun onResume() {
        super.onResume()
        imageViewExit.setOnClickListener {
            AUTH.signOut()
            (activity as MainActivity).replaceActivity(RegisterActivity())
        }

        setHasOptionsMenu(true)
        initFields()
    }

    private fun initFields() {
        settings_position.text = USER.position
        settings_btn_change_position.setOnClickListener { replaceFragment(ChangePositionFragment())}
        settings_btn_change_salary.setOnClickListener { replaceFragment(ChangeSalaryFragment()) }
    }


}