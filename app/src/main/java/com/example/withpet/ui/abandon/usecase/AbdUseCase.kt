package com.example.withpet.ui.abandon.usecase

import android.content.Context
import com.example.withpet.R
import com.example.withpet.ui.abandon.enums.eSido
import com.example.withpet.ui.abandon.enums.eState
import com.example.withpet.ui.abandon.enums.eUpkind
import com.example.withpet.vo.abandon.RawAbandonAnimalDTO
import io.reactivex.Single

interface AbdUseCase {
    fun getList(
            code: Int?,
            pageNo: Int
    ): Single<RawAbandonAnimalDTO>
}

class AbdUseCaseImpl(private val context: Context, private val abdDataSource: AbdDataSource) :
        AbdUseCase {

    private val serviceKey = context.getString(R.string.service_key)

    override fun getList(
            code: Int?,
            pageNo: Int
    ): Single<RawAbandonAnimalDTO> = abdDataSource.getList(
            serviceKey,
            "20140601",
            "20190601",
            eUpkind.개.code,
            eSido.서울특별시.code,
            code,/*org_cd*/
            eState.전체.param,
            pageNo,
            15
    )

}


