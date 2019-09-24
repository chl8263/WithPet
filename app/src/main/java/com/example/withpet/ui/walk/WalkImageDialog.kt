package com.example.withpet.ui.walk

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.withpet.R
import com.example.withpet.databinding.WalkImageDlgBinding
import com.example.withpet.ui.walk.adapter.WalkInfoAdapter
import com.example.withpet.ui.walk.view.FullSizeAppBottomSheetDialogFragment

class WalkImageDialog : FullSizeAppBottomSheetDialogFragment() {

    lateinit var binding: WalkImageDlgBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.walk_image_dlg, container, true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.close.setOnClickListener { dismiss() }

        arguments?.takeIf { it.containsKey(WalkInfoAdapter.DATA) }?.apply {
            binding.data = this.getParcelable(WalkInfoAdapter.DATA)
        }
    }
}