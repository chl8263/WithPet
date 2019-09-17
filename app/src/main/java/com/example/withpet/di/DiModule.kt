package com.example.withpet.di

import com.example.withpet.ui.diary.DiaryAddViewModel
import com.example.withpet.ui.diary.usecase.DiaryAddUseCase
import com.example.withpet.ui.diary.usecase.DiaryAddUseCaseImpl
import com.example.withpet.ui.hospital.hospitalMain.adapter.HospitalSearchRecyclerViewAdapter
import com.example.withpet.ui.hospital.hospitalMain.HospitalViewModel
import com.example.withpet.ui.hospital.hospitalMain.adapter.HospitalHistorySearchRecyclerViewAdapter
import com.example.withpet.ui.hospital.hospitalMain.usecase.HistoryRepository
import com.example.withpet.ui.hospital.hospitalMain.usecase.HospitalRepository
import com.example.withpet.ui.hospital.hospitalMain.usecase.LocationUseCase
import com.example.withpet.ui.hospital.hospitalMain.usecase.impl.HistoryRepositoryImpl
import com.example.withpet.ui.hospital.hospitalMain.usecase.impl.HospitalRepositoryImpl
import com.example.withpet.ui.hospital.hospitalMain.usecase.impl.LocationUseCaseImpl
import com.example.withpet.ui.hospital.hospitalComment.HosCommentViewModel
import com.example.withpet.ui.hospital.hospitalComment.usecase.HospitalCommentRepository
import com.example.withpet.ui.hospital.hospitalComment.usecase.impl.HospitalCommentRepositoryImpl
import com.example.withpet.ui.hospital.hospitalDetail.HosDetailViewModel
import com.example.withpet.ui.hospital.hospitalDetail.adapter.HospitalDetailReviewRecyclerViewAdapter
import com.example.withpet.ui.hospital.hospitalDetail.usecase.HospitalStarRepository
import com.example.withpet.ui.hospital.hospitalDetail.usecase.impl.HospitalStarRepositoryImpl
import com.example.withpet.ui.join.JoinViewModel
import com.example.withpet.ui.join.usecase.JoinUseCase
import com.example.withpet.ui.join.usecase.JoinUseCaseImpl
import com.example.withpet.ui.login.LoginViewModel
import com.example.withpet.ui.login.usecase.LoginUseCase
import com.example.withpet.ui.login.usecase.LoginUseCaseImpl
import com.example.withpet.ui.main.MainViewModel
import com.example.withpet.ui.my.MyViewModel
import com.example.withpet.ui.pat.PatAddViewModel
import com.example.withpet.ui.pat.usecase.ImageUseCase
import com.example.withpet.ui.pat.usecase.ImageUseCaseImpl
import com.example.withpet.ui.pat.usecase.PatUseCase
import com.example.withpet.ui.pat.usecase.PatUseCaseImpl
import com.example.withpet.ui.walk.WalkViewModel
import com.example.withpet.ui.walk.usecase.WalkUseCase
import com.example.withpet.ui.walk.usecase.impl.WalkUseCaseImpl
import com.example.withpet.util.DBManager
import org.koin.android.ext.koin.androidApplication
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

    single<PatUseCase> { PatUseCaseImpl() }
    single<ImageUseCase> { ImageUseCaseImpl() }
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

    viewModel {
        PatAddViewModel(androidApplication(), get(), get())
    }

    viewModel {
        MyViewModel(get())
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