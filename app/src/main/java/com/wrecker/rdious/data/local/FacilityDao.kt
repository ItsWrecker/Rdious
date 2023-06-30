package com.wrecker.rdious.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.wrecker.rdious.domain.entities.Exclusion
import com.wrecker.rdious.domain.entities.Facility
import com.wrecker.rdious.domain.entities.FacilityOption


@Dao
interface FacilityDao {

    @Upsert
    suspend fun upsertFacilityOptions(option: FacilityOption)

    @Upsert
    suspend fun upsertFacility(facility: Facility)

    @Upsert
    suspend fun upsertExclusion(exclusion: Exclusion)

    @Query("SELECT * FROM facility ORDER BY facility_id")
    suspend fun getFacilities(): List<Facility>

    @Query("SELECT * FROM exclusion ORDER BY id")
    suspend fun getExclusion(): List<Exclusion>

}