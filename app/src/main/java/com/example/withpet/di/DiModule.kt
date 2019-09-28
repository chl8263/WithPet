package com.example.withpet.di

import com.example.withpet.ui.abandon.AbdViewModel
import com.example.withpet.ui.abandon.usecase.AbdDataSource
import com.example.withpet.ui.abandon.usecase.AbdUseCase
import com.example.withpet.ui.abandon.usecase.AbdUseCaseImpl
import com.example.withpet.ui.diary.DiaryEditViewModel
import com.example.withpet.ui.diary.usecase.DiaryUseCase
import com.example.withpet.ui.diary.usecase.DiaryUseCaseImpl
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
import com.example.withpet.ui.hospital.hospitalMain.adapter.HospitalCardViewRecyclerViewAdapter
import com.example.withpet.ui.join.JoinViewModel
import com.example.withpet.ui.join.usecase.JoinUseCase
import com.example.withpet.ui.join.usecase.JoinUseCaseImpl
import com.example.withpet.ui.login.LoginViewModel
import com.example.withpet.ui.login.usecase.LoginUseCase
import com.example.withpet.ui.login.usecase.LoginUseCaseImpl
import com.example.withpet.ui.main.MainViewModel
import com.example.withpet.ui.my.MyPetViewModel
import com.example.withpet.ui.my.MyViewModel
import com.example.withpet.ui.pet.PetEditViewModel
import com.example.withpet.ui.pet.petHospital.PetHospitalViewModel
import com.example.withpet.ui.pet.usecase.ImageUseCase
import com.example.withpet.ui.pet.usecase.ImageUseCaseImpl
import com.example.withpet.ui.pet.usecase.PetUseCase
import com.example.withpet.ui.pet.usecase.PetUseCaseImpl
import com.example.withpet.ui.walk.WalkMainViewModel
import com.example.withpet.ui.walk.usecase.WalkMainUseCase
import com.example.withpet.ui.walk.usecase.impl.WalkMainUseCaseImpl
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

    single<WalkMainUseCase> { WalkMainUseCaseImpl(androidContext()) }

    single<DiaryUseCase> { DiaryUseCaseImpl() }

    single<PetUseCase> { PetUseCaseImpl() }

    single<ImageUseCase> { ImageUseCaseImpl(androidApplication()) }

    single<AbdDataSource> { createNetService(get()) }
    single<AbdUseCase> { AbdUseCaseImpl(androidApplication(), get()) }
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
        WalkMainViewModel(get(), get())
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
        DiaryEditViewModel(androidApplication(), get(), get())
    }

    viewModel {
        PetEditViewModel(get(), get())
    }

    viewModel {
        PetHospitalViewModel(get(), get(), get())
    }

    viewModel {
        MyViewModel(get())
    }
    viewModel {
        MyPetViewModel(get(), get())
    }

    viewModel {
        AbdViewModel(get())
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
    single {
        HospitalCardViewRecyclerViewAdapter()
    }
}

var dbManagerPart = module {
    single {
        DBManager(androidContext())
    }
}


var diModule = listOf(
        viewModelPart, recyclerViewAdapterPart, userCasePart, dbManagerPart, netModule
)