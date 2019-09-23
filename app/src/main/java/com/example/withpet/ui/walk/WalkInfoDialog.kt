package com.example.withpet.ui.walk

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.withpet.R
import com.example.withpet.databinding.WalkInfoDlgBinding
import com.example.withpet.ui.walk.view.TransparentBottomSheetDialogFragment
import com.example.withpet.vo.walk.WalkBaseDTO
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class WalkInfoDialog : TransparentBottomSheetDialogFragment() {

    lateinit var binding: WalkInfoDlgBinding

    private val mParent: WalkMainFragment by lazy { parentFragment as WalkMainFragment }

    private val viewModel by sharedViewModel<WalkViewModel>(from = { mParent })

    private val detailDialog = WalkInfoDetailDialog()

    private var data: WalkBaseDTO? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.walk_info_dlg, container, true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.takeIf { it.containsKey(DATA) }?.apply {
            data = getParcelable(DATA)
            binding.data = data
        } ?: dismiss()

        binding.distanceValue = arguments?.getString(DISTANCE)

        binding.detail.setOnClickListener {
            if (!detailDialog.isAdded) {
                detailDialog.arguments = Bundle(1).apply {
                    putParcelable(DATA, arguments?.getParcelable(DATA))
                }
                detailDialog.show(childFragmentManager, "정보상세조회")
            }
        }

        binding.direction.setOnClickListener {
             data?.let { d -> viewModel.getDirection(d._name, d.location) }
        }
    }

    companion object {
        const val DISTANCE = "DISTANCE"
        const val DATA = "DATA"
    }
}