package com.example.withpet.vo.walk

import android.os.Parcel
import android.os.Parcelable
import com.google.android.gms.maps.model.LatLng

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
) : Parcelable {

    val location: LatLng
        get() = LatLng((latitude?:"0.0").toDouble(), (longitude?:"0.0").toDouble())

//        {
//        "p_addr":"서울특별시 강동구 천호대로 1291(길동생태공원) ",
//        "guidance":"http://parks.seoul.go.kr/template/common/img/park_info/16_gildong/16_03.jpg",
//        "p_zone":"강동구",
//        "p_name":"동부공원녹지사업소 길동생태공원",
//        "use_refer":"매주 월요일은 휴관입니다\n사전예약 후 입장하실 수 있습니다. ▶ 인터넷 예약 바로가기(클릭하세요)\n공원을 깨끗하게 이용합니다.\n대중교통을 이용해 주세요.\n기념물, 시설물, 풀과 나무를 보호합니다.\n야생동물보호를 위해 음식물 반입을 금지합니다.\n입장시간\n10:00 ~ 16:00 (동절기 11~2월), 10:00~17:00(하절기 3~10월) \n(공원입장예약 안내 : 이용일 31일 09:00 부터  ~ 당일 15시까지 예약 가능 합니다.)",
//        "p_admintel":"02-489-2770",
//        "open_dt":"1999.5.20",
//        "main_equip":"탐방객안내소, 야외전시대, 관찰대, 목재데크, 조류관찰대, 길동생태문화센터",
//        "template_url":"http://parks.seoul.go.kr/template/sub/gildong.do",
//        "g_latitude":"448852.675",
//        "p_park":"길동생태공원",
//        "p_list_content":"길동생태공원은 생물의 서식처를 제공하고 종다양성을 증진시키며 자연생태계의 생물들을 관찰, 체험할 수 있도록 하여 시민들에게 건강한 생태공간을 제공하고 환경의 중요성을 일깨워주기 위한 공간입니다.",
//        "g_longitude":"213554.120",
//        "area":"80,683㎡",
//        "p_img":"http://parks.seoul.go.kr/file/info/view.do?fIdx=1885",
//        "visit_road":null,
//        "main_plants":"소나무, 보리수 등 64종 31,800주 산국, 부들 등 138종 192,800본",
//        "longitude":"127.1547791",
//        "p_idx":3,
//        "latitude":"37.5403935"},

//    {
//    "TEMPLATE_URL":"바로가기",
//    "P_NAME":"관리부서",
//    "AREA":"면적",
//    "GUIDANCE":"안내도",
//    "P_ZONE":"지역",
//    "LATITUDE":"Y좌표(WGS84)",
//    "P_ADMINTEL":"전화번호",
//    "P_LIST_CONTENT":"공원개요",
//    "MAIN_PLANTS":"주요식물",
//    "MAIN_EQUIP":"주요시설",
//    "P_PARK":"공원명",
//    "OPEN_DT":"개원일",
//    "P_IMG":"이미지",
//    "G_LONGITUDE":"X좌표(GRS80TM)",
//    "G_LATITUDE":"Y좌표(GRS80TM)",
//    "LONGITUDE":"X좌표(WGS84)",
//    "P_ADDR":"공원주소",
//    "VISIT_ROAD":"오시는길",
//    "P_IDX":"공원번호",
//    "USE_REFER":"이용시참고사항"},

    constructor() : this(p_addr = "")

    constructor(parcel: Parcel) : this(
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
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readInt(),
            parcel.readString())

    fun extractDetailList(): ArrayList<WalkDetailLowDTO> = arrayListOf(
            WalkDetailLowDTO(Pair("공원주소",p_addr)),              //  "TEMPLATE_URL":"바로가기",
            WalkDetailLowDTO(Pair("안내도",guidance)),                //  "P_NAME":"관리부서",
            WalkDetailLowDTO(Pair("지역",p_zone)),                //  "AREA":"면적",
            WalkDetailLowDTO(Pair("관리부서",p_name)),               //  "GUIDANCE":"안내도",
            WalkDetailLowDTO(Pair("이용시참고사항",use_refer)),             //  "P_ZONE":"지역",
            WalkDetailLowDTO(Pair("전화번호",p_admintel)),               //  "LATITUDE":"Y좌표(WGS84)",
            WalkDetailLowDTO(Pair("개원일",open_dt)),             //  "P_ADMINTEL":"전화번호",
            WalkDetailLowDTO(Pair("주요시설",main_equip)),              //  "P_LIST_CONTENT":"공원개요",
            WalkDetailLowDTO(Pair("바로가기",template_url)),                //  "MAIN_PLANTS":"주요식물",
//            WalkDetailLowDTO(Pair("X좌표",g_latitude)),              //  "MAIN_EQUIP":"주요시설",
            WalkDetailLowDTO(Pair("공원명",p_park)),               //  "P_PARK":"공원명",
            WalkDetailLowDTO(Pair("공원개요",p_list_content)),               //  "OPEN_DT":"개원일",
//            WalkDetailLowDTO(Pair("Y좌표",g_longitude)),              //  "P_IMG":"이미지",
            WalkDetailLowDTO(Pair("면적",area)),             //  "G_LONGITUDE":"X좌표(GRS80TM)",
            WalkDetailLowDTO(Pair("이미지",p_img)),                //  "G_LATITUDE":"Y좌표(GRS80TM)",
            WalkDetailLowDTO(Pair("오시는길",visit_road)),               //  "LONGITUDE":"X좌표(WGS84)",
            WalkDetailLowDTO(Pair("주요식물",main_plants)),             //  "P_ADDR":"공원주소",
            WalkDetailLowDTO(Pair("Y좌표",longitude))                //  "VISIT_ROAD":"오시는길",
    )


    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(template_url)
        parcel.writeString(p_name)
        parcel.writeString(area)
        parcel.writeString(guidance)
        parcel.writeString(p_zone)
        parcel.writeString(latitude)
        parcel.writeString(p_admintel)
        parcel.writeString(p_list_content)
        parcel.writeString(main_plants)
        parcel.writeString(main_equip)
        parcel.writeString(p_park)
        parcel.writeString(open_dt)
        parcel.writeString(p_img)
        parcel.writeString(g_longitude)
        parcel.writeString(g_latitude)
        parcel.writeString(longitude)
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