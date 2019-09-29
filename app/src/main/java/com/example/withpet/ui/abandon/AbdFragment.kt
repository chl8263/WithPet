package com.example.withpet.ui.abandon

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.withpet.R
import com.example.withpet.core.BaseFragment
import com.example.withpet.databinding.AbdFragmentBinding
import androidx.lifecycle.Observer
import com.example.withpet.ui.common.WebViewActivity
import com.example.withpet.util.Log
import kotlinx.android.synthetic.main.abd_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel


/**
 * todo
 * 1. 서울시 시군구 번호 받아오기 api 추가     완료(enum으로 변경)
 * 2. 선택한 시군구에 있는 유기견 api 추가     완료(enum으로 변경)
 * 3. 유기견 api 페이징 처리 추가
 * 4. 유기견 데이터 리스트로 뿌리기 추가
 * 5. 유기견 상세 페이지 추가
 * 6. 유기견 기사? 정보?? 페이지 추가
 * 7. 유기견 기사? 정보?? 상세 페이지 추가
 */
class AbdFragment : BaseFragment() {

    lateinit var binding: AbdFragmentBinding


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.abd_fragment, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onLoadOnce()
    }

    private fun onLoadOnce() {
        binding.find.setOnClickListener { startActivity(Intent(mContext, AbdListActivity::class.java)) }

        petInform1.setOnClickListener {
            val intent = Intent(context, WebViewActivity::class.java).apply {
                putExtra(WebViewActivity.URL, "http://www.animal.go.kr/portal_rnl/system/about.jsp")
            }
            context?.startActivity(intent)
        }

        petInform2.setOnClickListener {
            val intent = Intent(context, WebViewActivity::class.java).apply {
                putExtra(WebViewActivity.URL, "http://animal.go.kr/portal_rnl/abandonment/public_list.jsp")
            }
            context?.startActivity(intent)
        }

        petInform3.setOnClickListener {
            val intent = Intent(context, WebViewActivity::class.java).apply {
                putExtra(WebViewActivity.URL, "http://animal.go.kr/portal_rnl/vicarious/public_info.jsp")
            }
            context?.startActivity(intent)
        }

        petInform4.setOnClickListener {
            val intent = Intent(context, WebViewActivity::class.java).apply {
                putExtra(WebViewActivity.URL, "http://animal.go.kr/portal_rnl/farm_ani/info.jsp")
            }
            context?.startActivity(intent)
        }
    }

    companion object {
        fun newInstance(): AbdFragment {
            return AbdFragment().apply {
                arguments = Bundle()
            }
        }
    }

    /*val context = view.context
    val intent = Intent(context, WebViewActivity::class.java).apply {
        putExtra(WebViewActivity.URL, url)
    }
    context.startActivity(intent)*/
}