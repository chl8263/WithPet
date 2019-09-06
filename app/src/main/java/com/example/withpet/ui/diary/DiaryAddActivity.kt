package com.example.withpet.ui.diary

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.withpet.R
import com.example.withpet.core.BaseActivity
import com.example.withpet.databinding.ActivityDiaryAddBinding
import com.example.withpet.util.Gallery
import com.example.withpet.util.Log
import com.sang.permission.permission
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.FileNotFoundException

class DiaryAddActivity : BaseActivity() {

    private val vm: DiaryAddViewModel by viewModel()
    lateinit var bb: ActivityDiaryAddBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bb = DataBindingUtil.setContentView(mActivity, R.layout.activity_diary_add)

        bb.vm = vm
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        Log.i("onPostCreate Call")
        onLoadOnce()
    }

    private fun onLoadOnce() {
        vm.callGallery.observe(mActivity, Observer {
            permission(android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) {
                onGranted = { Gallery.callGallery(mActivity, REQ_GALLERY) }
                onDenied = {
                    showDialog(message = "권한동의를 하지 않으면 사용 하실 수 없습니다.", positiveButtonText = "확인")
                }
            }
        })
    }

    private fun resultGallery(data: Intent) {
        try {
            val imageUri = data.data
            imageUri?.let {
                val imageStream = contentResolver.openInputStream(it)
                val imageBitmap = BitmapFactory.decodeStream(imageStream)
                bb.image.setImageBitmap(imageBitmap)
            }
        } catch (fe: FileNotFoundException) {

        }
    }

    private fun resultCrop(data: Intent) {

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQ_GALLERY -> data?.let { resultGallery(it) }
                REQ_CROP -> data?.let { resultCrop(it) }
            }
        }
    }


    companion object {
        private const val REQ_START = 2000
        private const val REQ_GALLERY = REQ_START
        private const val REQ_CROP = REQ_START + 1
    }
}