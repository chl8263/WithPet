package com.example.withpet.di

import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module



var viewModelPart = module {

}

var recyclerViewAdapterPart = module {

}


var diModule = listOf(
    viewModelPart, recyclerViewAdapterPart
)