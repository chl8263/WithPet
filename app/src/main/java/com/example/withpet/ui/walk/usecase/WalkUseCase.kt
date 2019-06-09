package com.example.withpet.ui.walk.usecase

import com.example.withpet.vo.WalkBicycleDTOList

interface WalkUseCase {
    fun getBicycleList() : WalkBicycleDTOList
}