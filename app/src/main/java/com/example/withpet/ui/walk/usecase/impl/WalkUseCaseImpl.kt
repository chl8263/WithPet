package com.example.withpet.ui.walk.usecase.impl

import android.content.Context
import com.example.withpet.R
import com.example.withpet.ui.walk.usecase.WalkUseCase
import com.example.withpet.util.Formatter
import com.example.withpet.util.Log
import com.example.withpet.util.Util
import com.example.withpet.vo.walk.WalkBicycleDTO
import com.example.withpet.vo.walk.WalkBicycleDTOList
import com.example.withpet.vo.walk.WalkParkDTO
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.reactivex.Observable


class WalkUseCaseImpl(var context: Context) : WalkUseCase {

    private val db = FirebaseFirestore.getInstance()
    private val bicycleDB = db.collection(WALK_BICYCLE)

    override fun insertBicycleList(): Observable<Boolean> {
        return Observable.create { emitter ->

            val raw = Util.raw2string(context, R.raw.test_bicycle)
            val temp = Gson().fromJson(raw, WalkBicycleDTOList::class.java).parseData()

            val batch = db.batch()
            temp.bicycleDTOList.forEach { data ->
                val id = WALK_BICYCLE + "_" + Formatter.getDbIdFormat(data.objectid)
                batch.set(bicycleDB.document(id), data)
            }
            batch.commit().addOnCompleteListener {
                Log.w("Database Insert Finish")
                emitter.onNext(true)
            }
        }
    }

    // todo keyword로 시작하는 장소만 찾을 수 있음, like operator 사용 필요...
    override fun searchWalkList(keyword: String): Observable<MutableList<WalkBicycleDTO>> {
        return Observable.create { emitter ->
            Log.w("searchWalkList $keyword Start")
            bicycleDB.orderBy(ROAD_NAME).startAt(keyword).endAt(keyword + '\uf8ff').get()
                .addOnSuccessListener {
                    Log.w("searchWalkList Finish")
                    emitter.onNext(it.toObjects(WalkBicycleDTO::class.java))
                }
        }
    }

    override fun getBicycleList(): Observable<List<WalkBicycleDTO>> {
        return Observable.create { emitter ->
            bicycleDB.get().addOnSuccessListener {
                val result = it.toObjects(WalkBicycleDTO::class.java)
                Log.toast(context, "bicycle list Finish : ${result.size}")
                emitter.onNext(result)
            }
        }
    }

    override fun getParkList(): Observable<List<WalkParkDTO>> {
        return Observable.create { emitter ->
            val raw = Util.raw2string(context, R.raw.test_park)
            val result = Gson().fromJson<List<WalkParkDTO>>(
                raw,
                object : TypeToken<List<WalkParkDTO>>() {}.type
            )
            Log.toast(context, "park list Finish : ${result.size}")
            emitter.onNext(result)
        }
    }

    companion object {
        private const val WALK_BICYCLE = "WALK_BICYCLE"
        private const val ROAD_NAME = "road_name"
    }

}