package com.example.withpet.vo.abandon

data class AbandonAnimalDTO(val response: Response)

data class Response(val body: Body)

data class Body(
        val items: Items,
        val numOfRows: String,
        val pageNo: String,
        val totalCount: String
)


data class Items(
        val item: ArrayList<Item>
)

data class Item(
        val age: String,
        val careAddr: String,
        val careNm: String,
        val careTel: String,
        val chargeNm: String,
        val colorCd: String,
        val desertionNo: String,
        val filename: String,
        val happenDt: String,
        val happenPlace: String,
        val kindCd: String,
        val neuterYn: String,
        val noticeEdt: String,
        val noticeNo: String,
        val noticeSdt: String,
        val officetel: String,
        val orgNm: String,
        val popfile: String,
        val processState: String,
        val sexCd: String,
        val specialMark: String,
        val weight: String
)