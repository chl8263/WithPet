package com.example.withpet.ui.walk

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.withpet.R
import com.example.withpet.databinding.WalkSearchDlgBinding
import com.example.withpet.ui.walk.enums.eWalkType
import com.example.withpet.ui.walk.view.FullSizeAppBottomSheetDialogFragment
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

@Suppress("ClassName")

class WalkSearchDialog : FullSizeAppBottomSheetDialogFragment() {

    lateinit var binding: WalkSearchDlgBinding
    private val viewModel by sharedViewModel<WalkMainViewModel>(from = { parentFragment as WalkMainFragment })

    lateinit var list: RecyclerView
    private val pagerAdapter: WalkSearchPagerAdapter by lazy { WalkSearchPagerAdapter(childFragmentManager) }

    private val result: HashMap<eWalkType, Int> = hashMapOf()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.walk_search_dlg, container, false)
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // viewPager setting
        binding.pager.adapter = pagerAdapter
        binding.tabLayout.setupWithViewPager(binding.pager)

        binding.walkSearch.setOnEditorActionListener { v, i, _ ->
            if (i == EditorInfo.IME_ACTION_SEARCH) {
                viewModel.searchList(v.text.toString())
            }
            false
        }

        initView()
    }

    private fun initView() {
        arguments?.takeIf { it.containsKey(KEYWORD) }?.apply {
            binding.keyword = this.getString(KEYWORD)
        }
    }

    fun presentResult(enum: eWalkType, count: Int) {
        result[enum] = count
        var resultText = ""
        var totalCount = 0

        val it = result.iterator()
        while (it.hasNext()) {
            val data  = it.next()
            totalCount += data.value
           resultText+= "${data.key.displayName} ${data.value}개,"
        }
        resultText = "총 $totalCount 건의 검색결과가 있습니다.(${resultText.substring(0, resultText.length-1)})"
        binding.result.text = resultText
    }

    companion object {
        const val KEYWORD = "KEYWORD"
        const val TYPE = "TYPE"
    }
}


class WalkSearchPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    override fun getCount(): Int = 2

    override fun getItem(i: Int): WalkSearchFragment {
        val fragment = WalkSearchFragment()
        fragment.arguments = Bundle().apply {
            putSerializable(WalkSearchDialog.TYPE, eWalkType.getEnumByIndex(i))
        }
        return fragment
    }

    override fun getPageTitle(position: Int): CharSequence {
        return eWalkType.getEnumByIndex(position).displayName
    }
}