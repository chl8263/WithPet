package com.example.withpet.ui.abandon.usecase

import android.content.Context
import com.example.withpet.R
import com.example.withpet.util.Auth
import com.example.withpet.util.Log
import com.example.withpet.vo.diary.DiaryDTO
import com.google.common.base.Utf8
import com.google.firebase.firestore.FirebaseFirestore
import io.reactivex.Single
import retrofit2.Call
import java.net.URLEncoder

interface AbdUseCase {
    fun getSido(): Call<String>
}

class AbdUseCaseImpl(private val context: Context, private val abdDataSource: AbdDataSource) : AbdUseCase {

    override fun getSido(): Call<String> = abdDataSource.getSido(context.getString(R.string.service_key))
}
