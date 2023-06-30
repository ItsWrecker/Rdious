package com.wrecker.rdious.data.sources

import com.wrecker.rdious.domain.entities.Exclusion
import com.wrecker.rdious.domain.entities.FacilityData

interface DataSources {

    suspend fun getFacilities(): FacilityData?

    suspend fun updateFacilities(facilityData: FacilityData): Boolean = false

    suspend fun getExclusion(): List<Exclusion> = emptyList()

}