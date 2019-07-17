package com.example.withpet.vo.hospital

import java.io.Serializable

data class HospitalStarDTO (

    // 별점 평균
    var avg : Int ,
    // 별점 총 갯수
    var sum : Int ,

    // 별 1개의 갯수
    var starOne : Int ,
    // 별 2개의 갯수
    var starTwo : Int ,
    // 별 3개의 갯수
    var starThree : Int ,
    // 별 4개의 갯수
    var starFour : Int ,
    // 별 5개의 갯수
    var starFive : Int


) : Serializable