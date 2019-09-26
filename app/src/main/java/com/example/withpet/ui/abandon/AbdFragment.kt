package com.example.withpet.ui.abandon

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.withpet.R
import com.example.withpet.core.BaseFragment
import com.example.withpet.databinding.AbdFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * todo
 * 1. 서울시 시군구 번호 받아오기 api 추가
 * 2. 선택한 시군구에 있는 유기견 api 추가
 * 3. 유기견 api 페이징 처리 추가
 * 4. 유기견 데이터 리스트로 뿌리기 추가
 * 5. 유기견 상세 페이지 추가
 * 6. 유기견 기사? 정보?? 페이지 추가
 * 7.  유기견 기사? 정보?? 상세 페이지 추가
 */
class AbdFragment : BaseFragment() {

    lateinit var binding: AbdFragmentBinding
    val viewModel: AbdViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.abd_fragment, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeLiveData()
    }

    private fun observeLiveData() {
        viewModel.getSido()
    }

    companion object {
        fun newInstance(): AbdFragment {
            return AbdFragment().apply {
                arguments = Bundle()
            }
        }
    }
}