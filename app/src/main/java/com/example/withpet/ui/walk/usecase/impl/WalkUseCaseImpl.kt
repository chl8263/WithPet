package com.example.withpet.ui.walk.usecase.impl

import android.content.Context
import com.example.withpet.R
import com.example.withpet.ui.walk.usecase.WalkUseCase
import com.example.withpet.util.Util
import com.example.withpet.vo.walk.WalkBicycleDTOList
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import io.reactivex.Observable

class WalkUseCaseImpl(var context: Context) : WalkUseCase {
    private val db = FirebaseFirestore.getInstance()

    override fun insertBicycleList(): Boolean {
//        db.collection("cities").document("new-city-id").set(data)
        //todo 여기부터
        db.collection(WALK_BICYCLE).document("")
        return true
    }

    override fun getBicycleList(): Observable<WalkBicycleDTOList> {
        return Observable.create { emitter ->
            val raw = Util.raw2string(context, R.raw.bicycle)
            emitter.onNext(Gson().fromJson(raw, WalkBicycleDTOList::class.java))
        }

    }

    companion object {
        private const val WALK_BICYCLE = "WALK_BICYCLE"
    }

}