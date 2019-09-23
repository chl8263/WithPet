package com.example.withpet.ui.walk.adapter

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import androidx.viewpager.widget.PagerAdapter
import com.example.withpet.databinding.WalkInfoItemBinding
import com.example.withpet.ui.walk.WalkInfoDetailDialog
import com.example.withpet.util.DistanceUtil
import com.example.withpet.vo.walk.WalkBaseDTO
import com.google.android.gms.maps.model.LatLng


class WalkInfoAdapter(@LayoutRes val mLayoutID: Int, val fm: FragmentManager) : PagerAdapter() {

    private var currentLocation: LatLng? = null

    private var mInflater: LayoutInflater? = null

    private val mItems = arrayListOf<WalkBaseDTO>()

    private val mDistanceMap = hashMapOf<WalkBaseDTO, String>()

    private val detailDialog: WalkInfoDetailDialog = WalkInfoDetailDialog()

    fun set(items: ArrayList<WalkBaseDTO>) {
        mItems.clear()
        mItems.addAll(items)
        notifyDataSetChanged()
    }

    fun setCurrentLocation(location : LatLng){
        currentLocation = location

        mDistanceMap.clear()
        mItems.forEach {
            mDistanceMap[it] = DistanceUtil.getDistance(currentLocation, it.location, DistanceUtil.eDistanceUnit.kilometer)
        }

        notifyDataSetChanged()
    }

    override fun isViewFromObject(view: View, obj: Any): Boolean = (view == obj)

    override fun getCount(): Int = mItems.size

    fun getItem(position: Int) = mItems[position]

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val v = inflate(mLayoutID, container)
        val d = getItem(position)

        instantiateItem(v, d)
        container.addView(v)
        return v
    }

    private fun instantiateItem(v: View, d: WalkBaseDTO) {
        DataBindingUtil.bind<WalkInfoItemBinding>(v)?.apply {
            data = d

            distanceValue = mDistanceMap[d]

            detail.setOnClickListener {
                if (!detailDialog.isAdded) {
                    detailDialog.arguments = Bundle(1).apply {
                        putParcelable(DATA, d)
                    }
                    detailDialog.show(fm, "정보상세조회")
                }
            }

            // todo pager item에 viewModel을 어떻게 넣어야할까
            direction.setOnClickListener {
                data?.let { d ->
                    // viewModel.getDirection(d._name, d.location)
                    val url = "https://map.kakao.com/link/to/${d._name ?: ""},${d._latitude},${d._longitude}"
                    val urlIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url)).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    v.context.startActivity(urlIntent)
                }
            }
        }
    }

    private fun inflate(@LayoutRes layer: Int, parent: ViewGroup): View {
        if (mInflater == null)
            mInflater = LayoutInflater.from(parent.context)
        return mInflater!!.inflate(layer, parent, false)
    }

    override fun destroyItem(container: ViewGroup, position: Int, obj: Any) {
        container.removeView(obj as View)
    }

    companion object {
        const val DATA = "DATA"
    }
}