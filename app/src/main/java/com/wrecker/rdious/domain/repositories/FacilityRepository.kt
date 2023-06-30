package com.wrecker.rdious.domain.repositories

import com.wrecker.rdious.domain.entities.EventStatus
import com.wrecker.rdious.domain.entities.Exclusion
import com.wrecker.rdious.domain.entities.FacilityData
import kotlinx.coroutines.flow.Flow

interface FacilityRepository {

    suspend fun getFacility(): Flow<EventStatus<FacilityData>>

    suspend fun getExclusion(): Flow<EventStatus<List<Exclusion>>>

}