package com.example.withpet.ui.hospital

import android.annotation.SuppressLint
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide.init
import com.example.withpet.R
import com.example.withpet.core.BaseFragment
import com.example.withpet.databinding.FragmentHospitalBinding
import com.example.withpet.util.Log
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_maps_test.*
import kotlinx.android.synthetic.main.fragment_hospital.*
import kotlinx.android.synthetic.main.fragment_hospital.view.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class HospitalFragment : BaseFragment() ,OnMapReadyCallback{

    private lateinit var mapView : MapView
    private lateinit var map: GoogleMap

    private lateinit var navigation : BottomNavigationView

    private val adapter : HospitalSearchRecyclerViewAdapter by inject()

    lateinit var binding: FragmentHospitalBinding
    val viewModel: HospitalViewModel by viewModel()

    companion object {
        fun newInstance(): HospitalFragment {
            val args = Bundle()
            val fragment = HospitalFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_hospital, container, false)
        binding.viewModel = viewModel

        // mapView setting
        mapView = binding.root.hospitalMap
        mapView.getMapAsync(this)

        navigation = activity!!.findViewById(R.id.bottomNavigationView)

        // dataBinding setting
        initDataBinding()

        // initView Setting
        initView(binding.root)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mapView?.let { mapView.onCreate(savedInstanceState) }
    }

    fun initDataBinding(){
        viewModel.currentLocation.observe(this, Observer {
            val currentLocation = LatLng(it.latitude, it.longitude)
            map.addMarker(MarkerOptions().position(currentLocation).title("내위치"))
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLocation,15F))
        })
    }

    @SuppressLint("RestrictedApi")
    fun initView(view : View){

        view.hospitalRecyclerView.adapter = adapter
        view.hospitalRecyclerView.layoutManager = LinearLayoutManager(context)

        // search icon setting
        view.hospitalSearchIcon.setImageResource(R.drawable.search)
        view.hospitalSearchIcon.setTag(R.drawable.search)

        // search EdiText changed event logic
        view.hospitalSearchEdiText.setOnFocusChangeListener { view, hasFocus ->
            if (hasFocus) {

                // bottom navigataion
                navigation.visibility = View.GONE

                // mapView
                mapView.visibility = View.GONE

                // floatong button
                floatingActionButton.visibility = View.GONE

                // recyclerView
                hospitalRecyclerView.visibility = View.VISIBLE

                // search icon change
                hospitalSearchIcon.setImageResource(R.drawable.ic_left_arrow)
                hospitalSearchIcon.setTag(R.drawable.ic_left_arrow)

            }
            else  Log.e("false")
        }

        view.hospitalSearchIcon.setOnClickListener {view ->
            var tag = hospitalSearchIcon.getTag()
            if(tag == R.drawable.ic_left_arrow)
                Log.e("sex")
            else
                Log.e("sex2")

        }
    }

    //----------------------- map ----------------------------------
    override fun onMapReady(googleMap: GoogleMap?) {
        googleMap?.let {
            map = googleMap
        }?: run {
            Snackbar.make(mapView,"지도 설정 에러입니다.", Snackbar.LENGTH_SHORT).show()
        }

        // Get my location on startUp
        viewModel.getcurrentLocation()
    }
    //----------------------- Life cycle ----------------------------------

    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }

    override fun onStop() {
        super.onStop()
        mapView.onStop()
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }
}