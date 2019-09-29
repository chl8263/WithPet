package com.example.withpet.ui.walk

import android.animation.*
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.viewpager.widget.ViewPager
import com.example.withpet.R
import com.example.withpet.core.BaseFragment
import com.example.withpet.databinding.WalkFragmentBinding
import com.example.withpet.ui.walk.adapter.WalkInfoAdapter
import com.example.withpet.ui.walk.listener.SimpleOnPageChangeListener
import com.example.withpet.util.Const
import com.example.withpet.util.Log
import com.example.withpet.util.PP
import com.example.withpet.vo.walk.WalkBaseDTO
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import org.koin.androidx.viewmodel.ext.android.viewModel

object Location {
    var currentLocation : LatLng? = null
}

class WalkMainFragment : BaseFragment(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener, GoogleMap.OnMapClickListener {

    // data
    val viewModel: WalkMainViewModel by viewModel()
    lateinit var currentLocation: LatLng

    // data for map
    private var dataMap: HashMap<String, WalkBaseDTO> = hashMapOf()

    // data for pager
    private var dataList: ArrayList<WalkBaseDTO> = arrayListOf()
    private var loadFinishCount = 0

    // adapter
    private val adapter: WalkInfoAdapter
            by lazy { WalkInfoAdapter(R.layout.walk_info_item, childFragmentManager) }

    // view
    lateinit var binding: WalkFragmentBinding
    private lateinit var map: GoogleMap

    // animation
    private val pagerHeight by lazy { binding.pager.height.toFloat() }

    private val pagerAnimatorSet: AnimatorSet by lazy {
        AnimatorSet().apply {
            duration = 225L
            playTogether(pagerAnimationList)
        }
    }

    private val pagerAnimationList by lazy {
        listOf(
                ObjectAnimator.ofFloat(binding.pager, "translationY", pagerHeight, 0f).apply {
                    interpolator = AccelerateInterpolator()
                },
                ObjectAnimator.ofFloat(binding.floatingButton, "translationY", 0f, -pagerHeight).apply {
                    interpolator = AccelerateInterpolator()
                }
        )
    }


    companion object {
        fun newInstance(): WalkMainFragment {
            return WalkMainFragment().apply {
                arguments = Bundle()
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.walk_fragment, container, false)
        binding.viewModel = viewModel

        // 현재위치 확인
        viewModel.getCurrentLocation()

        // 지도 초기화
        binding.map.getMapAsync(this)
        binding.map.onCreate(savedInstanceState)

        binding.pager.adapter = adapter

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // editText setting
        binding.walkSearch.setOnClickListener {
            dismissPager()
            val dlg = WalkSearchDialog()
            dlg.arguments = Bundle().apply { putString(WalkSearchDialog.KEYWORD, (it as TextView).text.toString()) }
            dlg.show(childFragmentManager, "산책로 검색")
        }

        binding.pager.addOnPageChangeListener(SimpleOnPageChangeListener().apply {
            onPageScrollStateChanged = { state ->
                if (state == ViewPager.SCROLL_STATE_IDLE) {
                    val currentItem = adapter.getItem(binding.pager.currentItem)
                    map.animateCamera(CameraUpdateFactory.newLatLngZoom(currentItem.location, 15F))
                }
            }
        })

        observeLiveData()
    }

    private fun observeLiveData() {
        viewModel.curLocation.observe(this, Observer {
            Log.w("위치 불러오기 성공(latitude = ${it.latitude}, longitude = ${it.longitude})")

            currentLocation = LatLng(it.latitude, it.longitude)
            Location.currentLocation = currentLocation
            adapter.setCurrentLocation(currentLocation)

            map.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 15F))
        })

        viewModel.parkList.observe(this, Observer { list ->
            list.forEach { data -> addData(data) }
            dataList.addAll(list)
            if (++loadFinishCount == 2) adapter.set(dataList)
        })

        viewModel.trailList.observe(this, Observer { list ->
            list.forEach { data -> addData(data) }
            dataList.addAll(list)
            if (++loadFinishCount == 2) adapter.set(dataList)
        })

        viewModel.showAdminMenu.observe(this, Observer { })

        viewModel.showProgress.observe(this, Observer { it?.let { progress -> if (progress) mActivity.showProgress() else mActivity.dismissProgress() } })
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        Log.w("map ready")
        googleMap?.let {
            map = googleMap.apply {
                setOnMarkerClickListener(this@WalkMainFragment)
                setOnMapClickListener(this@WalkMainFragment)
                moveCamera(CameraUpdateFactory.newLatLngZoom(Const.MAP_START_LOCATION, 15F))
            }
        }

        // 공원 조회
        viewModel.getParkList()

        // 산책로 조회
        viewModel.getTrailList()
    }

    private fun addData(data: WalkBaseDTO) {
        data._name?.takeIf { it.trim().isNotEmpty() }?.apply {
            val marker = map.addMarker(
                    MarkerOptions().position(data.location).title(data._name)
                            .icon(BitmapDescriptorFactory.fromBitmap(ContextCompat.getDrawable(mActivity, data.type.icon)?.toBitmap()))
                            .flat(true)
            )
            dataMap[marker.id] = data
        }
    }

    override fun onMarkerClick(p0: Marker?): Boolean {
        p0?.let { marker ->
            dataMap[marker.id]?.let { d ->
                binding.pager.setCurrentItem(dataList.indexOf(d), false)
                showPager()
            }
        }
        return false
    }

    override fun onMapClick(p0: LatLng?) {
        dismissPager()
    }

    fun clickLocation(data: WalkBaseDTO) {
        binding.walkSearch.text = data._name
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(data.location, 15F))
        binding.pager.setCurrentItem(dataList.indexOf(data), false)
        showPager()
    }

    // animation
    private fun showPager() {
        Log.w("showPager")
        if (binding.pager.translationY >= pagerHeight) {
            pagerAnimatorSet.start()
        }
    }

    private fun dismissPager() {
        Log.w("dismissPager")
        if (binding.pager.translationY == 0f) {
            pagerAnimationList.forEach { animation -> animation.reverse() }
        }
    }

    override fun onStart() {
        super.onStart()
        binding.map.onStart()
    }

    override fun onStop() {
        super.onStop()
        binding.map.onStop()
    }

    override fun onResume() {
        super.onResume()
        binding.map.onResume()
    }

    override fun onPause() {
        super.onPause()
        binding.map.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.map.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        binding.map.onLowMemory()
    }
}