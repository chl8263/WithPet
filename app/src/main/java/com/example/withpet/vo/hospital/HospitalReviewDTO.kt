package com.example.withpet.vo.hospital

import java.io.Serializable

data class HospitalReviewDTO (

    var userId : String = "",
    var comment : String = "",
    var timeStamp : String = "",
    var starPoint : Int = 3

) : Serializable