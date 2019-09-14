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

    // todo 배치 추가 필요
    override fun insertBicycleList(): Observable<Boolean> {
        return Observable.create { emitter ->
            val raw = Util.raw2string(context, R.raw.test_bicycle)
            val temp = Gson().fromJson(raw, WalkBicycleDTOList::class.java).parseData()
            var successCount = 0
            var cancelCount = 0
            var failCount = 0
            var finishCount = 0
            val target = temp.bicycleDTOList.subList(0, 100)
            target.forEach { data ->
                val id = WALK_BICYCLE + "_" + Formatter.getDbIdFormat(data.objectid)
                bicycleDB.document(id).set(data)
                        .addOnSuccessListener { successCount++ }
                        .addOnCanceledListener { cancelCount++ }
                        .addOnFailureListener { failCount++ }
                        .addOnCompleteListener {
                            finishCount++
                            if (finishCount == target.size) {
                                Log.w("Database Insert Finish\n" +
                                        "==================================================================================================\n" +
                                        "successCount = $successCount\n" +
                                        "cancelCount = $cancelCount\n" +
                                        "failCount = $failCount\n" +
                                        "finishCount = $finishCount\n" +
                                        "=================================================================================================="
                                )
                                emitter.onNext(true)
                            }
                        }
            }
        }
    }

    override fun searchBicycleList(keyword: String)/*: Observable<ArrayList<WalkBicycleDTO>>*/ {
        bicycleDB
                .whereEqualTo(ROAD_NAME, keyword)
                .get()
                .addOnSuccessListener { documents ->
                    Log.w("searchBicycleList Finish")
                    for (document in documents) {
                        Log.w("${document.id} => ${document.data}")
                    }
                }
                .addOnFailureListener { exception ->
                    Log.w("Error getting documents: ", exception)
                }

    }

    // todo 자전거도로 목록 가져오는 방식 변경
    override fun getBicycleList(): Observable<List<WalkBicycleDTO>> {
        return Observable.create { emitter ->
            bicycleDB.get().addOnSuccessListener {
                val result = it.toObjects(WalkBicycleDTO::class.java)
                Log.toast(context, "자전거도로 목록 가져왔어요.")
                emitter.onNext(result)
            }
        }
    }

    override fun getParkList(): Observable<List<WalkParkDTO>> {
        return Observable.create { emitter ->
            val raw = Util.raw2string(context, R.raw.test_park)
            emitter.onNext(Gson().fromJson(raw, object : TypeToken<List<WalkParkDTO>>() {}.type))
        }
    }

    companion object {
        private const val WALK_BICYCLE = "WALK_BICYCLE"
        private const val ROAD_NAME = "road_name"
    }

}