package com.example.withpet.vo.eventBus

import com.example.withpet.vo.HospitalSearchDTO

data class HospitalCardEventVo(
    var eventName: String? = null,
    var data : HospitalSearchDTO
)
