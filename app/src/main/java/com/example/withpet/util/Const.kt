package com.example.withpet.util

import com.google.android.gms.maps.model.LatLng

object Const {

/**
*    fireStore collection
*/
//--------------------------------------------------
    // 1 depth collection
    val COLECT_HOSPITAL ="hospital"

    // 2 depth collection
    val COLECT_REVIEW ="review"
    val COLECT_STAR ="star"

//--------------------------------------------------
    // 1 depth collection Field name
    val COLECT_HOSPITAL_NAME        ="name"
    val COLECT_HOSPITAL_GU          ="gu"
    val COLECT_HOSPITAL_ADDRESS     ="address"
    val COLECT_HOSPITAL_LATITUDE    ="latitude"
    val COLECT_HOSPITAL_LONGTITUDE  ="longitude"
    val COLECT_HOSPITAL_HOSPITALUID ="hospitalUid"

/**
 *    Storage
 */
    val STORAGE_FOLDER_PROFILEIMAGE       ="profileImages"
    val STORAGE_FOLDER_USERPROFILEIMAGES  ="userProfileImages"


/**
 *    eventBus
 */
    val SHOW_HOSPITAL_CARDVIEW = "SHOW_HOSPITAL_CARDVIEW"


/**
 *    fragment bundle
 */

    val HOSPITAL_DETAIL_DATA = "HOSPITAL_DETAIL_DATA"


    /**
     * 현재 위치 로딩 전, 지도의 최초 실행지점 : 서울특별시청 좌표
     */
    val MAP_START_LOCATION = LatLng(37.5711337,126.9745258)
}
