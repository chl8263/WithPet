package com.example.withpet.ui.abandon

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.withpet.R
import com.example.withpet.databinding.AbdDetailDlgBinding
import com.example.withpet.ui.abandon.adapter.AbdDetailAdapter
import com.example.withpet.ui.walk.view.FullSizeAppBottomSheetDialogFragment
import com.example.withpet.vo.abandon.AbandonAnimalDTO
import android.content.Intent
import android.net.Uri
import com.example.withpet.ui.common.ImageDialog


class AbdDetailDialog : FullSizeAppBottomSheetDialogFragment() {

    lateinit var binding: AbdDetailDlgBinding

    private val imageDialog: ImageDialog = ImageDialog()

    val adapter = AbdDetailAdapter()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.abd_detail_dlg, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.list.adapter = adapter
        binding.list.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        binding.close.setOnClickListener { dismiss() }

        arguments?.getParcelable<AbandonAnimalDTO?>(DATA)?.let { data ->
            adapter.set(data.extractDetailList())

//            binding.call.setOnClickListener { startActivity(Intent(Intent.ACTION_DIAL, Uri.parse("tel:${data.careTel}"))) }

            binding.image.setOnClickListener {
                if (!imageDialog.isAdded) {
                    imageDialog.arguments = Bundle(2).apply {
                        putString(ImageDialog.NAME, "유기견 이미지")
                        putString(ImageDialog.URL, data.popfile)
                    }
                    imageDialog.show(childFragmentManager, "이미지보기")
                }
            }
            binding.data = data
        }

    }

    companion object {
        const val DATA = "DATA"
    }
}