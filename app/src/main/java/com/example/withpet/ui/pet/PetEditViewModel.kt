package com.example.withpet.ui.pet

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
import com.example.withpet.ui.pet.usecase.ImageUseCase
import com.example.withpet.ui.pet.usecase.PetUseCase
import com.example.withpet.util.*
import com.example.withpet.vo.hospital.HospitalSearchDTO
import com.example.withpet.vo.pet.PetDTO
import java.io.*
import kotlin.math.log10


class PetEditViewModel(private val petUseCase: PetUseCase,
                       private val imageUseCase: ImageUseCase) : BaseViewModel() {

    val image = ObservableField<Bitmap>()      // 사진
    val name = ObservableField<String>()            // 애완견 이름
    val birthDay = ObservableField<String>()        // 출생일
    val genderCheckId = ObservableInt(R.id.male)    // 성별
    val petNum = ObservableField<String>()          // 동물번호

    private val _callGallery = LiveEvent<Any>()     // Gallery 호출
    val callGallery: LiveData<Any>
        get() = _callGallery

    private val _callCrop = MutableLiveData<Uri>()     // Crop 호출
    val callCrop: LiveData<Uri>
        get() = _callCrop

    private val _showCalendar = LiveEvent<Any>()    // Calendar 호출
    val showCalendar: LiveData<Any>
        get() = _showCalendar

    private val _showHospital = LiveEvent<Any>()    // hospital 호출
    val showHospital: LiveData<Any>
        get() = _showHospital

    private val _errorMessage = MutableLiveData<String>()   // Error Message
    val errorMessage: LiveData<String>
        get() = _errorMessage

    private val _showProgress = MutableLiveData<Boolean>()   // ShowProgress
    val showProgress: LiveData<Boolean>
        get() = _showProgress

    private val _insertSuccess = MutableLiveData<PetDTO>()     // Gallery 호출
    val insertSuccess: LiveData<PetDTO>
        get() = _insertSuccess

    private val _infoMessage = MutableLiveData<String>()   // Error Message
    val infoMessage: LiveData<String>
        get() = _infoMessage


    fun init(petDTO: PetDTO) {
        name.set(petDTO.name)
        birthDay.set(petDTO.birthDay)
        val parse_gender = if (petDTO.gender == 0) R.id.male else R.id.female
        genderCheckId.set(parse_gender)
        petNum.set(petDTO.petNum)

        launch {
            imageUseCase.getBitmapFromUrl(petDTO.imageUrl)
                    .with()
                    .progress(_showProgress)
                    .subscribe({ image.set(it) }, { _errorMessage.postValue(it.message) })
        }
    }


    fun showCalendar() = _showCalendar.call()
    fun callGallery() = _callGallery.call()
    fun callHospital() = _showHospital.call()

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
            val imagePath = imageUseCase.getRealPath(it)
            imagePath?.let { path ->
                val stream = FileInputStream(File(path))
                val imageBitmap = BitmapFactory.decodeStream(stream)
                image.set(imageBitmap)
            }
        }
    }


    fun validation() {
        val image = image.get()
        val name = name.get()
        val birthDay = birthDay.get()
        val petNum = petNum.get()

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

        if (petNum != null && petNum.isNotEmpty() && petNum.length != 15) {
            _errorMessage.postValue("동물등록번호 15자리를 입력 해 주세요.")
            return
        }

        if (petNum.isNullOrEmpty()) {
            _infoMessage.postValue("")
        }

        imageUpload()
    }


    private fun imageUpload() {

        val email = Auth.getEmail()
        val isEmailNotNull = !email.isNullOrEmpty()

        if (isEmailNotNull) {
            val storagePath = "$email/pet/${name.get()}_${birthDay.get()}_${System.currentTimeMillis()}.jpg"
            val imageBitmap = image.get()
            imageBitmap?.let {
                try {
                    val baos = ByteArrayOutputStream()
                    it.compress(Bitmap.CompressFormat.JPEG, 100, baos)
                    val byteArray = baos.toByteArray()
                    val stream = ByteArrayInputStream(byteArray)
                    launch {
                        imageUseCase.upload(storagePath, stream)
                                .with()
                                .progress(_showProgress)
                                .subscribe({ downloadUrl -> edit(downloadUrl) }, { exception ->
                                    Log.e("upload Error : ${exception.message}")
                                    exception.printStackTrace()
                                    _errorMessage.postValue(exception.message)
                                })
                    }
                } catch (fe: FileNotFoundException) {
                    fe.printStackTrace()
                }
            } ?: _errorMessage.postValue("이미지가 없습니다.")
        } else {
            _errorMessage.postValue("로그인이 필요합니다.")
        }
    }

    private fun edit(downloadUrl: String) {

        val name = name.get()
        val birthDay = birthDay.get()
        val gender = genderCheckId.get()
        val petNum = petNum.get()

        val parse_gender = if (gender == R.id.male) 0 else 1

        Log.i("gender : ${gender == R.id.male}")

        if (name != null && birthDay != null) {
            launch {
                val petData = PetDTO(downloadUrl,
                        name,
                        birthDay,
                        parse_gender,
                        petNum,
                        HospitalSearchDTO())

                petUseCase.edit(petData)
                        .with()
                        .progress(_showProgress)
                        .subscribe({
                            if (it) _insertSuccess.postValue(petData)
                            else _insertSuccess.postValue(null)
                        }, { _errorMessage.postValue(it.message) })
            }
        }
    }
}