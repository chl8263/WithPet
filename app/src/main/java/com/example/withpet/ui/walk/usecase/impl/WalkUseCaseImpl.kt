package com.example.withpet.ui.walk.usecase.impl

import android.content.Context
import com.example.withpet.R
import com.example.withpet.ui.walk.usecase.WalkUseCase
import com.example.withpet.util.Util
import com.example.withpet.vo.walk.WalkBicycleDTOList
import com.google.gson.Gson
import io.reactivex.Observable

class WalkUseCaseImpl(var context: Context) : WalkUseCase {
    override fun getBicycleList(): Observable<WalkBicycleDTOList> {
        return Observable.create { emitter ->
            val raw = Util.raw2string(context, R.raw.bicycle)
            emitter.onNext(Gson().fromJson(raw, WalkBicycleDTOList::class.java))
        }

    }

}