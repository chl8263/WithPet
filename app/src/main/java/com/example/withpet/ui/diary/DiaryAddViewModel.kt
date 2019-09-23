package com.example.withpet.ui.diary

import android.app.Application
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.withpet.core.BaseViewModel
import com.example.withpet.ui.diary.usecase.DiaryUseCase
import com.example.withpet.ui.pet.usecase.ImageUseCase
import com.example.withpet.util.*
import com.example.withpet.vo.diary.DiaryDTO
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.InputStream
import kotlin.math.log10

class DiaryAddViewModel(private val ap: Application,
                        private val diaryUseCase: DiaryUseCase,
                        private val imageUseCase: ImageUseCase) : BaseViewModel() {


    val title = ObservableField<String>()           // 제목
    val content = ObservableField<String>()         // 내용
    val date = ObservableField<String>()            // 날짜
    val image = ObservableField<InputStream>()      // 사진

    private val _callCrop = MutableLiveData<Uri>()     // Crop 호출
    val callCrop: LiveData<Uri>
        get() = _callCrop

    private val _callGallery = LiveEvent<Any>()     // Gallery 호출
    val callGallery: LiveData<Any>
        get() = _callGallery

    private val _showCalendar = LiveEvent<Any>()    // Calendar 호출
    val showCalendar: LiveData<Any>
        get() = _showCalendar

    private val _errorMessage = MutableLiveData<String>()   // Error Message
    val errorMessage: LiveData<String>
        get() = _errorMessage

    private val _showProgress = MutableLiveData<Boolean>()   // Error Message
    val showProgress: LiveData<Boolean>
        get() = _showProgress

    private var imageRealPath: String? = null
    lateinit var petName: String


    fun gallery() = _callGallery.call()
    fun calendar() = _showCalendar.call()
    private fun callCrop(imageUri: Uri) = _callCrop.postValue(imageUri)


    fun resultCalendar(year: Int, month: Int, dayOfMonth: Int) {
        Log.i("year : $year, month : $month, dayOfMonth:$dayOfMonth")
        val plusMonth = month + 1

        val dayLength = (log10(dayOfMonth.toDouble()) + 1).toInt()
        val monthLength = (log10(plusMonth.toDouble()) + 1).toInt()

        val formatDay = if (dayLength == 1) "0$dayOfMonth" else dayOfMonth.toString()
        val formatMonth = if (monthLength == 1) "0$plusMonth" else plusMonth.toString()

        val selectDate = "$year$formatMonth$formatDay"
        val parseSelectDate = SDF.yyyymmdd.parse(selectDate)

        date.set(SDF.yyyymmdd_2.format(parseSelectDate))
    }

    fun validation() {
        val image = image.get()
        val getTitle = title.get()
        val getContent = content.get()
        val getDate = date.get()

        if (image == null) {
            _errorMessage.postValue("이미지를 넣어주세요.")
        }

        if (getTitle.isNullOrEmpty()) {
            _errorMessage.postValue("제목을 입력 해 주세요.")
            return
        }

        if (getContent.isNullOrEmpty()) {
            _errorMessage.postValue("내용을 입력 해 주세요.")
            return
        }

        if (getDate.isNullOrEmpty()) {
            _errorMessage.postValue("날짜를 입력 해 주세요.")
            return
        }

        imageUpload()
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
            imageRealPath = Gallery.getRealPathFromURI(ap, it)
            imageRealPath?.let { path ->
                val stream = FileInputStream(File(path))
                image.set(stream)
            }
        }
    }


    private fun imageUpload() {
        val email = Auth.getEmail()
        val isEmailNotNull = !email.isNullOrEmpty()
        if (isEmailNotNull) {
            val storagePath = "$email/diary/$petName/${title.get()}_${date.get()}_${System.currentTimeMillis()}.jpg"
            imageRealPath?.let {
                try {
                    val stream = FileInputStream(File(it))
                    launch {
                        imageUseCase.upload(storagePath, stream)
                                .with()
                                .progress(_showProgress)
                                .subscribe({ downloadUrl -> insert(downloadUrl) },
                                        { exception ->
                                            exception.printStackTrace()
                                            _errorMessage.postValue(exception.message)
                                        })
                    }
                } catch (fe: FileNotFoundException) {
                    fe.printStackTrace()
                    _errorMessage.postValue(fe.message)
                }
            }
        } else {
            _errorMessage.postValue("로그인이 필요합니다.")
        }
    }

    private fun insert(downloadUrl: String) {

        val title = title.get()
        val content = content.get()
        val date = date.get()

        if (title != null && content != null && date != null) {
            val diaryDTO = DiaryDTO(title, content, downloadUrl, date)
            launch {
                diaryUseCase.insert(diaryDTO)
                        .with()
                        .progress(_showProgress)
                        .subscribe({}, {})
            }
        }
    }
}