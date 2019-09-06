package com.example.withpet.ui.diary.usecase

import com.example.withpet.vo.diary.DiaryDTO
import io.reactivex.Single

interface DiaryAddUseCase {
    fun addDiary(diary: DiaryDTO): Single<Boolean>
}


class DiaryAddUseCaseImpl : DiaryAddUseCase {
    override fun addDiary(diary: DiaryDTO): Single<Boolean> {
        return Single.just(true)
    }
}