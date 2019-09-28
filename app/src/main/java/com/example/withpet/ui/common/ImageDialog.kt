package com.example.withpet.ui.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.withpet.R
import com.example.withpet.databinding.CommonImageDlgBinding
import com.example.withpet.ui.walk.view.FullSizeAppBottomSheetDialogFragment

class ImageDialog : FullSizeAppBottomSheetDialogFragment() {

    lateinit var binding: CommonImageDlgBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.common_image_dlg, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.close.setOnClickListener { dismiss() }

        arguments?.let {
            binding.name = it.getString(NAME)
            binding.url = it.getString(URL)
        }

    }

    companion object {
        const val NAME = "NAME"
        const val URL = "URL"
    }
}