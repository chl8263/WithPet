package com.example.withpet.vo

import com.google.gson.annotations.SerializedName

data class WalkBicycleDTOList(
    @SerializedName("DATA") val bicycleDTOList: ArrayList<WalkBicycleDTO>

) {
    override fun toString(): String {
        return "자전거도로 임시 데이터 목록============================================================\n" +
                "$bicycleDTOList"
    }
}

data class WalkBicycleDTO(
    val altitude: Int,                                          // 고도값
    val ed_dir: Int,                                            // 끝링크 각도
    val ed_nd_id: Double,                                       // 끝노드 ID
    val lane: Double,                                           // 차선수
    val lat: String,                                            // 위도
    val length: Double,                                         // 길이(m)
    val link_cate: Double,                                      // 링크 종별
    val link_cate2: Int,                                        // 주종코드
    val link_id: Double,                                        // 링크 ID
    val lng: String,                                            // 경도
    val objectid: Int,                                          // 고유번호
    val oneway: Double,                                         // 일방통행여부
    val road_cate: Double,                                      // 도로종별코드
    val road_name: String,                                      // 도로명
    val road_no: String,                                        // 도로번호
    val st_dir: Int,                                            // 시작링크 각도
    val st_nd_id: Double,                                       // 시작노드 ID
    val track: Double                                           // 자전거도로


) {
    override fun toString(): String {
        return "WalkBicycleDTO(고도값=$altitude, 끝링크=$ed_dir, 끝노드=$ed_nd_id, 차선수=$lane, 위도='$lat', 길이=$length, 링크=$link_cate, 주종코드=$link_cate2, " +
                "링크=$link_id, 경도='$lng', 고유번호=$objectid, 일방통행여부=$oneway, 도로종별코드=$road_cate, 도로명='$road_name', 도로번호='$road_no', 시작링크=$st_dir, 시작노드=$st_nd_id, 자전거도로=$track)\n"
    }
}
