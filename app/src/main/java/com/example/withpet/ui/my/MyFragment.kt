package com.example.withpet.ui.my

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.withpet.R
import com.example.withpet.core.BaseFragment
import com.example.withpet.databinding.FragmentMyBinding
import com.example.withpet.ui.diary.DiaryListActivity
import com.example.withpet.ui.my.adapter.MyPetPagerAdapter
import com.example.withpet.ui.pet.PetAddActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class MyFragment : BaseFragment() {

    lateinit var bb: FragmentMyBinding
    private val vm: MyViewModel by viewModel()

    private lateinit var pagerAdapter: MyPetPagerAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        bb = DataBindingUtil.inflate(inflater, R.layout.fragment_my, container, false)
        return bb.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bb.diary.setOnClickListener {
            startActivity(Intent(mActivity, DiaryListActivity::class.java))
        }
        bb.record.setOnClickListener {
            startActivity(Intent(mActivity, PetAddActivity::class.java))
        }
        vm.showProgress.observe(this, Observer { it?.let { progress -> if (progress) mActivity.showProgress() else mActivity.dismissProgress() } })

        vm.fragmentList.observe(this, Observer {
            it?.let { pagerList ->
                pagerAdapter = MyPetPagerAdapter(childFragmentManager, pagerList)
                bb.pager.adapter = pagerAdapter
            }
        })

        vm.getPatList()
    }

}