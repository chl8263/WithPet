package com.example.withpet.ui.walk

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.databinding.DataBindingUtil
import com.example.withpet.R
import com.example.withpet.databinding.WalkPetiquetteDlgBinding
import com.example.withpet.ui.walk.adapter.PetiquetteAdapter
import com.example.withpet.ui.walk.adapter.WalkInfoAdapter
import com.example.withpet.ui.walk.view.TransparentFullSizeBottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar

class WalkPetiquetteDialog : TransparentFullSizeBottomSheetDialogFragment() {

    lateinit var binding: WalkPetiquetteDlgBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.walk_petiquette_dlg, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.takeIf { it.containsKey(WalkInfoAdapter.DATA) }?.apply {
            binding.pager.adapter = PetiquetteAdapter(R.layout.walk_petiquette_dlg_item, getParcelable(WalkInfoAdapter.DATA)) {dismiss()}

        } ?:run{
            Snackbar.make(binding.root, "다시 시도해주세요~", Snackbar.LENGTH_SHORT).show()
            dismiss()
        }

    }
}