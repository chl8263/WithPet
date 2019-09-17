package com.example.withpet.ui.walk

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.example.withpet.R
import com.example.withpet.core.BaseFragment
import com.example.withpet.ui.walk.adapter.WalkSearchAdapter
import com.example.withpet.ui.walk.enums.eWalkType
import com.example.withpet.vo.walk.WalkBaseDTO
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


private const val ARG_OBJECT = "object"

class WalkSearchFragment : BaseFragment() {
    private val viewModel by sharedViewModel<WalkViewModel>(from = { parentFragment as WalkFragment })

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.walk_search_fragment, container, false)
    }

    lateinit var list: RecyclerView
    val adapter = WalkSearchAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        list = view.findViewById(R.id.list)
        list.adapter = adapter

        viewModel.searchedList.observe(this, Observer { list -> list?.let { adapter.set(it as MutableList<WalkBaseDTO>) } })
    }
}

class WalkSearchPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    override fun getCount(): Int = 2

    override fun getItem(i: Int): WalkSearchFragment {
        val fragment = WalkSearchFragment()
        fragment.arguments = Bundle().apply {
            // Our object is just an integer :-P
            putInt(ARG_OBJECT, i + 1)
        }
        return fragment
    }

    override fun getPageTitle(position: Int): CharSequence {
        return when (position) {
            0 -> eWalkType.BICYCLE.displayName
            1 -> eWalkType.PARK.displayName
            else -> ""
        }
    }
}