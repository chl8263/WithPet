package com.example.withpet.di

import com.example.withpet.ui.hospital.HospitalSearchRecyclerViewAdapter
import com.example.withpet.ui.hospital.HospitalViewModel
import com.example.withpet.ui.hospital.usecase.HospitalRepository
import com.example.withpet.ui.hospital.usecase.LocationUseCase
import com.example.withpet.ui.hospital.usecase.impl.HospitalRepositoryImpl
import com.example.withpet.ui.hospital.usecase.impl.LocationUseCaseImpl
import com.example.withpet.ui.join.JoinViewModel
import com.example.withpet.ui.join.usecase.JoinUseCase
import com.example.withpet.ui.join.usecase.JoinUseCaseImpl
import com.example.withpet.ui.login.LoginViewModel
import com.example.withpet.ui.login.usecase.LoginUseCase
import com.example.withpet.ui.login.usecase.LoginUseCaseImpl
import com.example.withpet.ui.main.MainViewModel
import com.example.withpet.ui.walk.WalkViewModel
import com.example.withpet.ui.walk.usecase.WalkUseCase
import com.example.withpet.ui.walk.usecase.impl.WalkUseCaseImpl
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


var userCasePart = module {

    single<LocationUseCase> { LocationUseCaseImpl(androidContext()) }
    single<HospitalRepository> { HospitalRepositoryImpl() }

    single<JoinUseCase> { JoinUseCaseImpl() }

    single<LoginUseCase> { LoginUseCaseImpl() }

    single<WalkUseCase> { WalkUseCaseImpl(androidContext()) }
}

var viewModelPart = module {
    viewModel {
        MainViewModel()
    }
    viewModel {
        LoginViewModel(get())
    }
    viewModel {
        JoinViewModel(get())
    }
    viewModel {
        WalkViewModel(get(), get())
    }
    viewModel {
        HospitalViewModel(get(),get())
    }
}

var recyclerViewAdapterPart = module {
    single {
        HospitalSearchRecyclerViewAdapter()
    }
}


var diModule = listOf(
        viewModelPart, recyclerViewAdapterPart, userCasePart
)