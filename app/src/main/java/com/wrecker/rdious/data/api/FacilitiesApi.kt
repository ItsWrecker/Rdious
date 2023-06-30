package com.wrecker.rdious.data.api

import com.wrecker.rdious.domain.entities.FacilityData
import retrofit2.Response
import retrofit2.http.GET

interface FacilitiesApi {

    @GET("iranjith4/ad-assignment/db")
    suspend fun getFacilities(): Response<FacilityData>
}