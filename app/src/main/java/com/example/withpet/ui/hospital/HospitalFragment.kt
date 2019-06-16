package com.example.withpet.ui.hospital

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.withpet.core.BaseFragment
import com.example.withpet.databinding.FragmentHospitalBinding
import com.example.withpet.ui.hospital.adapter.HospitalSearchRecyclerViewAdapter
import com.example.withpet.util.Const
import com.example.withpet.util.Log
import com.example.withpet.util.afterTextChanged
import com.example.withpet.vo.HospitalSearchDTO
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
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
        binding = DataBindingUtil.inflate(inflater, com.example.withpet.R.layout.fragment_hospital, container, false)
        binding.viewModel = viewModel

        // mapView setting
        mapView = binding.root.hospitalMap
        mapView.getMapAsync(this)

        navigation = activity!!.findViewById(com.example.withpet.R.id.bottomNavigationView)

        // dataBinding setting
        initDataBinding(binding.root)

        // initView Setting
        initView(binding.root)

        return binding.root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mapView?.let { mapView.onCreate(savedInstanceState) }
    }

    fun initDataBinding(view : View){
        viewModel.currentLocation.observe(this, Observer {
            val currentLocation = LatLng(it.latitude, it.longitude)
            map.addMarker(MarkerOptions().position(currentLocation).title("내위치"))
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLocation,15F))
        })

        viewModel.hospitalList.observe(this, Observer {
            adapter.searchList = it
            adapter.notifyDataSetChanged()
        })

        // 검색입력을 하는 경우
        view.hospitalSearchEdiText.afterTextChanged {
            val value = it.toString()
            if(value.isEmpty()){

            } else if (value.isNotEmpty() && value.length > 1)
                viewModel.getHospitalSearchData(value)

        }

    }

    @SuppressLint("RestrictedApi")
    fun initView(view : View){

        view.hospitalRecyclerView.adapter = adapter
        view.hospitalRecyclerView.layoutManager = LinearLayoutManager(context)

        // search icon setting
        view.hospitalSearchIcon.setImageResource(com.example.withpet.R.drawable.search)
        view.hospitalSearchIcon.setTag(com.example.withpet.R.drawable.search)

        // search EdiText changed event logic
        view.hospitalSearchEdiText.setOnFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                navigation.visibility = View.GONE
                mapView.visibility = View.GONE
                floatingActionButton.visibility = View.GONE
                hospital_search_layout.visibility = View.VISIBLE
                hospitalSearchIcon.setImageResource(com.example.withpet.R.drawable.ic_left_arrow)
                hospitalSearchIcon.setTag(com.example.withpet.R.drawable.ic_left_arrow)

                adapter.searchList.clear()
            }
            else  Log.e("edit Test focus out")
        }

        view.hospitalSearchIcon.setOnClickListener {view ->
            var tag = hospitalSearchIcon.getTag()
            if(tag == com.example.withpet.R.drawable.ic_left_arrow) {     // 뒤로가기 버튼일 경우
                hospitalSearchEdiText.text = Editable.Factory.getInstance().newEditable("")
                navigation.visibility = View.VISIBLE
                mapView.visibility = View.VISIBLE
                floatingActionButton.visibility = View.VISIBLE
                hospital_search_layout.visibility = View.GONE
                hospitalSearchIcon.setImageResource(com.example.withpet.R.drawable.search)
                hospitalSearchIcon.setTag(com.example.withpet.R.drawable.search)
            }
            else
                Log.e("2")

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