package com.example.withpet.vo.hospital

import java.io.Serializable

data class HospitalStarDTO (

    // 별점 평균
    var avg : Double = 0.0 ,
    // 별점 총 점수
    var sum : Int = 0 ,

    // 별점 총 갯수
    var starTotalCount : Int = 0 ,
    // 별 1개의 갯수
    var starOne : Int = 0 ,
    // 별 2개의 갯수
    var starTwo : Int = 0 ,
    // 별 3개의 갯수
    var starThree : Int = 0 ,
    // 별 4개의 갯수
    var starFour : Int = 0 ,
    // 별 5개의 갯수
    var starFive : Int = 0


) : Serializable