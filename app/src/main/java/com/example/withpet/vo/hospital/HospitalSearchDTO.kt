package com.example.withpet.vo.hospital

import java.io.Serializable

data class HospitalSearchDTO (

    var name : String ? = null,
    var address : String ? = null,
    var gu : String ? = null,
    var dong : String ? = null,
    var latitude : String ? = null,
    var longitude : String ? = null,
    var hospitalUid : String ? = null

) : Serializable