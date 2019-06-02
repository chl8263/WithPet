package com.example.withpet.ui.walk

import com.example.withpet.core.BaseViewModel

class WalkViewModel : BaseViewModel() {

    lateinit var goMapActivity : () -> Unit?

    fun goMapActivity() {
        goMapActivity.invoke()
    }

}