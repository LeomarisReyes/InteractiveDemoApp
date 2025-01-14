package com.example.data.remote.models

data class PaginatedResponseDto (
    val page: Int,
    val pageSize  : Int,
    val totalRecords : Int?,
    val pageCount : Int?,
    val data : List<ColombiaPresidentDto>
)
