package com.example.withpet.ui.walk

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.withpet.R
import com.example.withpet.core.BaseFragment
import com.example.withpet.databinding.WalkFragmentBinding
import com.example.withpet.ui.walk.enums.eWalkType
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


class WalkFragment : BaseFragment(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    lateinit var binding: WalkFragmentBinding
    val viewModel: WalkViewModel by viewModel()

    private lateinit var map: GoogleMap

    private lateinit var currentLocation: LatLng
    private val infoDialog = WalkInfoDialog()

    private var dataMap: HashMap<String, WalkBaseDTO> = hashMapOf()

    private val pagerAdapter: WalkSearchPagerAdapter by lazy { WalkSearchPagerAdapter(childFragmentManager) }

    companion object {
        fun newInstance(): WalkFragment {
            val args = Bundle()
            val fragment = WalkFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.walk_fragment, container, false)
        binding.viewModel = viewModel

        // 현재위치 확인
        viewModel.getCurrentLocation()

        // mapView setting
        binding.map.getMapAsync(this)
        binding.map.onCreate(savedInstanceState)

        // viewPager setting
        binding.pager.adapter = pagerAdapter
        binding.tabLayout.setupWithViewPager(binding.pager)

        // editText setting
        binding.walkSearch.apply {
            setOnFocusChangeListener { _, hasFocus -> binding.pager.visibility = if (hasFocus) View.VISIBLE else View.GONE }
            setOnEditorActionListener { v, i, _ ->
                if (i == EditorInfo.IME_ACTION_SEARCH) {
                    viewModel.searchList(v.text.toString())
                }
                false
            }
        }

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.currentLocation.observe(this, Observer {
            Log.w("위치 불러오기 성공(latitude = ${it.latitude}, longitude = ${it.longitude})")
            // 마지막 위치 저장
            PP.LAST_LATITUDE.set(it.latitude.toString())
            PP.LAST_LONGITUDE.set(it.longitude.toString())

            currentLocation = LatLng(it.latitude, it.longitude)
            map.addMarker(MarkerOptions().position(currentLocation).title("내위치"))
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 15F))
        })

        viewModel.bicycleList.observe(this, Observer { list ->
            list.forEach { data ->
                data._name?.takeIf { it.trim().isNotEmpty() }?.apply {
                    val marker = map.addMarker(
                            MarkerOptions().position(data.location).title(data._name)
                                    .icon(BitmapDescriptorFactory.fromBitmap(ContextCompat.getDrawable(mActivity, R.drawable.walk_bicycle)?.toBitmap()))
                                    .flat(true)
                    )
                    dataMap[marker.id] = data
                }
            }
        })

        viewModel.parkList.observe(this, Observer { list ->
            list.forEach { data ->
                if (data.p_name.trim().isNotEmpty()) {
                    val marker = map.addMarker(
                            MarkerOptions().position(data.location).title(data.p_name)
                                    .icon(BitmapDescriptorFactory.fromBitmap(ContextCompat.getDrawable(mActivity, R.drawable.walk_park)?.toBitmap()))
                                    .flat(true)
                    )
                    dataMap[marker.id] = data
                }
            }
        })

        viewModel.showAdminMenu.observe(this, Observer {
        })
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        Log.w("map ready")
        googleMap?.let {
            map = googleMap
            map.setOnMarkerClickListener(this)

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

    private fun getLastLocation(): LatLng? {
        val defaultDouble: Double = (-1).toDouble()
        val lastLatitude = PP.LAST_LATITUDE["0"]?.toDouble() ?: defaultDouble
        val lastLongitude = PP.LAST_LONGITUDE["0"]?.toDouble() ?: defaultDouble
        return if (lastLatitude > 0 && lastLongitude > 0) {
            LatLng(lastLatitude, lastLongitude)
        } else null
    }

    override fun onMarkerClick(p0: Marker?): Boolean {
        if (!infoDialog.isAdded) {
            p0?.let { marker ->
                dataMap[marker.id]?.let { data ->
                    val args = Bundle(3)
                    args.putString(WalkInfoDialog.NAME, marker.title)
                    args.putString(WalkInfoDialog.TYPE, eWalkType.BICYCLE.displayName)
                    args.putParcelable(WalkInfoDialog.DATA, data)
                    infoDialog.arguments = args
                    infoDialog.show(childFragmentManager, "정보조회")
                }
            }
        }
        return false
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