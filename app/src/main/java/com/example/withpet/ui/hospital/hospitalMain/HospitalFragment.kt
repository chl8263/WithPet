package com.example.withpet.ui.hospital.hospitalMain

import android.annotation.SuppressLint
import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.example.withpet.R
import com.example.withpet.core.BaseFragment
import com.example.withpet.databinding.FragmentHospitalBinding
import com.example.withpet.ui.hospital.callBackListener.OnFragmentBackListener
import com.example.withpet.ui.hospital.hospitalMain.adapter.HospitalHistorySearchRecyclerViewAdapter
import com.example.withpet.ui.hospital.hospitalMain.adapter.HospitalSearchRecyclerViewAdapter
import com.example.withpet.ui.hospital.hospitalDetail.HosDetailFragment
import com.example.withpet.ui.hospital.hospitalMain.adapter.HospitalCardViewRecyclerViewAdapter
import com.example.withpet.ui.hospital.hospitalMain.listener.OnShowHospitalDialogListener
import com.example.withpet.ui.hospital.hospitalMain.listener.SnapPagerScrollListener
import com.example.withpet.ui.main.MainActivity
import com.example.withpet.ui.walk.Location.currentLocation
import com.example.withpet.util.Const.HOSPITAL_DETAIL_DATA
import com.example.withpet.util.Const.MAP_START_LOCATION
import com.example.withpet.util.Const.SHOW_HOSPITAL_CARDVIEW
import com.example.withpet.util.Log
import com.example.withpet.util.afterTextChanged
import com.example.withpet.vo.hospital.HospitalSearchDTO
import com.example.withpet.vo.eventBus.HospitalCardEventVo
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_hospital.*
import kotlinx.android.synthetic.main.fragment_hospital.floatingActionButton
import kotlinx.android.synthetic.main.fragment_hospital.hospitalSearchEdiText
import kotlinx.android.synthetic.main.fragment_hospital.hospitalSearchIcon
import kotlinx.android.synthetic.main.fragment_hospital.hospital_search_layout
import kotlinx.android.synthetic.main.fragment_hospital.view.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


class HospitalFragment : BaseFragment(), OnMapReadyCallback, OnFragmentBackListener, GoogleMap.OnMarkerClickListener , OnShowHospitalDialogListener {

    private lateinit var mapView: MapView
    private lateinit var map: GoogleMap

    private var isSearch = false

    private lateinit var geocoder: Geocoder
    private var markerList : ArrayList<Marker> = ArrayList()

    private var uiType = UiType.TYPE1

    private lateinit var navigation: BottomNavigationView

    private val hospitalAdapter: HospitalSearchRecyclerViewAdapter by inject()
    private val historyAdapter: HospitalHistorySearchRecyclerViewAdapter by inject()
    private val cardViewHospitalAdapter: HospitalCardViewRecyclerViewAdapter by inject()

    lateinit var binding: FragmentHospitalBinding
    val viewModel: HospitalViewModel by viewModel()

    var hos_detail_data: HospitalSearchDTO? = null

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

        geocoder = Geocoder(context, Locale.KOREA)

        // mapView setting
        mapView = binding.root.hospitalMap
        mapView.getMapAsync(this)

        navigation = activity!!.findViewById(R.id.bottomNavigationView)

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

    fun initDataBinding(view: View) {
        viewModel.currentLocation.observe(this, Observer {
            val currentLocation = LatLng(it.latitude, it.longitude)

            map.clear()     // 마커 지우기
            map.addMarker(MarkerOptions().position(MAP_START_LOCATION).icon(BitmapDescriptorFactory.fromBitmap(
                ContextCompat.getDrawable(mActivity, R.drawable.walk_park)?.toBitmap())).title("내위치"))
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 15F))

            var address: MutableList<Address>? = geocoder.getFromLocation(it.latitude, it.longitude, 1)

            address?.let {
                address[0].subLocality?.let {
                    viewModel.getHospitalFromSubLocation(address[0].subLocality)
                }
            }
        })

        viewModel.hospitalList.observe(this, Observer {
            hospitalAdapter.searchList = it
            hospitalAdapter.notifyDataSetChanged()
        })

        viewModel.hospitalSubLocaList.observe(this, Observer {
            if(it.size > 0) {
                mapBottomLayout.visibility = View.VISIBLE
                hospitalCardViewRecyclerView.visibility = View.VISIBLE
                hos_cardView.visibility = View.GONE
                cardViewHospitalAdapter.searchList = it
                cardViewHospitalAdapter.notifyDataSetChanged()
                hospitalCardViewRecyclerView.layoutManager?.scrollToPosition((it.size / 2))
                addLocationMarker(it)
            }
        })

        viewModel.historyList.observe(this, Observer {
            historyAdapter.historyList = it
            historyAdapter.notifyDataSetChanged()
        })

        // 검색입력 reactive 처리
        view.hospitalSearchEdiText.afterTextChanged {
            val value = it.toString()
            if (value.isEmpty()) {

            } else if (value.isNotEmpty() && value.length > 1)
                viewModel.getHospitalSearchData(value)
        }
        // 검색입력 버튼을 누른경우
        view.hospitalSearchEdiText.setOnEditorActionListener { textView, i, keyEvent ->
            if (i == EditorInfo.IME_ACTION_SEARCH) {
                viewModel.getHospitalSearchData(textView.text.toString())
                true
            }
            false
        }
    }

    @SuppressLint("RestrictedApi")
    fun initView(view: View) {

        EventBus.getDefault().register(this)

        // hospital search recyclerView setting
        view.hospitalRecyclerView.adapter = hospitalAdapter
        view.hospitalRecyclerView.layoutManager = LinearLayoutManager(context)

        // hospital history recyclerView setting
        view.hospital_HistoryRecyclerView.adapter = historyAdapter
        view.hospital_HistoryRecyclerView.layoutManager = LinearLayoutManager(context)

        // hospital CardView recyclerView setting
        cardViewHospitalAdapter.mShowHospitalDialogListener = this
        view.hospitalCardViewRecyclerView.adapter = cardViewHospitalAdapter
        view.hospitalCardViewRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        var snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(view.hospitalCardViewRecyclerView)

        // scroll listener 등록
        var snapListener = SnapPagerScrollListener(snapHelper = snapHelper,
            type = SnapPagerScrollListener.Type.ON_SETTLED,
            notifyOnInit = true,
            listener = object : SnapPagerScrollListener.OnChangeListener {
                override fun onSnapped(position: Int) {
                    cardViewHospitalAdapter.searchList?.let {
                        var lat = cardViewHospitalAdapter.searchList[position].latitude
                        var long = cardViewHospitalAdapter.searchList[position].longitude
                        val currentLocation = LatLng(lat!!.toDouble(), long!!.toDouble())
                        moveCarema(currentLocation)
                        markerList.forEach {
                                marker: Marker ->
                            if(((marker.tag as HashMap<String, Any>)["data"] as HospitalSearchDTO).latitude == lat){
                                marker.showInfoWindow()
                                return
                            }
                        }
                    }
                }
            }
        )
        view.hospitalCardViewRecyclerView.addOnScrollListener(snapListener)

        // search icon setting
        view.hospitalSearchIcon.setImageResource(R.drawable.search)
        view.hospitalSearchIcon.setTag(R.drawable.search)

        // search EdiText changed event logic
        view.hospitalSearchEdiText.setOnFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                uiMode_Type2()
            } else Log.e("edit Text focus out")
        }

        view.hospitalSearchIcon.setOnClickListener { view ->
            var tag = hospitalSearchIcon.getTag()
            if (tag == R.drawable.ic_left_arrow) {     // 뒤로가기 버튼일 경우
                when (uiType) {
                    UiType.TYPE2 -> uiMode_Type1()
                    UiType.TYPE3 -> uiMode_Type2()
                    else -> uiMode_Type1()
                }
            }
        }

        view.hos_cardView.setOnClickListener {
            hos_detail_data?.let {
                showHospitalDetailDialog(hos_detail_data!!)
            }
        }
    }

    override fun showHospitalDetailDialog(data: HospitalSearchDTO) {
        val dialog = HosDetailFragment.newInstance()

        var bundle = Bundle()
        bundle.putSerializable(HOSPITAL_DETAIL_DATA, data)
        dialog.arguments = bundle

        dialog.isCancelable = false
        dialog.dialog?.setCanceledOnTouchOutside(false)

        startFragmentDialog(dialog, android.R.transition.slide_bottom)
    }

    @SuppressLint("RestrictedApi")
    fun uiMode_Type1() {
        hospitalSearchEdiText.text = Editable.Factory.getInstance().newEditable("")
        navigation.visibility = View.VISIBLE
        mapView.visibility = View.VISIBLE
        floatingActionButton.visibility = View.VISIBLE
        hospital_search_layout.visibility = View.GONE
        mapBottomLayout.visibility = View.GONE
        hos_cardView.visibility = View.GONE

        hospitalSearchIcon.setImageResource(R.drawable.search)
        hospitalSearchIcon.setTag(R.drawable.search)
        uiType = UiType.TYPE1
    }

    @SuppressLint("RestrictedApi")
    fun uiMode_Type2() {
        navigation.visibility = View.GONE
        mapView.visibility = View.GONE
        floatingActionButton.visibility = View.GONE
        hospital_search_layout.visibility = View.VISIBLE
        mapBottomLayout.visibility = View.GONE
        hospitalSearchIcon.setImageResource(R.drawable.ic_left_arrow)
        hospitalSearchIcon.setTag(R.drawable.ic_left_arrow)

        hospitalAdapter.searchList.clear()
        setHistoryData()
        uiType = UiType.TYPE2
    }

    fun setHistoryData() {
        historyAdapter.historyList.clear()
        viewModel.getHistoryData()
    }

    // hospital List 누를경우
    @SuppressLint("RestrictedApi")
    fun showHospitalDetail(data: HospitalSearchDTO) {

        // hospital detail data 로 넘어가기 위한 data 설정
        hos_detail_data = data

        // ui 설정
        mapBottomLayout.visibility = View.VISIBLE
        hospitalCardViewRecyclerView.visibility = View.GONE
        hos_cardView.visibility = View.VISIBLE
        hos_card_Title.text = data.name
        hos_card_address.text = data.address

        when (data.starAvg.toInt()) {
            1 -> {
                hos_card_star_img_1.setImageResource(R.drawable.ic_star)
                hos_card_star_img_2.setImageResource(R.drawable.ic_empty_star)
                hos_card_star_img_3.setImageResource(R.drawable.ic_empty_star)
                hos_card_star_img_4.setImageResource(R.drawable.ic_empty_star)
                hos_card_star_img_5.setImageResource(R.drawable.ic_empty_star)
            }
            2 -> {
                hos_card_star_img_1.setImageResource(R.drawable.ic_star)
                hos_card_star_img_2.setImageResource(R.drawable.ic_star)
                hos_card_star_img_3.setImageResource(R.drawable.ic_empty_star)
                hos_card_star_img_4.setImageResource(R.drawable.ic_empty_star)
                hos_card_star_img_5.setImageResource(R.drawable.ic_empty_star)
            }
            3 -> {
                hos_card_star_img_1.setImageResource(R.drawable.ic_star)
                hos_card_star_img_2.setImageResource(R.drawable.ic_star)
                hos_card_star_img_3.setImageResource(R.drawable.ic_star)
                hos_card_star_img_4.setImageResource(R.drawable.ic_empty_star)
                hos_card_star_img_5.setImageResource(R.drawable.ic_empty_star)
            }
            4 -> {
                hos_card_star_img_1.setImageResource(R.drawable.ic_star)
                hos_card_star_img_2.setImageResource(R.drawable.ic_star)
                hos_card_star_img_3.setImageResource(R.drawable.ic_star)
                hos_card_star_img_4.setImageResource(R.drawable.ic_star)
                hos_card_star_img_5.setImageResource(R.drawable.ic_empty_star)
            }
            5 -> {
                hos_card_star_img_1.setImageResource(R.drawable.ic_star)
                hos_card_star_img_2.setImageResource(R.drawable.ic_star)
                hos_card_star_img_3.setImageResource(R.drawable.ic_star)
                hos_card_star_img_4.setImageResource(R.drawable.ic_star)
                hos_card_star_img_5.setImageResource(R.drawable.ic_star)
            }
            else -> {
                hos_card_star_img_1.setImageResource(R.drawable.ic_empty_star)
                hos_card_star_img_2.setImageResource(R.drawable.ic_empty_star)
                hos_card_star_img_3.setImageResource(R.drawable.ic_empty_star)
                hos_card_star_img_4.setImageResource(R.drawable.ic_empty_star)
                hos_card_star_img_5.setImageResource(R.drawable.ic_empty_star)
            }
        }

        mapView.visibility = View.VISIBLE
        floatingActionButton.visibility = View.VISIBLE
        hospital_search_layout.visibility = View.GONE
        hospitalSearchEdiText.text = Editable.Factory.getInstance().newEditable(data.name)
        hospitalCardViewRecyclerView.visibility = View.GONE

        // 카메라 이동
        val currentLocation = tranceLoca(data.latitude , data.longitude)

        addLocationMarker(arrayListOf(hos_detail_data!!))
        moveCarema(currentLocation)
        uiType = UiType.TYPE3
    }

    // event bus
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: HospitalCardEventVo) {
        if (event.eventName == SHOW_HOSPITAL_CARDVIEW) {

            showHospitalDetail(event.data)
        }
    }

    override fun onMarkerClick(marker: Marker?): Boolean {
        marker?.let{
            marker.showInfoWindow()
            if(hospitalCardViewRecyclerView.visibility == View.VISIBLE) {
                view!!.hospitalCardViewRecyclerView!!.layoutManager!!.scrollToPosition((marker.tag as HashMap<String, Any>)["index"] as Int)
                viewModel.getcurrentLocation()
            }
        }
        return true
    }

    private fun addLocationMarker(list: ArrayList<HospitalSearchDTO>) {
        map.clear()
        markerList.clear()
        list.forEachIndexed { index,data: HospitalSearchDTO->
            var marker = map.addMarker(MarkerOptions().position(tranceLoca(data.latitude , data.longitude)).title(data.name))
            var hashtable = hashMapOf<String,Any>("data" to data , "index" to index)
            marker.tag = hashtable
            markerList.add(marker)
        }
    }

    private fun tranceLoca(lat : String? , long : String?) : LatLng = LatLng(lat!!.toDouble() , long!!.toDouble())

    private fun moveCarema(latLng: LatLng) {
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14F))
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        googleMap?.let {
            map = googleMap
            map.clear()     // 마커 지우기
            map.addMarker(MarkerOptions().position(MAP_START_LOCATION).icon(BitmapDescriptorFactory.fromBitmap(
                ContextCompat.getDrawable(mActivity, R.drawable.walk_park)?.toBitmap())).title("내위치"))
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(MAP_START_LOCATION, 15F))
        } ?: run {
            Snackbar.make(mapView, "지도 설정 에러입니다.", Snackbar.LENGTH_SHORT).show()
        }

        googleMap?.setOnMarkerClickListener(this)

        // Get my location on startUp
        viewModel.getcurrentLocation()
    }

    // onBackPressed
    override fun onBack() {

        var mainActivity = activity as MainActivity
        //mainActivity.onBackPressed()

        when (uiType) {
            UiType.TYPE1 -> {
                mainActivity.setOnBackPressedByFragment(null)
                mainActivity.onBackPressed()
            }
            UiType.TYPE2 -> {
                uiMode_Type1()
            }
            UiType.TYPE3 -> {
                uiMode_Type2()
            }
        }
    }

    // s : Life cycle

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context as MainActivity).setOnBackPressedByFragment(this)
    }

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

        var mainActivity = activity as MainActivity
        mainActivity.setOnBackPressedByFragment(null)
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }
    // e : Life cycle
}