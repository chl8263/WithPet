package com.example.withpet.vo.hospital

import java.io.Serializable

data class HospitalCommentDTO (

    var userUid : String ,
    var comment : String ,
    var timeStamp : String

) : Serializable