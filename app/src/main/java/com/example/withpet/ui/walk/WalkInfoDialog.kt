package com.example.withpet.ui.walk

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.withpet.R
import com.example.withpet.databinding.WalkInfoDlgBinding
import com.example.withpet.ui.walk.view.TransparentBottomSheetDialogFragment
import com.example.withpet.vo.walk.WalkBicycleDTO

@Suppress("ClassName")
class WalkInfoDialog : TransparentBottomSheetDialogFragment() {

    lateinit var binding: WalkInfoDlgBinding

    private val title by lazy { arguments?.getString(ROAD_NAME) }
    private val type by lazy { arguments?.getString(TYPE) }
    private val data by lazy { arguments?.getParcelable(DATA) as WalkBicycleDTO? }
    private val detailDialog by lazy {
        WalkInfoDetailDialog().apply {
            val args = Bundle(1)
            args.putParcelable(DATA, data)
            arguments = args
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.walk_info_dlg, container, true)
        binding.title = title
        binding.type = type
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.detail.setOnClickListener {
            if (!detailDialog.isAdded) detailDialog.show(childFragmentManager, "정보상세조회")
        }
    }

    companion object {
        const val ROAD_NAME = "ROAD_NAME"
        const val TYPE = "TYPE"
        const val DATA = "DATA"
    }

    enum class eWalkType(val displayName: String) {
        BICYCLE("자전거도로");
    }
}