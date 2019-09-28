package com.example.withpet.ui.walk.usecase.impl

import android.content.Context
import com.example.withpet.R
import com.example.withpet.ui.walk.usecase.WalkMainUseCase
import com.example.withpet.util.Formatter
import com.example.withpet.util.Log
import com.example.withpet.util.Util
import com.example.withpet.vo.walk.WalkBicycleDTO
import com.example.withpet.vo.walk.WalkBicycleDTOList
import com.example.withpet.vo.walk.WalkParkDTO
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.reactivex.Single


class WalkMainUseCaseImpl(var context: Context) : WalkMainUseCase {

    private val db = FirebaseFirestore.getInstance()
    private val bicycleDB = db.collection(WALK_BICYCLE)
    private val parkDB = db.collection(WALK_PARK)

    override fun insertBicycleList(): Single<Boolean> {
        return Single.create { emitter ->

            val raw = Util.raw2string(context, R.raw.test_bicycle)
            val temp = Gson().fromJson(raw, WalkBicycleDTOList::class.java).parseData()

            val batch = db.batch()
            temp.bicycleDTOList.forEach { data ->
                val id = BICYCLE + "_" + Formatter.getDbIdFormat(data.objectid)
                batch.set(bicycleDB.document(id), data)
            }
            batch.commit().addOnCompleteListener {
                Log.w("Database Insert Finish")
                emitter.onSuccess(true)
            }
        }
    }

    override fun insertParkList(): Single<Boolean> {
        return Single.create { emitter ->

            val raw = Util.raw2string(context, R.raw.test_park)
            val temp = Gson().fromJson<List<WalkParkDTO>>(
                raw,
                object : TypeToken<List<WalkParkDTO>>() {}.type
            )

            val batch = db.batch()
            temp.forEach { data ->
                val id = PARK + "_" + data._name
                batch.set(parkDB.document(id), data)
            }
            batch.commit().addOnCompleteListener {
                Log.w("Database Insert Finish")
                emitter.onSuccess(true)
            }
        }
    }

    // todo keyword로 시작하는 장소만 찾을 수 있음, like operator 사용 필요...
    override fun searchBicycleList(keyword: String): Single<MutableList<WalkBicycleDTO>> {
        return Single.create { emitter ->
            Log.w("searchBicycleList $keyword Start")
            bicycleDB.orderBy(_NAME).startAt(keyword).endAt(keyword + '\uf8ff').get()
                .addOnSuccessListener {
                    Log.w("searchBicycleList Finish")
                    emitter.onSuccess(it.toObjects(WalkBicycleDTO::class.java))
                }
        }
    }

    override fun searchParkList(keyword: String): Single<MutableList<WalkParkDTO>> {
        return Single.create { emitter ->
            Log.w("searchParkList $keyword Start")
            parkDB.orderBy(_NAME).startAt(keyword).endAt(keyword + '\uf8ff').get()
                .addOnSuccessListener {
                    Log.w("searchParkList Finish")
                    emitter.onSuccess(it.toObjects(WalkParkDTO::class.java))
                }
        }
    }

    override fun getBicycleList(): Single<List<WalkBicycleDTO>> {
        return Single.create { emitter ->
            bicycleDB.get().addOnSuccessListener {
                val result = it.toObjects(WalkBicycleDTO::class.java)
                Log.w("bicycle list Finish : ${result.size}")
                emitter.onSuccess(result)
            }
        }
    }

    override fun getParkList(): Single<List<WalkParkDTO>> {
        return Single.create { emitter ->
            parkDB.get().addOnSuccessListener {
                val result = it.toObjects(WalkParkDTO::class.java)
                Log.w("park list Finish : ${result.size}")
                emitter.onSuccess(result)
            }
        }
    }

    companion object {
        private const val BICYCLE = "BICYCLE"
        private const val PARK = "PARK"
        private const val WALK_BICYCLE = "WALK_BICYCLE"
        private const val WALK_PARK = "WALK_PARK"
        private const val _NAME = "_name"
    }

}