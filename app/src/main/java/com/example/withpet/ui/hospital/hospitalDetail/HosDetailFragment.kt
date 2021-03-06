package com.example.withpet.ui.hospital.hospitalDetail

import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.withpet.BaseDialogFragment
import com.example.withpet.R
import com.example.withpet.databinding.FragmentHosDetailBinding
import com.example.withpet.ui.hospital.hospitalComment.HosCommentFragment
import com.example.withpet.ui.hospital.hospitalDetail.adapter.HospitalDetailReviewRecyclerViewAdapter
import com.example.withpet.util.Const.HOSPITAL_DETAIL_DATA
import com.example.withpet.util.Log
import com.example.withpet.vo.hospital.HospitalSearchDTO
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.hos_detail_review_comment.view.*
import kotlinx.android.synthetic.main.hos_detail_review_star.view.*
import kotlinx.android.synthetic.main.hos_detail_star_layout.view.*
import kotlinx.android.synthetic.main.hospital_detail_fragment.*
import kotlinx.android.synthetic.main.hospital_detail_fragment.view.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.math.floor

class HosDetailFragment : BaseDialogFragment() , SwipeRefreshLayout.OnRefreshListener {

    private val reviewAdapter: HospitalDetailReviewRecyclerViewAdapter by inject()

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
            DataBindingUtil.inflate(inflater, R.layout.hospital_detail_fragment, container, false)
        binding.viewModel = viewModel

        initView(binding.root)

        // dataBinding setting
        initDataBinding(binding.root)

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

        view.hospitalCall.setOnClickListener {
            startActivity(Intent(Intent.ACTION_DIAL, Uri.parse("tel:02-1111-2222")))
        }

        view.hospital_review_swipeRefreshView.setOnRefreshListener(this)

        // hospital review recyclerView setting
        view.hospital_review_recyclerView.adapter = reviewAdapter
        view.hospital_review_recyclerView.layoutManager = LinearLayoutManager(context)

        hos_detail_data.hospitalUid?.let { viewModel.getHospitalStarData(it) }
    }


    fun initDataBinding(view : View){
        viewModel.reviewList.observe(this, Observer {
            reviewAdapter.reviewList = it
            reviewAdapter.notifyDataSetChanged()

            view.hospital_review_swipeRefreshView.isRefreshing = false
        })

        // starData 를 가져왔을 경우 tab1, tab2 에 데이터를 설정해주어야함.
        viewModel.starData.observe(this, Observer {
            t ->
            t?.let {
                view.hos_detail_tab1_star_avg.text = (floor(t.avg*10)/10).toString()
                view.hos_detail_tab1_star_count.text = "(${t.starTotalCount}개)"
                view.star_review_count_5.text = " ${t.starFive} "
                view.star_review_count_4.text = " ${t.starFour} "
                view.star_review_count_3.text = " ${t.starThree} "
                view.star_review_count_2.text = " ${t.starTwo} "
                view.star_review_count_1.text = " ${t.starOne} "

                view.star_review_tab2_avg.text = (floor(t.avg*10)/10).toString()

                when (t.avg.toInt()){
                    1 -> {
                        view.hos_detail_tab1_star_img_1.setImageResource(R.drawable.ic_star)
                        view.hos_detail_tab1_star_img_2.setImageResource(R.drawable.ic_empty_star)
                        view.hos_detail_tab1_star_img_3.setImageResource(R.drawable.ic_empty_star)
                        view.hos_detail_tab1_star_img_4.setImageResource(R.drawable.ic_empty_star)
                        view.hos_detail_tab1_star_img_5.setImageResource(R.drawable.ic_empty_star)
                    }
                    2 -> {
                        view.hos_detail_tab1_star_img_1.setImageResource(R.drawable.ic_star)
                        view.hos_detail_tab1_star_img_2.setImageResource(R.drawable.ic_star)
                        view.hos_detail_tab1_star_img_3.setImageResource(R.drawable.ic_empty_star)
                        view.hos_detail_tab1_star_img_4.setImageResource(R.drawable.ic_empty_star)
                        view.hos_detail_tab1_star_img_5.setImageResource(R.drawable.ic_empty_star)
                    }
                    3 -> {
                        view.hos_detail_tab1_star_img_1.setImageResource(R.drawable.ic_star)
                        view.hos_detail_tab1_star_img_2.setImageResource(R.drawable.ic_star)
                        view.hos_detail_tab1_star_img_3.setImageResource(R.drawable.ic_star)
                        view.hos_detail_tab1_star_img_4.setImageResource(R.drawable.ic_empty_star)
                        view.hos_detail_tab1_star_img_5.setImageResource(R.drawable.ic_empty_star)
                    }
                    4 -> {
                        view.hos_detail_tab1_star_img_1.setImageResource(R.drawable.ic_star)
                        view.hos_detail_tab1_star_img_2.setImageResource(R.drawable.ic_star)
                        view.hos_detail_tab1_star_img_3.setImageResource(R.drawable.ic_star)
                        view.hos_detail_tab1_star_img_4.setImageResource(R.drawable.ic_star)
                        view.hos_detail_tab1_star_img_5.setImageResource(R.drawable.ic_empty_star)
                    }
                    5 -> {
                        view.hos_detail_tab1_star_img_1.setImageResource(R.drawable.ic_star)
                        view.hos_detail_tab1_star_img_2.setImageResource(R.drawable.ic_star)
                        view.hos_detail_tab1_star_img_3.setImageResource(R.drawable.ic_star)
                        view.hos_detail_tab1_star_img_4.setImageResource(R.drawable.ic_star)
                        view.hos_detail_tab1_star_img_5.setImageResource(R.drawable.ic_star)
                    }
                    else -> {
                        view.hos_detail_tab1_star_img_1.setImageResource(R.drawable.ic_empty_star)
                        view.hos_detail_tab1_star_img_2.setImageResource(R.drawable.ic_empty_star)
                        view.hos_detail_tab1_star_img_3.setImageResource(R.drawable.ic_empty_star)
                        view.hos_detail_tab1_star_img_4.setImageResource(R.drawable.ic_empty_star)
                        view.hos_detail_tab1_star_img_5.setImageResource(R.drawable.ic_empty_star)
                    }
                }

                when ( t.avg.toInt()){
                    1 -> {
                        view.star_review_tab2_right_img1.setImageResource(R.drawable.ic_star)
                        view.star_review_tab2_right_img2.setImageResource(R.drawable.ic_empty_star)
                        view.star_review_tab2_right_img3.setImageResource(R.drawable.ic_empty_star)
                        view.star_review_tab2_right_img4.setImageResource(R.drawable.ic_empty_star)
                        view.star_review_tab2_right_img5.setImageResource(R.drawable.ic_empty_star)
                    }
                    2 -> {
                        view.star_review_tab2_right_img1.setImageResource(R.drawable.ic_star)
                        view.star_review_tab2_right_img2.setImageResource(R.drawable.ic_star)
                        view.star_review_tab2_right_img3.setImageResource(R.drawable.ic_empty_star)
                        view.star_review_tab2_right_img4.setImageResource(R.drawable.ic_empty_star)
                        view.star_review_tab2_right_img5.setImageResource(R.drawable.ic_empty_star)
                    }
                    3 -> {
                        view.star_review_tab2_right_img1.setImageResource(R.drawable.ic_star)
                        view.star_review_tab2_right_img2.setImageResource(R.drawable.ic_star)
                        view.star_review_tab2_right_img3.setImageResource(R.drawable.ic_star)
                        view.star_review_tab2_right_img4.setImageResource(R.drawable.ic_empty_star)
                        view.star_review_tab2_right_img5.setImageResource(R.drawable.ic_empty_star)
                    }
                    4 -> {
                        view.star_review_tab2_right_img1.setImageResource(R.drawable.ic_star)
                        view.star_review_tab2_right_img2.setImageResource(R.drawable.ic_star)
                        view.star_review_tab2_right_img3.setImageResource(R.drawable.ic_star)
                        view.star_review_tab2_right_img4.setImageResource(R.drawable.ic_star)
                        view.star_review_tab2_right_img5.setImageResource(R.drawable.ic_empty_star)
                    }
                    5 -> {
                        view.star_review_tab2_right_img1.setImageResource(R.drawable.ic_star)
                        view.star_review_tab2_right_img2.setImageResource(R.drawable.ic_star)
                        view.star_review_tab2_right_img3.setImageResource(R.drawable.ic_star)
                        view.star_review_tab2_right_img4.setImageResource(R.drawable.ic_star)
                        view.star_review_tab2_right_img5.setImageResource(R.drawable.ic_star)
                    }
                    else -> {
                        view.star_review_tab2_right_img1.setImageResource(R.drawable.ic_empty_star)
                        view.star_review_tab2_right_img2.setImageResource(R.drawable.ic_empty_star)
                        view.star_review_tab2_right_img3.setImageResource(R.drawable.ic_empty_star)
                        view.star_review_tab2_right_img4.setImageResource(R.drawable.ic_empty_star)
                        view.star_review_tab2_right_img5.setImageResource(R.drawable.ic_empty_star)
                    }
                }

                view.star_review_tab2_right_avg_comment.text = "(${t.starTotalCount} 개의 평가)"
            }
        })
    }

    private fun setTab_1(view: View) {

        view.hos_detail_main.visibility = View.VISIBLE
        view.hos_detail_review_title.visibility = View.GONE
        view.hos_detail_main_info.visibility = View.VISIBLE
        view.hos_detail_main_review.visibility = View.GONE

        view.hos_detail_Title.text = hos_detail_data.name
        view.hos_detail_info_Title.text =
            "저희 ${hos_detail_data.name} 동물병원은 반려동물과 함께 하기 위한 보호자님들과의 소통과 진료에 항상 힘쓰고 있습니다. \n" +
                    "동물등록이 가능한 병원으로써 귀하의 소중한 변려동물의 주치의가 되는 동물병원 입니다."
        view.hos_de1tail_util_address.text = "${hos_detail_data.address}"
        view.hos_de1tail_util_address2.text = "${hos_detail_data.gu} ${hos_detail_data.dong}"
    }

    private fun setTab_2(view: View) {
        view.hos_detail_review_title.text = hos_detail_data.name
        view.hos_detail_review_title.visibility = View.VISIBLE
        view.hos_detail_main.visibility = View.GONE
        view.hos_detail_main_info.visibility = View.GONE
        view.hos_detail_main_review.visibility = View.VISIBLE

        viewModel.getHospitalReviewData(hos_detail_data.hospitalUid.toString())
    }

    override fun onRefresh() {
        viewModel.getHospitalReviewData(hos_detail_data.hospitalUid.toString())
    }
}