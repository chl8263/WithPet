package com.example.withpet.ui.main

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.example.withpet.R
import com.example.withpet.core.BaseActivity
import com.example.withpet.databinding.MainBinding
import com.example.withpet.ui.hospital.HospitalFragment
import com.example.withpet.ui.my.MyFragment
import com.example.withpet.ui.walk.WalkFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity() {

    lateinit var binding: MainBinding

    val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.viewModel = viewModel
        viewModel.mNavigationItemSelectedListener = navigationSelectedListener

    }

    fun replaceFragment(fragment: androidx.fragment.app.Fragment) {
        val ft: androidx.fragment.app.FragmentTransaction = supportFragmentManager.beginTransaction()
        ft.replace(R.id.container, fragment).commit()
    }

    var navigationSelectedListener: BottomNavigationView.OnNavigationItemSelectedListener =
            BottomNavigationView.OnNavigationItemSelectedListener { menuItem ->

                when (menuItem.itemId) {
                    R.id.one -> {
                        replaceFragment(MyFragment())
                        return@OnNavigationItemSelectedListener true
                    }
                    R.id.two -> {
                        replaceFragment(WalkFragment.newInstance())
                        return@OnNavigationItemSelectedListener true
                    }

                    R.id.hospital -> {
                        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                            replaceFragment(HospitalFragment.newInstance())
                        }
                        return@OnNavigationItemSelectedListener true
                    }
//            R.id.four -> {
//                replaceFragment(AlarmFragment.newInstance())
//                return@OnNavigationItemSelectedListener true
//            }

                    else -> false

                }
                return@OnNavigationItemSelectedListener false
            }
}
