package com.example.withpet.ui.my

import android.app.Activity
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
import com.example.withpet.ui.my.adapter.MyPagerAdapter
import com.example.withpet.ui.pet.PetAddActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class MyFragment : BaseFragment() {

    lateinit var bb: FragmentMyBinding
    private val vm: MyViewModel by viewModel()

    private lateinit var pagerAdapter: MyPagerAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        bb = DataBindingUtil.inflate(inflater, R.layout.fragment_my, container, false)
        return bb.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bb.vm = vm
        vm.showProgress.observe(this, Observer { it?.let { progress -> if (progress) mActivity.showProgress() else mActivity.dismissProgress() } })
        vm.fragmentList.observe(this, Observer {
            it?.let { pagerList ->
                pagerAdapter = MyPagerAdapter(childFragmentManager, pagerList)
                bb.pager.adapter = pagerAdapter
            }
        })
        vm.callAddPet.observe(mActivity, Observer {
            val addPetIntent = Intent(mActivity, PetAddActivity::class.java)
            startActivityForResult(addPetIntent, REQ_ADD_PET)
        })
        vm.errorMessage.observe(mActivity, Observer { errorMessage -> showDialog(message = errorMessage, positiveButtonText = "확인") })
        vm.getPetList()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQ_ADD_PET -> vm.getPetList()
            }
        }
    }

    companion object {
        private const val REQ_START = 150
        const val REQ_ADD_PET = REQ_START
    }
}