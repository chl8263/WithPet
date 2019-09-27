package com.example.withpet.vo.abandon

import com.google.gson.annotations.SerializedName

data class RawAbandonAnimalDTO(val response: Response)

data class Response(val body: Body)

data class Body(
        val items: Items,
        val numOfRows: String,
        val pageNo: String,
        val totalCount: String
)

data class Items(
        @SerializedName("item") val list: ArrayList<AbandonAnimalDTO>
)

data class AbandonAnimalDTO(
        val age: String,                  // 나이
        val careAddr: String,             // 보호장소
        val careNm: String,               // 보호소이름
        val careTel: String,              // 보호소전화번호
        val chargeNm: String,             // 담당자
        val colorCd: String,              // 색상
        val desertionNo: String,          // 유기번호
        val filename: String,             // Thumbnail Image
        val happenDt: String,             // 접수일
        val happenPlace: String,          // 발견장소
        val kindCd: String,               // 품종
        val neuterYn: String,             // 중성화여부
        val noticeEdt: String,            // 공고종료일
        val noticeNo: String,             // 공고번호
        val noticeSdt: String,            // 공고시작일
        val officetel: String,            // 담당자연락처
        val orgNm: String,                // 관할기관
        val popfile: String,              // Image
        val processState: String,         // 상태
        val sexCd: String,                // 성별
        val specialMark: String,          // 특징
        val weight: String                // 체중
){
        val neuterName get() = if(neuterYn == "Y") "예" else "아니오"
}