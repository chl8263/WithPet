package com.example.withpet.ui.walk

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.withpet.databinding.WalkSearchFragmentBinding
import com.example.withpet.R
import com.example.withpet.core.BaseFragment
import com.example.withpet.ui.walk.adapter.WalkSearchAdapter
import com.example.withpet.ui.walk.enums.eWalkType
import com.example.withpet.vo.walk.WalkBaseDTO
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import com.google.android.material.snackbar.Snackbar

class WalkSearchFragment : BaseFragment() {
    lateinit var binding: WalkSearchFragmentBinding

    private val walkMainFragment: WalkMainFragment by lazy { parentFragment!!.parentFragment as WalkMainFragment }
    private val viewModel by sharedViewModel<WalkMainViewModel>(from = { walkMainFragment })

    private var mParent: WalkSearchDialog? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.walk_search_fragment, container, false)
        return binding.root
    }

    val adapter = WalkSearchAdapter { data -> onItemClickListener(data) }

    private fun onItemClickListener(data: WalkBaseDTO) {
        walkMainFragment.clickLocation(data)
        mParent?.dismiss()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initBinding()
        initView()
    }

    @Suppress("UNCHECKED_CAST")
    private fun initBinding() {
        arguments?.takeIf { it.containsKey(WalkSearchDialog.TYPE) }?.apply {
            when (getSerializable(WalkSearchDialog.TYPE) as eWalkType) {
                eWalkType.PARK -> viewModel.searchParkList.observe(this@WalkSearchFragment,
                        Observer { list -> setData(eWalkType.PARK, list as MutableList<WalkBaseDTO>) })
                eWalkType.TRAIL -> viewModel.searchTrailList.observe(this@WalkSearchFragment,
                        Observer { list -> setData(eWalkType.TRAIL, list as MutableList<WalkBaseDTO>) })
                else -> {
                    Snackbar.make(binding.root, "다시 시도해주세요~", Snackbar.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun initView() {
        binding.list.adapter = adapter

        parentFragment?.takeIf { it is WalkSearchDialog }?.apply { mParent = this as WalkSearchDialog }
    }

    private fun setData(enum: eWalkType, list: MutableList<WalkBaseDTO>?) {
        list?.let {
            adapter.set(it)
            binding.none.root.visibility = if (it.isEmpty()) View.VISIBLE else View.GONE
            mParent?.presentResult(enum, it.size)
        }
    }

}

