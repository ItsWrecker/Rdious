package com.wrecker.rdious.data.sources

import com.wrecker.rdious.data.api.FacilitiesApi
import com.wrecker.rdious.domain.entities.FacilityData
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val facilitiesApi: FacilitiesApi
) : DataSources {

    override suspend fun getFacilities(): FacilityData? {
        return try {
            val response = facilitiesApi.getFacilities()
            println(response)
            if (response.isSuccessful && "${response.code()}".startsWith("20")) response.body()
            else null
        }catch (exception: Exception){
            null
        }
    }
}