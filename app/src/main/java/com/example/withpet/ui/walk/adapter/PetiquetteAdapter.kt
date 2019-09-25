package com.example.withpet.ui.walk.adapter

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.viewpager.widget.PagerAdapter
import com.example.withpet.R
import com.example.withpet.databinding.WalkPetiquetteDlgItemBinding
import com.example.withpet.ui.walk.Location
import com.example.withpet.vo.walk.WalkBaseDTO

class PetiquetteAdapter(@LayoutRes val mLayoutID: Int, val data: WalkBaseDTO, val dismiss: () -> Unit) : PagerAdapter() {
    private var mInflater: LayoutInflater? = null

    private val mItems: List<Pair<Int, Int>> = listOf(Pair(R.string.petiquette_title_1, R.string.petiquette_content_1)
            , Pair(R.string.petiquette_title_2, R.string.petiquette_content_2)
            , Pair(R.string.petiquette_title_3, R.string.petiquette_content_3)
    )

    override fun isViewFromObject(view: View, obj: Any): Boolean = (view == obj)

    override fun getCount(): Int = mItems.size

    private fun getItem(position: Int) = mItems[position]

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val v = inflate(mLayoutID, container)
        val d = getItem(position)

        instantiateItem(v, d)
        container.addView(v)
        return v
    }

    private fun instantiateItem(v: View, d: Pair<Int, Int>) {
        DataBindingUtil.bind<WalkPetiquetteDlgItemBinding>(v)?.apply {
            title = v.context.getString(d.first)
            content = v.context.getString(d.second)
            confirm.setOnClickListener {
                //                 viewModel.getDirection(d._name, d.location)
                openDirection(v.context)
                dismiss.invoke()
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

    private fun openDirection(context: Context) {
        val url = try {
            val packageName = "net.daum.android.map"
            val pm = context.packageManager
            pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES)
            "daummaps://route?sp=${Location.currentLocation!!.latitude},${Location.currentLocation!!.longitude}&ep=${data._latitude},${data._longitude}8&by=FOOT"
        } catch (e: PackageManager.NameNotFoundException) {
            val name = data._name ?: ""
            "https://map.kakao.com/link/to/$name,${data._latitude},${data._longitude}"
        }
        url.takeIf { it.isNotEmpty() }?.apply {
            val urlIntent = Intent(Intent.ACTION_VIEW, Uri.parse(this)).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(urlIntent)
        }
    }

}