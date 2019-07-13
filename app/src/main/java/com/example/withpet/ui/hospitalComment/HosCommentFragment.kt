package com.example.withpet.ui.hospitalComment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.example.withpet.R
import com.example.withpet.util.Const.HOSPITAL_DETAIL_DATA
import com.example.withpet.vo.hospital.HospitalSearchDTO
import kotlinx.android.synthetic.main.hos_comment_fargment.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class HosCommentFragment : DialogFragment() {

    lateinit var binding: com.example.withpet.databinding.FragmentHosCommentBinding
    val viewModel: HosCommentViewModel by viewModel()

    lateinit var hos_detail_data: HospitalSearchDTO

    companion object {
        fun newInstance(): HosCommentFragment {
            val args = Bundle()
            val fragment = HosCommentFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, com.example.withpet.R.layout.hos_comment_fargment, container, false)
        binding.viewModel = viewModel

        initView(binding.root)

        return binding.root
    }

    private fun initView(view : View){

        hos_detail_data = arguments?.getSerializable(HOSPITAL_DETAIL_DATA) as HospitalSearchDTO

        view.comment_title.text = hos_detail_data.name

        view.comment_dismiss.setOnClickListener {
            dismiss()
        }

        view.comment_submit.setOnClickListener {
            // comment 작성 게시 처리
            // star 로직
            // review 목록관리
            //FirebaseFirestore.getInstance().collection(Const.FIRESTORE_COLLECTION_PROFILEIMAGE).document(uid).set(map)
        }

        view.comment_star_01.setOnClickListener {
            view.comment_star_01.setImageResource(R.drawable.ic_star)
            view.comment_star_02.setImageResource(R.drawable.ic_empty_star)
            view.comment_star_03.setImageResource(R.drawable.ic_empty_star)
            view.comment_star_04.setImageResource(R.drawable.ic_empty_star)
            view.comment_star_05.setImageResource(R.drawable.ic_empty_star)
        }

        view.comment_star_02.setOnClickListener {
            view.comment_star_01.setImageResource(R.drawable.ic_star)
            view.comment_star_02.setImageResource(R.drawable.ic_star)
            view.comment_star_03.setImageResource(R.drawable.ic_empty_star)
            view.comment_star_04.setImageResource(R.drawable.ic_empty_star)
            view.comment_star_05.setImageResource(R.drawable.ic_empty_star)
        }

        view.comment_star_03.setOnClickListener {
            view.comment_star_01.setImageResource(R.drawable.ic_star)
            view.comment_star_02.setImageResource(R.drawable.ic_star)
            view.comment_star_03.setImageResource(R.drawable.ic_star)
            view.comment_star_04.setImageResource(R.drawable.ic_empty_star)
            view.comment_star_05.setImageResource(R.drawable.ic_empty_star)
        }

        view.comment_star_04.setOnClickListener {
            view.comment_star_01.setImageResource(R.drawable.ic_star)
            view.comment_star_02.setImageResource(R.drawable.ic_star)
            view.comment_star_03.setImageResource(R.drawable.ic_star)
            view.comment_star_04.setImageResource(R.drawable.ic_star)
            view.comment_star_05.setImageResource(R.drawable.ic_empty_star)
        }

        view.comment_star_05.setOnClickListener {
            view.comment_star_01.setImageResource(R.drawable.ic_star)
            view.comment_star_02.setImageResource(R.drawable.ic_star)
            view.comment_star_03.setImageResource(R.drawable.ic_star)
            view.comment_star_04.setImageResource(R.drawable.ic_star)
            view.comment_star_05.setImageResource(R.drawable.ic_star)
        }

    }


}