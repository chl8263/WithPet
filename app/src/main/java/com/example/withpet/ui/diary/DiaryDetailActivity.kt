package com.example.withpet.ui.diary

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.PopupMenu
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.withpet.R
import com.example.withpet.core.BaseActivity
import com.example.withpet.databinding.ActivityDiaryDetailBinding
import com.example.withpet.vo.diary.DiaryDTO
import org.koin.androidx.viewmodel.ext.android.viewModel

class DiaryDetailActivity : BaseActivity() {

    lateinit var bb: ActivityDiaryDetailBinding
    private val vm: DiaryDetailViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bb = DataBindingUtil.setContentView(mActivity, R.layout.activity_diary_detail)
        bb.vm = vm
        onParseExtra()
        onLoadOnce()
    }

    private fun onParseExtra() {
        try {
            intent.getSerializableExtra(EXTRA.DIARY_DTO)?.let {
                val dto = it as DiaryDTO
                vm.init(dto)
            }
            intent.getStringExtra(EXTRA.PET_NAME)?.let {
                vm.petName = it
            } ?: showDialog(message = "잘못된 접근 입니다. 다시 확인해주세요.",
                    positiveButtonText = "확인",
                    positiveListener = { _, _ -> finish() })
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun onLoadOnce() {

        vm.showProgress.observe(
                this,
                Observer { it?.let { progress -> if (progress) mActivity.showProgress() else mActivity.dismissProgress() } })
        vm.showOption.observe(this, Observer {
            val popup = PopupMenu(mContext, bb.option).apply {
                menuInflater.inflate(R.menu.diary_detail_menu, menu)
                setOnMenuItemClickListener {
                    when (it.itemId) {
                        R.id.edit -> vm.goEdit()
                        R.id.delete -> vm.delete()
                    }
                    return@setOnMenuItemClickListener false
                }
            }
            popup.show()
        })
        vm.goEdit.observe(this, Observer {
            it?.let { diaryDTO ->
                val diaryEditIntent = Intent(this, DiaryEditActivity::class.java).apply {
                    putExtra(DiaryEditActivity.EXTRA.DIARY_DTO, diaryDTO)
                    putExtra(DiaryEditActivity.EXTRA.PET_NAME, vm.petName)
                }
                startActivityForResult(diaryEditIntent, REQ_DIARY_EDIT)
            }
        })

        vm.deleteSuccess.observe(this, Observer {
            finish()
        })
    }

    override fun finish() {
        setResult(Activity.RESULT_OK)
        super.finish()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQ_DIARY_EDIT -> {
                    data?.let {
                        val dto = it.getSerializableExtra(DiaryEditActivity.RES.DIARY_DTO)
                        dto?.let { serialzable ->
                            val diaryDTO = serialzable as DiaryDTO
                            vm.init(diaryDTO)
                        }
                    }
                }
            }
        }
    }


    interface EXTRA {
        companion object {
            const val DIARY_DTO = "DIARY_DTO"
            const val PET_NAME = "PET_NAME"
        }
    }

    companion object {
        private const val REQ_START = 3000
        const val REQ_DIARY_EDIT = REQ_START
    }
}