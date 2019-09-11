package com.example.withpet.ui.pat

import android.app.Application
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.withpet.R
import com.example.withpet.core.BaseViewModel
import com.example.withpet.ui.pat.usecase.ImageUseCase
import com.example.withpet.ui.pat.usecase.PatUseCase
import com.example.withpet.util.LiveEvent
import com.example.withpet.util.Log
import com.example.withpet.util.SDF
import com.example.withpet.vo.pat.PatDTO
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.io.FileNotFoundException
import java.io.InputStream
import kotlin.math.log10

class PatAddViewModel(private val ap: Application,
                      private val patUseCase: PatUseCase,
                      private val imageUseCase: ImageUseCase) : BaseViewModel() {

    val image = ObservableField<InputStream>()           // 사진
    val name = ObservableField<String>()            // 애완견 이름
    val birthDay = ObservableField<String>()        // 출생일
    val genderCheckId = ObservableInt(R.id.male)    // 성별
    val patNum = ObservableField<String>()          // 동물번호

    private val _callGallery = LiveEvent<Any>()     // Gallery 호출
    val callGallery: LiveData<Any>
        get() = _callGallery

    private val _callCrop = MutableLiveData<Uri>()     // Crop 호출
    val callCrop: LiveData<Uri>
        get() = _callCrop

    private val _showCalendar = LiveEvent<Any>()    // Calendar 호출
    val showCalendar: LiveData<Any>
        get() = _showCalendar

    private val _errorMessage = MutableLiveData<String>()   // Error Message
    val errorMessage: LiveData<String>
        get() = _errorMessage


    fun showCalendar() = _showCalendar.call()
    fun callGallery() = _callGallery.call()

    private fun callCrop(imageUri: Uri) = _callCrop.postValue(imageUri)

    fun resultBirthDay(year: Int, month: Int, dayOfMonth: Int) {
        Log.i("year : $year, month : $month, dayOfMonth:$dayOfMonth")
        val plusMonth = month + 1

        val dayLength = (log10(dayOfMonth.toDouble()) + 1).toInt()
        val monthLength = (log10(plusMonth.toDouble()) + 1).toInt()

        val formatDay = if (dayLength == 1) "0$dayOfMonth" else dayOfMonth.toString()
        val formatMonth = if (monthLength == 1) "0$plusMonth" else plusMonth.toString()

        val selectDate = "$year$formatMonth$formatDay"
        val parseSelectDate = SDF.yyyymmdd.parse(selectDate)

        birthDay.set(SDF.yyyymmdd_2.format(parseSelectDate))
    }

    fun resultGallery(data: Intent) {
        try {
            val imageUri = data.data
            imageUri?.let { callCrop(it) }
        } catch (fe: FileNotFoundException) {
            Log.e("resultGallery ErrorMessage : ${fe.message}")
            fe.printStackTrace()
            _errorMessage.postValue("사진첩 호출 중 오류가 발생하였습니다.\n다시 시도하여 주세요.")
        }
    }

    fun resultCrop(data: Intent) {
        Log.i("resultCrop")
        val imageUri = data.data
        imageUri?.let {
            val imageStream = ap.contentResolver.openInputStream(it)
            image.set(imageStream)
        }
    }


    fun validation() {
        val image = image.get()
        val name = name.get()
        val birthDay = birthDay.get()
        val gender = genderCheckId.get()
        val patNum = patNum.get()

        if (image == null) {
            _errorMessage.postValue("이미지를 등록해주세요.")
            return
        }
        if (name.isNullOrEmpty()) {
            _errorMessage.postValue("이름을 등록해주세요.")
            return
        }
        if (birthDay.isNullOrEmpty()) {
            _errorMessage.postValue("생년월일을 등록해주세요.")
            return
        }

        insert()
    }

    private fun insert() {
        val patImage = image.get()

        patImage?.let {
            launch {
                imageUseCase.upload("aaa@gmail.com/pat/child.jpg", it)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({
                            Log.i(it)
                        }, {

                        })
            }
        }


//        launch {
//            patUseCase.insert(PatDTO("test", "test", "910730", 0, "393939"))
//                    .subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe({}, {})
//        }
    }
}