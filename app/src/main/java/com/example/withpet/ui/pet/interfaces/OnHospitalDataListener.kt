package com.example.withpet.ui.pet.interfaces

import com.example.withpet.vo.hospital.HospitalSearchDTO

interface OnHospitalDataListener {
    fun getHospitalData(hospitalSearchDTO: HospitalSearchDTO)
}