package com.example.withpet.ui.walk.usecase

import com.example.withpet.vo.walk.WalkBicycleDTO
import com.example.withpet.vo.walk.WalkBicycleDTOList
import io.reactivex.Observable

interface WalkUseCase {

    fun insertBicycleList() : Observable<Boolean>

    fun searchBicycleList(keyword : String) /*: Observable<ArrayList<WalkBicycleDTO>>*/

    fun getBicycleList() : Observable<WalkBicycleDTOList>
}