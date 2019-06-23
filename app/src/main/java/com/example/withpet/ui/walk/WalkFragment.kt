package com.example.withpet.ui.walk

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.withpet.R
import com.example.withpet.core.BaseFragment
import com.example.withpet.databinding.FragmentWalkBinding
import com.example.withpet.util.Log
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel

class WalkFragment : BaseFragment(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    lateinit var binding: FragmentWalkBinding
    val viewModel: WalkViewModel by viewModel()

    private lateinit var map: GoogleMap

    companion object {
        fun newInstance(): WalkFragment {
            val args = Bundle()
            val fragment = WalkFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_walk, container, false)
        binding.viewModel = viewModel

        // mapView setting
        binding.map.getMapAsync(this)
        binding.map.onCreate(savedInstanceState)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.currentLocation.observe(this, Observer {
            val currentLocation = LatLng(it.latitude, it.longitude)
            map.addMarker(MarkerOptions().position(currentLocation).title("내위치"))
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 15F))
        })

        viewModel.bicycleList.observe(this, Observer { list ->
            for (data in list) {
                if (data.road_name.trim().isNotEmpty()) {
                    val marker = MarkerOptions().position(data.location).title(data.road_name)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_map_marker))
                        .flat(true)
                    map.addMarker(marker)
                }
            }
        })
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        googleMap?.let {
            map = googleMap
            map.setOnMarkerClickListener(this)
        } ?: run {
            Snackbar.make(binding.map, "지도 설정 에러입니다.", Snackbar.LENGTH_SHORT).show()
        }

        viewModel.getcurrentLocation()

        // 자전거 도로 조회
        viewModel.getBicycleList()
    }

    override fun onMarkerClick(p0: Marker?): Boolean {
        val dlg = WalkInfoDlg()
        val args = Bundle(1)
        args.putString(WalkInfoDlg.ROAD_NAME, p0?.title)
        dlg.arguments = args
        dlg.show(childFragmentManager, "지도정보조회")
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