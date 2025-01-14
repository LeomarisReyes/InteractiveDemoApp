package com.example.data.remote.apis

import com.example.data.remote.models.ColombiaPresidentDto
import com.example.data.remote.models.PaginatedResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

private const val COLOMBIA_PRESIDENTS_URL = "President/pagedList/"
private const val COLOMBIA_PRESIDENTS_BY_ID_URL = "President/"
private const val SORT_BY_VALUE = "sortBy"
private const val SORT_DIRECTION_VALUE = "sortDirection"
private const val PAGE_VALUE = "Page"
private const val PAGE_SIZE_VALUE = "PageSize"

interface ColombiaPresidentsApi {
    @GET(COLOMBIA_PRESIDENTS_URL)
    suspend fun getColombianPresidents(
        @Query(PAGE_VALUE) currentPage: Int? = 1,
        @Query(PAGE_SIZE_VALUE) pageSize: Int? = 5,
        @Query(SORT_BY_VALUE) sortBy: String? = "id",
        @Query(SORT_DIRECTION_VALUE) sortDirection: String? = "asc",
    ): Response<PaginatedResponseDto>

    @GET("$COLOMBIA_PRESIDENTS_BY_ID_URL/{id}")
    suspend fun getPresidentById(
        @Path("id") id: Int
    ): Response<ColombiaPresidentDto>

}
