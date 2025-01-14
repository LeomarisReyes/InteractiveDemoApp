package com.example.data.remote.repository.implementations

import com.example.data.remote.apis.ColombiaPresidentsApi
import com.example.data.remote.models.ColombiaPresidentDto
import com.example.data.remote.models.PaginatedResponseDto
import com.example.data.remote.repository.ColombiaPresidentRepository
import com.example.data.remote.utils.NetworkResult
import com.example.data.remote.utils.handleApiRequest
import javax.inject.Inject


class ColombiaPresidentRepositoryImpl @Inject constructor(
    private val api: ColombiaPresidentsApi
) : ColombiaPresidentRepository {
    override suspend fun getPresidents(currentPage: Int, pageSize: Int): NetworkResult<PaginatedResponseDto> {
        return handleApiRequest {
            api.getColombianPresidents(currentPage = currentPage, pageSize = pageSize)
        }
    }

    override suspend fun getPresidentById(id: Int): NetworkResult<ColombiaPresidentDto> {
        return handleApiRequest {
            api.getPresidentById(id)
        }
    }
}