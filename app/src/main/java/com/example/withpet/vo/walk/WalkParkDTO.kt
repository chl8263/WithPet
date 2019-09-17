package com.example.withpet.vo.walk

import android.os.Parcel
import android.os.Parcelable
import com.example.withpet.ui.walk.enums.eWalkType
import com.google.gson.annotations.SerializedName

data class WalkParkDTO(
        val p_addr: String = "",
        val guidance: String? = "",
        val p_zone: String? = "",
        val p_name: String = "",
        val use_refer: String? = "",
        val p_admintel: String? = "",
        val open_dt: String? = "",
        val main_equip: String? = "",
        val template_url: String? = "",
        val g_latitude: String? = "",
        val p_park: String? = "",
        val p_list_content: String? = "",
        val g_longitude: String? = "",
        val area: String? = "",
        val p_img: String? = "",
        val visit_road: String? = "",
        val main_plants: String? = "",
        val longitude: String? = "",
        val p_idx: Int? = 0,
        val latitude: String? = ""
) : WalkBaseDTO(type = eWalkType.PARK) {

    override val _name get() = p_park
    override val _latitude get() = latitude
    override val _longitude get() = longitude

    constructor() : this(p_addr = "")

    constructor(parcel: Parcel) : this(
            parcel.readString() ?: "",
            parcel.readString(),
            parcel.readString(),
            parcel.readString() ?: "",
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readInt(),
            parcel.readString())

    override fun extractDetailList(): ArrayList<WalkDetailRowDTO> = arrayListOf(
            WalkDetailRowDTO(Pair("공원주소", p_addr)),              //  "TEMPLATE_URL":"바로가기",
            WalkDetailRowDTO(Pair("안내도", guidance)),                //  "P_NAME":"관리부서",
            WalkDetailRowDTO(Pair("지역", p_zone)),                //  "AREA":"면적",
            WalkDetailRowDTO(Pair("관리부서", p_name)),               //  "GUIDANCE":"안내도",
            WalkDetailRowDTO(Pair("이용시참고사항", use_refer)),             //  "P_ZONE":"지역",
            WalkDetailRowDTO(Pair("전화번호", p_admintel)),               //  "LATITUDE":"Y좌표(WGS84)",
            WalkDetailRowDTO(Pair("개원일", open_dt)),             //  "P_ADMINTEL":"전화번호",
            WalkDetailRowDTO(Pair("주요시설", main_equip)),              //  "P_LIST_CONTENT":"공원개요",
            WalkDetailRowDTO(Pair("바로가기", template_url)),                //  "MAIN_PLANTS":"주요식물",
//            WalkDetailRowDTO(Pair("X좌표",g_latitude)),              //  "MAIN_EQUIP":"주요시설",
            WalkDetailRowDTO(Pair("공원명", _name)),               //  "P_PARK":"공원명",
            WalkDetailRowDTO(Pair("공원개요", p_list_content)),               //  "OPEN_DT":"개원일",
//            WalkDetailRowDTO(Pair("Y좌표",g_longitude)),              //  "P_IMG":"이미지",
            WalkDetailRowDTO(Pair("면적", area)),             //  "G_LONGITUDE":"X좌표(GRS80TM)",
            WalkDetailRowDTO(Pair("이미지", p_img)),                //  "G_LATITUDE":"Y좌표(GRS80TM)",
            WalkDetailRowDTO(Pair("오시는길", visit_road)),               //  "LONGITUDE":"X좌표(WGS84)",
            WalkDetailRowDTO(Pair("주요식물", main_plants))             //  "P_ADDR":"공원주소",
//            WalkDetailRowDTO(Pair("Y좌표",longitude))                //  "VISIT_ROAD":"오시는길",
    )


    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(template_url)
        parcel.writeString(p_name)
        parcel.writeString(area)
        parcel.writeString(guidance)
        parcel.writeString(p_zone)
        parcel.writeString(_latitude)
        parcel.writeString(p_admintel)
        parcel.writeString(p_list_content)
        parcel.writeString(main_plants)
        parcel.writeString(main_equip)
        parcel.writeString(_name)
        parcel.writeString(open_dt)
        parcel.writeString(p_img)
        parcel.writeString(g_longitude)
        parcel.writeString(g_latitude)
        parcel.writeString(_longitude)
        parcel.writeString(p_addr)
        parcel.writeString(visit_road)
        parcel.writeInt(p_idx ?: 0)
        parcel.writeString(use_refer)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<WalkParkDTO> {
        override fun createFromParcel(parcel: Parcel): WalkParkDTO {
            return WalkParkDTO(parcel)
        }

        override fun newArray(size: Int): Array<WalkParkDTO?> {
            return arrayOfNulls(size)
        }
    }
}