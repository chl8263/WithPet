package com.example.withpet

import com.example.withpet.util.Const
import com.example.withpet.util.Log
import com.example.withpet.vo.HospitalSearchDTO
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    /**
     *  Hospital data firebase input
     *  json 데이터를 Gson 을이용하여 Vo 객체에 파싱하고 database에 push
     *  @author 최원균
     *  @since 2019.06.16
     * */
    @Test
    fun hospitalDataInput (){
        var gson = Gson()
        var test = com.example.withpet.vo.HospitalData.Test()

        //val token = TypeToken<ArrayList<HospitalSearchDTO>>()
        val turnsType = object : TypeToken<ArrayList<HospitalSearchDTO>>() {}.type
        var list : ArrayList<HospitalSearchDTO> = gson.fromJson(test.data, turnsType)
        var uid = "HOSPITAL_"
        var ins  = 689
        for( i in list) {
            Log.e(i)
            i.hospitalUid = uid+ins
            FirebaseFirestore.getInstance().collection(Const.COLECT_HOSPITAL).document(uid+ins).set(i).addOnCompleteListener {
                    task ->
                if(task.isSuccessful){
                    Log.e("ssssssssssss")
                }else {
                    Log.e(task.exception.toString())
                }
            }
            ins++
        }
    }
}
