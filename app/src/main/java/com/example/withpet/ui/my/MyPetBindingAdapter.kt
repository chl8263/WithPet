package com.example.withpet.ui.my

import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.example.withpet.R

object MyPetBindingAdapter {


    @JvmStatic
    @BindingAdapter("app:gender")
    fun setGenderImage(view: AppCompatTextView, gender: Int?) {
        gender?.let {
            val context = view.context

            val drawableMale = ContextCompat.getDrawable(context, R.drawable.ic_gender_male)
            val drawableFemale = ContextCompat.getDrawable(context, R.drawable.ic_gender_female)
            val drawable = if (it == 0) drawableMale else drawableFemale
            drawable?.let { genderDrawable ->
                view.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, genderDrawable, null)
            }

        }
    }

}