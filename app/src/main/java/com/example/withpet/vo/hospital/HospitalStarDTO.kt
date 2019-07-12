package com.example.withpet.vo.hospital

import java.io.Serializable

data class HospitalStarDTO (

    // 별점 평균
    var avg : Int ? = null,
    // 별점 합계
    var sum : Int ? = null,

    // 별 1개의 갯수
    var starOne : String ? = null,
    // 별 2개의 갯수
    var starTwo : String ? = null,
    // 별 3개의 갯수
    var starThree : String ? = null,
    // 별 4개의 갯수
    var starFour : String ? = null,
    // 별 5개의 갯수
    var starFive : String ? = null

) : Serializable