package com.mybaseprojectandroid.model

data class Aktivitas(
    val id: String? = null,
    val idUser: String? = null,
    var dateUpdate: DateModel? = null,
    val startDate: DateModel? = null,
    val endDate: DateModel? = null,
    var sumWeekBring: Int? = null,
    var sumDayBring: Int? = null,
    val month: Int? = null,
    val week: Int? = null,
    var isUpdate: Boolean? = null,
    val timeStamp: Long? = null
)
