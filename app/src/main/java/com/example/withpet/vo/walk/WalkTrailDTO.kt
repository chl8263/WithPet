package com.example.withpet.vo.walk

import android.os.Parcel
import android.os.Parcelable
import com.example.withpet.ui.walk.enums.eWalkType


data class WalkTrailDTO(
        val area_gu: String? = "",                 // 자치구
        var content: String = "",                  // 설명
        val course_category_nm: String? = "",      // 코스 카테고리명
        val course_level: String? = "",            // 코스레벨
        val course_name: String = "",              // 코스명
        val cpi_content: String? = "",             // 포인트 설명
        val cpi_name: String? = "",                // 포인트명칭
        val detail_course: String? = "",           // 세부코스
        val distance: String? = "",                // 거리
        val lead_time: String? = "",               // 소요시간
        val relate_subway: String? = "",           // 연계지하철
        var traffic_info: String = "",             // 교통편
        val vote_cnt: Int? = 0,                    // 추천수
        val x: String? = "",                       // X 좌표
        val y: String? = ""                        // Y 좌표
) : WalkBaseDTO(
        type = eWalkType.TRAIL
) {
    override val _name get() = parsedName
    override val _latitude get() = x
    override val _longitude get() = y

    private val parsedName : String
        get() = run {
            val reg = Regex("[0-9]")
            course_name.replace(reg, "").replace("코스-","")
        }

    private val parsedContent: String
        get() = run {
            val index = content.indexOf("<br")
            if (index > 0) content.substring(0, index) else content
        }

    private val parsedTrafficInfo: String
        get() = traffic_info.replace("<br />", "\n")


            override

    fun extractDetailList(): ArrayList<WalkDetailRowDTO> = arrayListOf(
            WalkDetailRowDTO(Pair("자치구", area_gu)),
            WalkDetailRowDTO(Pair("코스 카테고리명", course_category_nm)),
            WalkDetailRowDTO(Pair("코스명", course_name)),
            WalkDetailRowDTO(Pair("코스레벨", course_level)),
            WalkDetailRowDTO(Pair("세부코스", detail_course)),
            WalkDetailRowDTO(Pair("포인트 설명", cpi_content)),
            WalkDetailRowDTO(Pair("포인트명칭", cpi_name)),
            WalkDetailRowDTO(Pair("설명", parsedContent)),
            WalkDetailRowDTO(Pair("거리", distance)),
            WalkDetailRowDTO(Pair("소요시간", lead_time)),
            WalkDetailRowDTO(Pair("연계지하철", relate_subway)),
            WalkDetailRowDTO(Pair("교통편", parsedTrafficInfo)),
            WalkDetailRowDTO(Pair("추천수", "${vote_cnt}개"))
    )

    constructor() : this(course_name = "")

    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readInt(),
            parcel.readString(),
            parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(area_gu)
        parcel.writeString(content)
        parcel.writeString(course_category_nm)
        parcel.writeString(course_level)
        parcel.writeString(course_name)
        parcel.writeString(cpi_content)
        parcel.writeString(cpi_name)
        parcel.writeString(detail_course)
        parcel.writeString(distance)
        parcel.writeString(lead_time)
        parcel.writeString(relate_subway)
        parcel.writeString(traffic_info)
        parcel.writeInt(vote_cnt?:0)
        parcel.writeString(x)
        parcel.writeString(y)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<WalkTrailDTO> {
        override fun createFromParcel(parcel: Parcel): WalkTrailDTO {
            return WalkTrailDTO(parcel)
        }

        override fun newArray(size: Int): Array<WalkTrailDTO?> {
            return arrayOfNulls(size)
        }
    }

}