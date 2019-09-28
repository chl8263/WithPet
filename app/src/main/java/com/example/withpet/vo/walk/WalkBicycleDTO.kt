package com.example.withpet.vo.walk

import android.os.Parcel
import android.os.Parcelable
import com.example.withpet.ui.walk.enums.eWalkType
import com.example.withpet.util.Formatter
import com.google.gson.annotations.SerializedName

data class WalkBicycleDTOList(
        @SerializedName("DATA") private val data: ArrayList<WalkBicycleDTO>
) {
    lateinit var bicycleDTOList: ArrayList<WalkBicycleDTO>

    fun parseData(): WalkBicycleDTOList {
        val removeList: MutableList<WalkBicycleDTO> = mutableListOf()
        val roadNameList: MutableSet<String> = mutableSetOf()
        bicycleDTOList = data.apply {
            forEach {
                val name = it.road_name.trim()
                when {
                    name.isEmpty() -> removeList.add(it)
                    roadNameList.contains(name) -> removeList.add(it)
                    else -> roadNameList.add(name)
                }
            }
            removeAll(removeList)
            sortBy { it.objectid }
        }
        return this
    }
}

data class WalkBicycleDTO(
        val altitude: Int = 0,                                              // 고도값
        val ed_dir: Int = 0,                                                // 끝링크 각도
        val ed_nd_id: Double = 0.0,                                         // 끝노드 ID
        val lane: Double = 0.0,                                             // 차선수
        val lat: String? = "",  // 위도
        val length: Double = 0.0,                                           // 길이(m)
        val link_cate: Double = 0.0,                                        // 링크 종별
        val link_cate2: Int = 0,                                            // 주종코드
        val link_id: Double = 0.0,                                          // 링크 ID
        val lng: String? = "", // 경도
        val objectid: Int = 0,                                              // 고유번호
        val oneway: Double = 0.0,                                           // 일방통행여부
        val road_cate: Double = 0.0,                                        // 도로종별코드
        val road_name: String = "", // 도로명
        val road_no: String = "",                                           // 도로번호
        val st_dir: Int = 0,                                                // 시작링크 각도
        val st_nd_id: Double = 0.0,                                         // 시작노드 ID
        val track: Double = 0.0                                             // 자전거도로
) : WalkBaseDTO(
        type = eWalkType.BICYCLE
) {

    override val _name get() = road_name
    override val _latitude get() = lat
    override val _longitude get() = lng

    constructor() : this(road_name = "")

    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readInt(),
            parcel.readDouble(),
            parcel.readDouble(),
            parcel.readString(),
            parcel.readDouble(),
            parcel.readDouble(),
            parcel.readInt(),
            parcel.readDouble(),
            parcel.readString(),
            parcel.readInt(),
            parcel.readDouble(),
            parcel.readDouble(),
            parcel.readString(),
            parcel.readString(),
            parcel.readInt(),
            parcel.readDouble(),
            parcel.readDouble()
    )

    override fun extractDetailList(): ArrayList<WalkDetailRowDTO> = arrayListOf(
//            WalkDetailRowDTO(Pair("고도값", Formatter.f(altitude))),
//            WalkDetailRowDTO(Pair("끝링크 각도", Formatter.f(ed_dir))),
//            WalkDetailRowDTO(Pair("끝노드 ID", Formatter.f(ed_nd_id))),
            WalkDetailRowDTO(Pair("차선수", Formatter.f(lane, suffix = "차선"))),
//            WalkDetailRowDTO(Pair("위도", _latitude)),
            WalkDetailRowDTO(Pair("길이(km)", Formatter.f(length, suffix = "km"))),
//            WalkDetailRowDTO(Pair("링크 종별", "$link_cate")),
//            WalkDetailRowDTO(Pair("주종코드", "$link_cate2")),
//            WalkDetailRowDTO(Pair("링크 ID", "$link_id")),
//            WalkDetailRowDTO(Pair("경도", _longitude)),
//            WalkDetailRowDTO(Pair("고유번호", "$objectid")),
//            WalkDetailRowDTO(Pair("일방통행여부", "$oneway")),
//            WalkDetailRowDTO(Pair("도로종별코드", "$road_cate")),
            WalkDetailRowDTO(Pair("도로명", _name))
//            WalkDetailRowDTO(Pair("도로번호", road_no)),
//            WalkDetailRowDTO(Pair("시작링크 각도", "$st_dir")),
//            WalkDetailRowDTO(Pair("시작노드 ID", "$st_nd_id")),
//            WalkDetailRowDTO(Pair("자전거도로", Formatter.f(track)))
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(altitude)
        parcel.writeInt(ed_dir)
        parcel.writeDouble(ed_nd_id)
        parcel.writeDouble(lane)
        parcel.writeString(_latitude)
        parcel.writeDouble(length)
        parcel.writeDouble(link_cate)
        parcel.writeInt(link_cate2)
        parcel.writeDouble(link_id)
        parcel.writeString(_longitude)
        parcel.writeDouble(oneway)
        parcel.writeDouble(road_cate)
        parcel.writeString(_name)
        parcel.writeString(road_no)
        parcel.writeInt(st_dir)
        parcel.writeDouble(st_nd_id)
        parcel.writeDouble(track)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun toString(): String {
        return "WalkBicycleDTO(고유번호=$objectid, 도로명='$_name', 고도값=$altitude, 끝링크=$ed_dir, 끝노드=$ed_nd_id, 차선수=$lane, 위도='$_latitude', 길이=$length, 링크=$link_cate, 주종코드=$link_cate2, " +
                "링크=$link_id, 경도='$_longitude', 일방통행여부=$oneway, 도로종별코드=$road_cate, 도로번호='$road_no', 시작링크=$st_dir, 시작노드=$st_nd_id, 자전거도로=$track)\n"
    }

    companion object CREATOR : Parcelable.Creator<WalkBicycleDTO> {
        override fun createFromParcel(parcel: Parcel): WalkBicycleDTO {
            return WalkBicycleDTO(parcel)
        }

        override fun newArray(size: Int): Array<WalkBicycleDTO?> {
            return arrayOfNulls(size)
        }
    }
}
