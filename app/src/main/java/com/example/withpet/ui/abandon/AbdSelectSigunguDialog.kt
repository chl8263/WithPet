package com.example.withpet.ui.abandon

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.withpet.R
import com.example.withpet.databinding.AbdSelectSigunguDlgBinding
import com.example.withpet.ui.abandon.adapter.AbdSigunguAdapter
import com.example.withpet.ui.abandon.enums.SigunguEnum
import com.example.withpet.ui.walk.view.AppBottomSheetDialogFragment
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class AbdSelectSigunguDialog : AppBottomSheetDialogFragment() {

    private val viewModel by sharedViewModel<AbdViewModel>(from = { mActivity })

    private val adapter = AbdSigunguAdapter { enum -> select(enum) }

    private val enumList: ArrayList<SigunguEnum> = SigunguEnum.values().toCollection(ArrayList())

    private lateinit var binding: AbdSelectSigunguDlgBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.abd_select_sigungu_dlg, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.close.setOnClickListener { dismiss() }

        binding.list.adapter = adapter
        binding.list.addItemDecoration(DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL))
        adapter.set(enumList)
    }


    private fun select(enum: SigunguEnum) {
        if (mActivity is AbdListActivity) {
            viewModel.select(enum)
            dismiss()
        }
    }

}