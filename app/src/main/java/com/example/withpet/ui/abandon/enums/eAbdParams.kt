@file:Suppress("SpellCheckingInspection", "ClassName", "EnumEntryName", "NonAsciiCharacters")

package com.example.withpet.ui.abandon.enums

enum class eState(val param: String?) {
    //    //상태 - 전체 : null(빈값) - 공고중 : notice - 보호중 : protect
    전체(null),
    공고중("notice"),
    보호중("protect")
}

enum class eUpkind(val code: Int) {
    개(417000),
    고양이(422400),
    기타(429900)
}