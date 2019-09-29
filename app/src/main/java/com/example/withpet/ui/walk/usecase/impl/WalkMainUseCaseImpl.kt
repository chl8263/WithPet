package com.example.withpet.ui.walk.usecase.impl

import android.content.Context
import com.example.withpet.R
import com.example.withpet.ui.walk.usecase.WalkMainUseCase
import com.example.withpet.util.Log
import com.example.withpet.util.Util
import com.example.withpet.vo.walk.WalkParkDTO
import com.example.withpet.vo.walk.WalkTrailDTO
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.reactivex.Single


class WalkMainUseCaseImpl(var context: Context) : WalkMainUseCase {

    private val db = FirebaseFirestore.getInstance()
    private val parkDB = db.collection(WALK_PARK)
    private val trailDB = db.collection(WALK_TRAIL)

    override fun insertParkList() {
        val raw = Util.raw2string(context, R.raw.test_park)
        val temp = Gson().fromJson<List<WalkParkDTO>>(raw, object : TypeToken<List<WalkParkDTO>>() {}.type)

        val batch = db.batch()
        temp.forEach { data ->
            val id = PARK + "_" + data._name
            batch.set(parkDB.document(id), data)
        }
        batch.commit().addOnCompleteListener {
            Log.w("Database Insert Finish")
        }
    }

    override fun insertTrailList() {
        Log.w("test 진입")
        val raw = Util.raw2string(context, R.raw.test_trail)
        val temp = Gson().fromJson<List<WalkTrailDTO>>(raw, object : TypeToken<List<WalkTrailDTO>>() {}.type)

        val batch = db.batch()

        val targetCourseName = ArrayList<String>()
        temp.forEach { data ->
            if (!targetCourseName.contains(data.course_name)) {
                targetCourseName.add(data.course_name)
                val id = TRAIL + "_" + data._name
                batch.set(trailDB.document(id), data)
            }
        }
        batch.commit().addOnCompleteListener {
            Log.w("Database Insert Finish")
        }
    }

    // todo keyword로 시작하는 장소만 찾을 수 있음, like operator 사용 필요...

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

    override fun searchTrailList(keyword: String): Single<MutableList<WalkTrailDTO>> {
        return Single.create { emitter ->
            Log.w("searchTrailList $keyword Start")
            trailDB.orderBy(_NAME).startAt(keyword).endAt(keyword + '\uf8ff').get()
                    .addOnSuccessListener {
                        Log.w("searchTrailList Finish")
                        emitter.onSuccess(it.toObjects(WalkTrailDTO::class.java))
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

    override fun getTrailList(): Single<List<WalkTrailDTO>> {
        return Single.create { emitter ->
            trailDB.get().addOnSuccessListener {
                val result = it.toObjects(WalkTrailDTO::class.java)
                Log.w("trail list Finish : ${result.size}")
                result.forEach {
                    Log.w("${it.course_name}, ${it._longitude}, ${it._latitude}" )
                }
                emitter.onSuccess(result)
            }
        }
    }

    companion object {
        private const val PARK = "PARK"
        private const val TRAIL = "TRAIL"
        private const val WALK_PARK = "WALK_PARK"
        private const val WALK_TRAIL = "WALK_TRAIL"
        private const val _NAME = "_name"
    }

}