package com.example.withpet.ui.walk

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.TimeInterpolator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewPropertyAnimator
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
import com.google.android.material.animation.AnimationUtils
import org.koin.androidx.viewmodel.ext.android.viewModel

/** todo
 * 1. 마커 클릭 시 나오는 BottomSheetDialog -> pager 교체 (완료)
 * 2. pager animation 수동으로 생성                       (완료)
 * 3. 검색 결과도 pager로 나오도록 수정
 * 4. 검색 default 화면 추가
 * 5. animateCamera 끊김현상
 * 6. 내위치 이동 floating 추가
 * 7. image click 시 확대되서 보이도록 bottomsheetdialog 추가
 */
class WalkMainFragment : BaseFragment(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener, GoogleMap.OnMapClickListener {

    // data
    val viewModel: WalkMainViewModel by viewModel()
    lateinit var currentLocation: LatLng

    // data for map
    private var prevPagerVisible: Boolean = false
    private var dataMap: HashMap<String, WalkBaseDTO> = hashMapOf()
    private var markerMap: HashMap<String, Marker> = hashMapOf()

    // data for pager
    private var dataList: ArrayList<WalkBaseDTO> = arrayListOf()
    private var loadFinishCount = 0

    // adapter
    private val adapter: WalkInfoAdapter
            by lazy { WalkInfoAdapter(R.layout.walk_info_item, childFragmentManager) }

    // view
    lateinit var binding: WalkFragmentBinding
    private lateinit var map: GoogleMap

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
        binding.walkSearch.setOnClickListener { WalkSearchDialog().show(childFragmentManager, "산책로 검색") }

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
        viewModel.currentLocation.observe(this, Observer {
            Log.w("위치 불러오기 성공(latitude = ${it.latitude}, longitude = ${it.longitude})")
            // 마지막 위치 저장
            PP.LAST_LATITUDE.set(it.latitude.toString())
            PP.LAST_LONGITUDE.set(it.longitude.toString())

            currentLocation = LatLng(it.latitude, it.longitude)
            adapter.setCurrentLocation(currentLocation)

            map.addMarker(MarkerOptions().position(currentLocation).title("내위치"))
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 15F))
        })

        viewModel.bicycleList.observe(this, Observer { list ->
            list.forEach { data -> addData(data) }
            dataList.addAll(list)
            if (++loadFinishCount == 2) adapter.set(dataList)
        })

        viewModel.parkList.observe(this, Observer { list ->
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
            }

            // 현재 위치 불러오기 전일 경우에는 마지막으로 검색된 위치로 이동
            if (!::currentLocation.isInitialized) {
                Log.w("현재 위치 불러오기 전일 경우에는 마지막으로 검색된 위치로 이동")
                getLastLocation()?.let { lastLocation -> map.moveCamera(CameraUpdateFactory.newLatLngZoom(lastLocation, 15F)) }
            }
        }

        // 자전거 도로 조회
        viewModel.getBicycleList()

        // 공원 조회
        viewModel.getParkList()
    }

    private fun addData(data: WalkBaseDTO) {
        data._name?.takeIf { it.trim().isNotEmpty() }?.apply {
            val marker = map.addMarker(
                    MarkerOptions().position(data.location).title(data._name)
                            .icon(BitmapDescriptorFactory.fromBitmap(ContextCompat.getDrawable(mActivity, data.type.icon)?.toBitmap()))
                            .flat(true)
            )
            dataMap[marker.id] = data
            markerMap[marker.id] = marker
        }
    }

    private fun getLastLocation(): LatLng? {
        val defaultDouble: Double = (-1).toDouble()
        val lastLatitude = PP.LAST_LATITUDE["0"]?.toDouble() ?: defaultDouble
        val lastLongitude = PP.LAST_LONGITUDE["0"]?.toDouble() ?: defaultDouble
        return if (lastLatitude > 0 && lastLongitude > 0) {
            LatLng(lastLatitude, lastLongitude)
        } else null
    }

    override fun onMarkerClick(p0: Marker?): Boolean {
        p0?.let { marker ->
            dataMap[marker.id]?.let { d ->
                binding.pager.currentItem = dataList.indexOf(d)
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
    }

    var currentAnimator: ViewPropertyAnimator? = null

    // animation
    private fun showPager() {
        Log.w("showPager")
        animate(true, binding.pager, 0, 225L, AnimationUtils.LINEAR_OUT_SLOW_IN_INTERPOLATOR)
    }

    private fun dismissPager() {
        Log.w("dismissPager")
        animate(false, binding.pager, binding.pager.height, 175L, AnimationUtils.FAST_OUT_LINEAR_IN_INTERPOLATOR)
    }

    private fun animate(isPagerVisible: Boolean, child: View, targetY: Int, duration: Long, interpolator: TimeInterpolator) {
        // 중복 애니메이션 막기 위한 플래그 추가
        if (prevPagerVisible != isPagerVisible) {
            prevPagerVisible = isPagerVisible
            currentAnimator?.let {
                it.cancel()
                binding.pager.clearAnimation()
            }
            currentAnimator = child.animate()
                    .translationY(targetY.toFloat())
                    .setInterpolator(interpolator)
                    .setDuration(duration)
                    .setListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator) {
                            currentAnimator = null
                        }
                    })
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