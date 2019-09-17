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
import com.example.withpet.util.Log
import com.example.withpet.vo.walk.WalkBaseDTO
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


private const val TYPE = "TYPE"

class WalkSearchFragment : BaseFragment() {
    private val viewModelOwner: WalkFragment by lazy {
        Log.w("1 : ${parentFragment?.javaClass?.simpleName}")
        Log.w("2 : ${parentFragment?.parentFragment?.javaClass?.simpleName}")
        Log.w("3 : ${parentFragment?.parentFragment?.parentFragment?.javaClass?.simpleName}")

        parentFragment?.parentFragment as WalkFragment
    }
    private val viewModel by sharedViewModel<WalkViewModel>(from = { viewModelOwner })

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.walk_search_fragment, container, false)
    }

    lateinit var list: RecyclerView
    val adapter = WalkSearchAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.takeIf { it.containsKey(TYPE) }?.apply {
            when (getSerializable(TYPE) as eWalkType) {
                eWalkType.BICYCLE -> viewModel.searchBicycleList.observe(this@WalkSearchFragment, Observer { list -> list?.let { adapter.set(it as MutableList<WalkBaseDTO>) } })
                eWalkType.PARK -> viewModel.searchParkList.observe(this@WalkSearchFragment, Observer { list -> list?.let { adapter.set(it as MutableList<WalkBaseDTO>) } })
                else -> null
            }
        }

        list = view.findViewById(R.id.list)
        list.adapter = adapter


    }
}

