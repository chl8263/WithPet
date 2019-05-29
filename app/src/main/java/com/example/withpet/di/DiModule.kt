package com.example.withpet.di

import com.example.withpet.ui.join.JoinViewModel
import com.example.withpet.ui.login.LoginViewModel
import com.example.withpet.ui.main.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

var repositoryPart = module {
    //single<HelloRepository> { HelloRepositoryImpl() }
}

var viewModelPart = module {
    viewModel {
        MainViewModel()
    }
    viewModel {
        LoginViewModel()
    }
    viewModel {
        JoinViewModel()
    }
}

var recyclerViewAdapterPart = module {

}


var diModule = listOf(
    viewModelPart, recyclerViewAdapterPart
)