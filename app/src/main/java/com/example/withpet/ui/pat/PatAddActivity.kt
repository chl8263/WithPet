package com.example.withpet.ui.pat

import android.Manifest
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.withpet.R
import com.example.withpet.core.BaseActivity
import com.example.withpet.databinding.ActivityPatAddBinding
import com.example.withpet.util.Gallery
import com.sang.permission.permission
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class PatAddActivity : BaseActivity() {

    lateinit var bb: ActivityPatAddBinding
    private val vm: PatAddViewModel by viewModel()


    private val calendar = Calendar.getInstance()
    private val datePicker: DatePickerDialog by lazy {
        DatePickerDialog(mContext, DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            vm.resultBirthDay(year, month, dayOfMonth)
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).apply {
            datePicker.maxDate = calendar.timeInMillis
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bb = DataBindingUtil.setContentView(mActivity, R.layout.activity_pat_add)
        bb.vm = vm
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        onLoadOnce()
    }

    private fun onLoadOnce() {

        vm.callGallery.observe(mActivity, Observer {
            permission(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE) {
                onGranted = { Gallery.callGallery(mActivity, REQ_GALLERY) }
                onDenied = {
                    showDialog(message = "권한동의를 하지 않으면 사용 하실 수 없습니다.", positiveButtonText = "확인")
                }
            }
        })
        vm.callCrop.observe(mActivity, Observer {
            it?.let { imageUri ->
                val cropIntent = Gallery.getCropIntent(application, imageUri, 16, 9)
                startActivityForResult(cropIntent, REQ_CROP)
            }
        })

        vm.showCalendar.observe(mActivity, androidx.lifecycle.Observer {
            if (!datePicker.isShowing) datePicker.show()
        })

        vm.errorMessage.observe(mActivity, Observer { it?.let { errorMessage -> showDialog(message = errorMessage, positiveButtonText = "확인") } })

        vm.showProgress.observe(mActivity, Observer { it?.let { progress -> if (progress) showProgress() else dismissProgress() } })

        vm.insertSuccess.observe(mActivity, Observer {
            it?.let { isSuccess ->
                if (isSuccess) {
                    setResult(Activity.RESULT_OK)
                    finish()
                } else {
                    showDialog(message = "등록에 실패하였습니다.\n다시 시도해주세요.", positiveButtonText = "확인")
                }
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQ_GALLERY -> data?.let { vm.resultGallery(it) }
                REQ_CROP -> data?.let { vm.resultCrop(it) }
            }
        }
    }

    companion object {
        private const val REQ_START = 1500
        private const val REQ_GALLERY = REQ_START
        private const val REQ_CROP = REQ_START + 1
    }
}