package com.example.withpet.ui.walk.usecase.impl

import android.content.Context
import com.example.withpet.R
import com.example.withpet.ui.walk.usecase.WalkUseCase
import com.example.withpet.util.Util
import com.example.withpet.vo.WalkBicycleDTOList
import com.google.gson.Gson

class WalkUseCaseImpl(var context : Context) : WalkUseCase {
    override fun getBicycleList(): WalkBicycleDTOList {
//        val liveData = SingleLiveEvent<WalkBicycleDTOList>()
        val raw = Util.raw2string(context, R.raw.bicycle)
        return Gson().fromJson(raw, WalkBicycleDTOList::class.java)
    }

}