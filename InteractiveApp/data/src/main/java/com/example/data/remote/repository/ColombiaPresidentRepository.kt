package com.example.data.remote.repository

import com.example.data.remote.models.ColombiaPresidentDto
import com.example.data.remote.models.PaginatedResponseDto
import com.example.data.remote.utils.NetworkResult

interface ColombiaPresidentRepository {
    suspend fun getPresidents(currentPage: Int, pageSize : Int): NetworkResult<PaginatedResponseDto>
    suspend fun getPresidentById(id : Int): NetworkResult<ColombiaPresidentDto>
}
