package com.example.withpet.vo.abandon

import android.os.Parcel
import android.os.Parcelable
import com.example.withpet.util.SDF
import com.example.withpet.vo.walk.WalkDetailRowDTO
import com.google.gson.annotations.SerializedName

data class RawAbandonAnimalDTO(val response: Response)

data class Response(val body: Body)

data class Body(
    val items: Items,
    val numOfRows: String,
    val pageNo: String,
    val totalCount: Int
)

data class Items(
    @SerializedName("item") val list: ArrayList<AbandonAnimalDTO>
)

data class AbandonAnimalDTO(
    val age: String,                  // 나이
    val careAddr: String,             // 보호장소
    val careNm: String,               // 보호소이름
    val careTel: String,              // 보호소전화번호
    val chargeNm: String,             // 담당자
    val colorCd: String,              // 색상
    val desertionNo: String,          // 유기번호
    val filename: String,             // Thumbnail Image
    val happenDt: String,             // 접수일
    val happenPlace: String,          // 발견장소
    val kindCd: String,               // 품종
    val neuterYn: String,             // 중성화여부
    val noticeEdt: String,            // 공고종료일
    val noticeNo: String,             // 공고번호
    val noticeSdt: String,            // 공고시작일
    val officetel: String,            // 담당자연락처
    val orgNm: String,                // 관할기관
    val popfile: String,              // Image
    val processState: String,         // 상태
    val sexCd: String,                // 성별
    val specialMark: String,          // 특징
    val weight: String                // 체중
) : Parcelable {
    val neuterName get() = if (neuterYn == "Y") "예" else "아니오"
    val dKindCd get() = kindCd.replace("[개] ", "")
    val sexName get() = if(sexCd == "M") "수컷" else "암컷"
    val colorName get() = "${colorCd}색"

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
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    fun extractDetailList(): ArrayList<WalkDetailRowDTO> = arrayListOf(
        WalkDetailRowDTO(Pair("유기번호", desertionNo)),
        WalkDetailRowDTO(Pair("상태", processState)),
        WalkDetailRowDTO(Pair("품종", dKindCd)),
        WalkDetailRowDTO(Pair("성별", sexName)),
        WalkDetailRowDTO(Pair("나이", age)),
        WalkDetailRowDTO(Pair("색상", colorName)),
        WalkDetailRowDTO(Pair("특징", specialMark)),
        WalkDetailRowDTO(Pair("체중", weight)),
        WalkDetailRowDTO(Pair("중성화여부", neuterName)),
        WalkDetailRowDTO(Pair("접수일", happenDt)),
        WalkDetailRowDTO(Pair("발견장소", happenPlace)),
        WalkDetailRowDTO(Pair("공고번호", noticeNo)),
        WalkDetailRowDTO(Pair("공고시작일", SDF.yyyymmdd_2.format(SDF.yyyymmdd.opt_parse(noticeSdt)))),
        WalkDetailRowDTO(Pair("공고종료일", SDF.yyyymmdd_2.format(SDF.yyyymmdd.opt_parse(noticeEdt)))),
        WalkDetailRowDTO(Pair("담당자", chargeNm)),
        WalkDetailRowDTO(Pair("담당자연락처", officetel)),
        WalkDetailRowDTO(Pair("관할기관", orgNm)),
        WalkDetailRowDTO(Pair("보호장소", careAddr)),
        WalkDetailRowDTO(Pair("보호소이름", careNm)),
        WalkDetailRowDTO(Pair("보호소전화번호", careTel))
//        WalkDetailRowDTO(Pair("Thumbnail Image", filename)),
//        WalkDetailRowDTO(Pair("Image", popfile)),
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(age)
        parcel.writeString(careAddr)
        parcel.writeString(careNm)
        parcel.writeString(careTel)
        parcel.writeString(chargeNm)
        parcel.writeString(colorCd)
        parcel.writeString(desertionNo)
        parcel.writeString(filename)
        parcel.writeString(happenDt)
        parcel.writeString(happenPlace)
        parcel.writeString(kindCd)
        parcel.writeString(neuterYn)
        parcel.writeString(noticeEdt)
        parcel.writeString(noticeNo)
        parcel.writeString(noticeSdt)
        parcel.writeString(officetel)
        parcel.writeString(orgNm)
        parcel.writeString(popfile)
        parcel.writeString(processState)
        parcel.writeString(sexCd)
        parcel.writeString(specialMark)
        parcel.writeString(weight)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<AbandonAnimalDTO> {
        override fun createFromParcel(parcel: Parcel): AbandonAnimalDTO {
            return AbandonAnimalDTO(parcel)
        }

        override fun newArray(size: Int): Array<AbandonAnimalDTO?> {
            return arrayOfNulls(size)
        }
    }
}