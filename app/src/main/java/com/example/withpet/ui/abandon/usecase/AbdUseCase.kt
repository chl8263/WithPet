package com.example.withpet.ui.abandon.usecase

import android.content.Context
import com.example.withpet.R
import com.example.withpet.ui.abandon.enums.eSido
import com.example.withpet.ui.abandon.enums.eState
import com.example.withpet.ui.abandon.enums.eUpkind
import com.example.withpet.util.SDF
import com.example.withpet.vo.abandon.RawAbandonAnimalDTO
import io.reactivex.Single
import java.text.SimpleDateFormat
import java.util.*

interface AbdUseCase {
    fun getList(
        code: Int?,
        pageNo: Int
    ): Single<RawAbandonAnimalDTO>
}

class AbdUseCaseImpl(private val context: Context, private val abdDataSource: AbdDataSource) :
    AbdUseCase {

    private val serviceKey = context.getString(R.string.service_key)

    // 3개월 동안의 유기견 목록을 불러온다
    override fun getList(
        code: Int?,
        pageNo: Int
    ): Single<RawAbandonAnimalDTO> = abdDataSource.getList(
        serviceKey,
        getDate(Calendar.MONTH, -3),
        getDate(),
        eUpkind.개.code,
        eSido.서울특별시.code,
        code,
        eState.전체.param,
        pageNo,
        15
    )

    private fun getDate(unit: Int? = null, value: Int = 0): String {
        val cal = Calendar.getInstance()
        unit?.let {
            cal.add(unit, value)
        }
        return SDF.yyyymmdd.format(cal.timeInMillis)
    }

}


