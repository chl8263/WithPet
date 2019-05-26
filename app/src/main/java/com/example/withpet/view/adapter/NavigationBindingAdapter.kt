package com.example.withpet.view.adapter

import android.util.Log
import androidx.databinding.BindingAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView

object NavigationBindingAdapter {

    @JvmStatic
    @BindingAdapter("setOnNavigationItemSelected")
    fun setOnNavigationItemSelectedListener(view : BottomNavigationView, listener: BottomNavigationView.OnNavigationItemSelectedListener?){
        if(listener != null){
            view.setOnNavigationItemSelectedListener(listener)
        }else{
            Log.e("NaviBindingAdapter","listener null")
        }
    }

    @JvmStatic
    @BindingAdapter("setInitItemId")
    fun setInitItemId(view: BottomNavigationView, itemId: Int){
        view.selectedItemId = itemId
    }
}
