package com.example.withpet.ui.walk

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.example.withpet.R
import com.example.withpet.core.BaseFragment
import com.example.withpet.ui.walk.adapter.WalkSearchAdapter
import com.example.withpet.ui.walk.enums.eWalkType
import com.example.withpet.vo.walk.WalkBaseDTO
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import androidx.recyclerview.widget.DividerItemDecoration


private const val TYPE = "TYPE"

class WalkSearchFragment : BaseFragment() {
    private val walkMainFragment: WalkMainFragment by lazy { parentFragment!!.parentFragment as WalkMainFragment }
    private val viewModel by sharedViewModel<WalkViewModel>(from = { walkMainFragment })

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.walk_search_fragment, container, false)
    }

    lateinit var list: RecyclerView
    val adapter = WalkSearchAdapter { data -> onItemClickListener(data) }

    private fun onItemClickListener(data: WalkBaseDTO) {
        walkMainFragment.clickLocation(data)
        parentFragment?.takeIf { it is WalkSearchDialog }?.apply { (this as WalkSearchDialog).dismiss() }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        initBinding()
        initView()

        list = view.findViewById(R.id.list)
        list.adapter = adapter
        val decoration = DividerItemDecoration(mActivity, DividerItemDecoration.VERTICAL)
        list.addItemDecoration(decoration)

    }

    private fun initBinding() {
        arguments?.takeIf { it.containsKey(TYPE) }?.apply {
            when (getSerializable(TYPE) as eWalkType) {
                eWalkType.BICYCLE -> viewModel.searchBicycleList.observe(this@WalkSearchFragment, Observer { list -> list?.let { adapter.set(it as MutableList<WalkBaseDTO>) } })
                eWalkType.PARK -> viewModel.searchParkList.observe(this@WalkSearchFragment, Observer { list -> list?.let { adapter.set(it as MutableList<WalkBaseDTO>) } })
                else -> null
            }
        }
    }

    private fun initView() {}
}

