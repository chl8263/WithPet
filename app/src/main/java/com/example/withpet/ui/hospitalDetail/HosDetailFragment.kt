package com.example.withpet.ui.hospitalDetail

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.withpet.BaseDialogFragment
import com.example.withpet.databinding.FragmentHosDetailBinding
import com.example.withpet.ui.hospitalComment.HosCommentFragment
import com.example.withpet.util.Const.HOSPITAL_DETAIL_DATA
import com.example.withpet.vo.hospital.HospitalSearchDTO
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.hos_detail_review_comment.view.*
import kotlinx.android.synthetic.main.hospital_detail_fragment.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class HosDetailFragment : BaseDialogFragment() {

    //private val hospitalAdapter: HospitalSearchRecyclerViewAdapter by inject()
    //private val historyAdapter: HospitalHistorySearchRecyclerViewAdapter by inject()

    lateinit var binding: FragmentHosDetailBinding
    val viewModel: HosDetailViewModel by viewModel()

    lateinit var hos_detail_data: HospitalSearchDTO


    companion object {
        fun newInstance(): HosDetailFragment {
            val args = Bundle()
            val fragment = HosDetailFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding =
            DataBindingUtil.inflate(inflater, com.example.withpet.R.layout.hospital_detail_fragment, container, false)
        binding.viewModel = viewModel

        initView(binding.root)

        return binding.root
    }

    private fun initView(view: View) {

        hos_detail_data = arguments?.getSerializable(HOSPITAL_DETAIL_DATA) as HospitalSearchDTO

        // 초기 셋팅
        setTab_1(view)

        view.hos_detail_fragment_tab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(p0: TabLayout.Tab?) {}
            override fun onTabUnselected(p0: TabLayout.Tab?) {}

            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> setTab_1(view)
                    1 -> setTab_2(view)
                }
            }
        })

        view.hospital_review_comment.setOnClickListener {
            val dialog = HosCommentFragment.newInstance()
            var bundle = Bundle()
            bundle.putSerializable(HOSPITAL_DETAIL_DATA,hos_detail_data)
            dialog.arguments = bundle

            dialog.isCancelable = false
            dialog.dialog?.setCanceledOnTouchOutside(false)

            startFragmentDialog(dialog, android.R.transition.slide_right)
        }


        view.hos_detail_fargment_backImg.setOnClickListener {
            dismiss()
        }
    }

    private fun setTab_1(view: View) {

        /*var animation = AlphaAnimation(0,1)
        animation.duration = 1000*/

        view.hos_detail_main.visibility = View.VISIBLE
        //view.hos_detail_main.animation = animation
        view.hos_detail_main_info.visibility = View.VISIBLE
        view.hos_detail_main_review.visibility = View.GONE

        view.hos_detail_Title.text = hos_detail_data.name
        view.hos_detail_info_Title.text =
            "저희 ${hos_detail_data.name} 동물병원은 반려동물과 함께 하기 위한 보호자님들과의 소통과 진료에 항상 힘쓰고 있습니다. 구라에요!"
        view.hos_de1tail_util_address.text = "${hos_detail_data.address}"
        view.hos_de1tail_util_address2.text = "${hos_detail_data.gu} ${hos_detail_data.dong}"
    }

    private fun setTab_2(view: View) {
        /*var animation = AlphaAnimation(0,1)
        animation.duration = 1000*/

        view.hos_detail_main.visibility = View.GONE
        //view.hos_detail_main.animation = animation
        view.hos_detail_main_info.visibility = View.GONE
        view.hos_detail_main_review.visibility = View.VISIBLE
    }


}