package com.example.withpet.di

import com.example.withpet.ui.hospital.HospitalViewModel
import com.example.withpet.ui.hospital.usecase.LocationUseCase
import com.example.withpet.ui.hospital.usecase.impl.LocationUseCaseImpl
import com.example.withpet.ui.join.JoinViewModel
import com.example.withpet.ui.login.LoginViewModel
import com.example.withpet.ui.main.MainViewModel
import com.example.withpet.ui.walk.WalkViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module



var userCasePart = module {

    single<LocationUseCase> { LocationUseCaseImpl(androidContext()) }
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
    viewModel {
        WalkViewModel()
    }
    viewModel {
        HospitalViewModel(get())
    }
}

var recyclerViewAdapterPart = module {

}


var diModule = listOf(
    viewModelPart, recyclerViewAdapterPart, userCasePart
)