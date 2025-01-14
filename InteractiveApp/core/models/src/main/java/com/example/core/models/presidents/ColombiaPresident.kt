package com.example.core.models.presidents

data class ColombiaPresident(
    val id: Int = 0,
    val image: String = "",
    val name: String= "",
    val lastName: String= "",
    val startPeriodDate: String = "",
    val endPeriodDate: String? = null,
    val politicalParty: String= "",
    val description: String= ""
)
