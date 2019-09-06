package com.example.withpet.di

import com.example.withpet.ui.diary.DiaryAddViewModel
import com.example.withpet.ui.diary.usecase.DiaryAddUseCase
import com.example.withpet.ui.diary.usecase.DiaryAddUseCaseImpl
import com.example.withpet.ui.hospital.adapter.HospitalSearchRecyclerViewAdapter
import com.example.withpet.ui.hospital.HospitalViewModel
import com.example.withpet.ui.hospital.adapter.HospitalHistorySearchRecyclerViewAdapter
import com.example.withpet.ui.hospital.usecase.HistoryRepository
import com.example.withpet.ui.hospital.usecase.HospitalRepository
import com.example.withpet.ui.hospital.usecase.LocationUseCase
import com.example.withpet.ui.hospital.usecase.impl.HistoryRepositoryImpl
import com.example.withpet.ui.hospital.usecase.impl.HospitalRepositoryImpl
import com.example.withpet.ui.hospital.usecase.impl.LocationUseCaseImpl
import com.example.withpet.ui.hospitalComment.HosCommentViewModel
import com.example.withpet.ui.hospitalComment.usecase.HospitalCommentRepository
import com.example.withpet.ui.hospitalComment.usecase.impl.HospitalCommentRepositoryImpl
import com.example.withpet.ui.hospitalDetail.HosDetailViewModel
import com.example.withpet.ui.hospitalDetail.adapter.HospitalDetailReviewRecyclerViewAdapter
import com.example.withpet.ui.hospitalDetail.usecase.HospitalStarRepository
import com.example.withpet.ui.hospitalDetail.usecase.impl.HospitalStarRepositoryImpl
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
import com.example.withpet.util.DBManager
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


var userCasePart = module {

    single<LocationUseCase> { LocationUseCaseImpl(androidContext()) }

    single<HospitalRepository> { HospitalRepositoryImpl() }

    single<HospitalCommentRepository> { HospitalCommentRepositoryImpl() }

    single<HistoryRepository> { HistoryRepositoryImpl(get()) }

    single<HospitalStarRepository> { HospitalStarRepositoryImpl() }

    single<JoinUseCase> { JoinUseCaseImpl() }

    single<LoginUseCase> { LoginUseCaseImpl() }

    single<WalkUseCase> { WalkUseCaseImpl(androidContext()) }

    single<DiaryAddUseCase> { DiaryAddUseCaseImpl() }
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
        HospitalViewModel(get(), get(), get())
    }
    viewModel {
        HosDetailViewModel(get(), get())
    }
    viewModel {
        HosCommentViewModel(get())
    }

    viewModel {
        DiaryAddViewModel(get())
    }
}

var recyclerViewAdapterPart = module {
    single {
        HospitalSearchRecyclerViewAdapter(get())
    }
    single {
        HospitalHistorySearchRecyclerViewAdapter(get())
    }
    single {
        HospitalDetailReviewRecyclerViewAdapter()
    }
}

var dbManagerPart = module {
    single {
        DBManager(androidContext())
    }
}


var diModule = listOf(
        viewModelPart, recyclerViewAdapterPart, userCasePart, dbManagerPart
)