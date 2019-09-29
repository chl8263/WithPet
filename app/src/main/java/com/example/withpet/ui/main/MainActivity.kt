package com.example.withpet.ui.main

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.example.withpet.R
import com.example.withpet.core.BaseActivity
import com.example.withpet.databinding.MainBinding
import com.example.withpet.ui.abandon.AbdFragment
import com.example.withpet.ui.hospital.callBackListener.OnFragmentBackListener
import com.example.withpet.ui.hospital.hospitalMain.HospitalFragment
import com.example.withpet.ui.my.MyFragment
import com.example.withpet.ui.walk.WalkMainFragment
import com.example.withpet.util.Auth
import com.example.withpet.util.Log
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import com.sang.permission.SPermission
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity() {

    lateinit var binding: MainBinding

    val viewModel: MainViewModel by viewModel()

    private var mFragmentBackListener: OnFragmentBackListener? =
        null   // hospital fragment 에서 onBackPressed catch 하기위한 Listener

    private var isDialogFragmentInstance: Boolean = false   // hospital fragment 에서 onBackPressed catch 하기위한 Listener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.viewModel = viewModel
        viewModel.mNavigationItemSelectedListener = navigationSelectedListener

        Log.i("email : ${Auth.getEmail()}, disPlayName : ${Auth.getDisplayName()}")
        replaceFragment(MyFragment())
    }

    override fun onDestroy() {
        super.onDestroy()
        Auth.signOut()
    }

    fun replaceFragment(fragment: androidx.fragment.app.Fragment) {
        val ft: androidx.fragment.app.FragmentTransaction =
            supportFragmentManager.beginTransaction()
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
                    SPermission.Builder(this).apply {
                        permissions = arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION)
                        onGranted = { replaceFragment(WalkMainFragment.newInstance()) }
                        onDenied = {
                            Snackbar.make(binding.root, "위치 권한을 허용해 주셔야 산책로 기능을 사용하실 수 있어요!", Snackbar.LENGTH_SHORT)
                                .show()
                        }
                        check()
                    }
                    return@OnNavigationItemSelectedListener true
                }

                R.id.hospital -> {
                    SPermission.Builder(this).apply {
                        permissions = arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION)
                        onGranted = { replaceFragment(HospitalFragment.newInstance()) }
                        onDenied = {
                            Snackbar.make(binding.root, "위치 권한을 허용해 주셔야 동물병원 기능을 사용하실 수 있어요!", Snackbar.LENGTH_SHORT)
                                .show()
                        }
                        check()
                    }
                    return@OnNavigationItemSelectedListener true
                }
                R.id.four -> {
                    replaceFragment(AbdFragment.newInstance())
                    return@OnNavigationItemSelectedListener true
                }
            }
            return@OnNavigationItemSelectedListener false
        }

    fun setOnBackPressedByFragment(onBackPressedListener: OnFragmentBackListener?) {
        this.mFragmentBackListener = onBackPressedListener
    }

    fun setIsDialogFragmentInstance(bool: Boolean) {
        isDialogFragmentInstance = bool
    }

    override fun onBackPressed() {
        when {
            supportFragmentManager.backStackEntryCount > 0 -> super.onBackPressed()
            mFragmentBackListener != null -> mFragmentBackListener!!.onBack()
            else -> super.onBackPressed()
        }
    }
}
