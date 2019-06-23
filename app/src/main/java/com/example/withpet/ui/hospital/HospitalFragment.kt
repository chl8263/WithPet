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
import com.example.withpet.ui.hospital.adapter.HospitalHistorySearchRecyclerViewAdapter
import com.example.withpet.ui.hospital.adapter.HospitalSearchRecyclerViewAdapter
import com.example.withpet.ui.hospitalDetail.HosDetailFragment
import com.example.withpet.util.Const.SHOW_HOSPITAL_CARDVIEW
import com.example.withpet.util.Log
import com.example.withpet.util.afterTextChanged
import com.example.withpet.vo.HospitalSearchDTO
import com.example.withpet.vo.eventBus.HospitalCardEventVo
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_hospital.*
import kotlinx.android.synthetic.main.fragment_hospital.view.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel


class HospitalFragment : BaseFragment() ,OnMapReadyCallback{

    private lateinit var mapView : MapView
    private lateinit var map: GoogleMap

    private lateinit var navigation : BottomNavigationView

    private val hospitalAdapter : HospitalSearchRecyclerViewAdapter by inject()
    private val historyAdapter : HospitalHistorySearchRecyclerViewAdapter by inject()

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
            hospitalAdapter.searchList = it
            hospitalAdapter.notifyDataSetChanged()
        })

        viewModel.historyList.observe(this, Observer {
            historyAdapter.historyList = it
            historyAdapter.notifyDataSetChanged()
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

        EventBus.getDefault().register(this)

        // hospital search recyclerView setting
        view.hospitalRecyclerView.adapter = hospitalAdapter
        view.hospitalRecyclerView.layoutManager = LinearLayoutManager(context)

        // hospital history recyclerView setting
        view.hospital_HistoryRecyclerView.adapter = historyAdapter
        view.hospital_HistoryRecyclerView.layoutManager = LinearLayoutManager(context)

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

                hospitalAdapter.searchList.clear()
                setHistoryData()
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
                hos_cardView.visibility = View.GONE
                hospitalSearchIcon.setImageResource(com.example.withpet.R.drawable.search)
                hospitalSearchIcon.setTag(com.example.withpet.R.drawable.search)
            }
        }

        view.hos_cardView.setOnClickListener {
            val dialog = HosDetailFragment.newInstance()
            startFragmentDialog(dialog , android.R.transition.slide_top)
        }
    }

    fun setHistoryData(){
        historyAdapter.historyList.clear()
        viewModel.getHistoryData()
    }

    // hospital List 누를경우
    @SuppressLint("RestrictedApi")
    fun showHospitalDetail(data : HospitalSearchDTO){

        // ui 설정
        hos_cardView.visibility = View.VISIBLE
        hos_card_Title.text = data.name
        hos_card_address.text = data.address
        mapView.visibility = View.VISIBLE
        floatingActionButton.visibility = View.VISIBLE
        hospital_search_layout.visibility = View.GONE
        hospitalSearchEdiText.text = Editable.Factory.getInstance().newEditable(data.name)

        // 카메라 이동
        val currentLocation = LatLng(data.latitude!!.toDouble(), data.longitude!!.toDouble())
        map.addMarker(MarkerOptions().position(currentLocation).title(data.name))
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation,15F))

    }

    //----------------------- event bus ----------------------------
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event : HospitalCardEventVo){
        if(event.eventName == SHOW_HOSPITAL_CARDVIEW){

            showHospitalDetail(event.data)
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
        EventBus.getDefault().unregister(this)
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }
}