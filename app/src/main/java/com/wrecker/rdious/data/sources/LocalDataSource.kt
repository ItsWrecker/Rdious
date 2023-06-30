package com.wrecker.rdious.data.sources

import com.wrecker.rdious.data.local.FacilitiesDatabase
import com.wrecker.rdious.data.local.FacilityDao
import com.wrecker.rdious.domain.entities.Exclusion
import com.wrecker.rdious.domain.entities.FacilityData
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val database: FacilitiesDatabase
) : DataSources {


    override suspend fun getFacilities(): FacilityData? {
        return try {
            val facilities = database.dao.getFacilities()
            val exclusion = database.dao.getExclusion()
            if (facilities.isNotEmpty() && exclusion.isNotEmpty()){
                FacilityData(facilities, listOf(exclusion))
            }else null
        }catch (exception: Exception){
            exception.printStackTrace()
            null
        }
    }

    override suspend fun updateFacilities(facilityData: FacilityData): Boolean {
        return try {
            facilityData.facilities.onEach {
                database.dao.upsertFacility(it)
            }

            facilityData.exclusions.onEach { exclusions ->
                exclusions.onEach {
                    database.dao.upsertExclusion(it)
                }
            }
            true
        } catch (exception: Exception) {
            false
        }
    }

    override suspend fun getExclusion(): List<Exclusion> {
        return try {
            database.dao.getExclusion()
        }catch (exception: Exception){
            exception.printStackTrace()
            emptyList()
        }
    }

}