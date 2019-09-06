package com.example.withpet.ui.diary.usecase

import com.example.withpet.vo.diary.DiaryDTO
import io.reactivex.Single

interface DiaryListUseCase {
    fun getList(): Single<ArrayList<DiaryDTO>>
}
