package com.wrecker.rdious.domain.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable


@Entity
data class FacilityOption(
    val name: String,
    val icon: String,
    @PrimaryKey(autoGenerate = false)
    val id: String
)

@Entity
data class Facility(
    @PrimaryKey(autoGenerate = false)
    val facility_id: String,
    val name: String,
    val options: List<FacilityOption>
)

@Entity
data class Exclusion(
    @PrimaryKey(autoGenerate = true)
    val id: Int=0,
    val facility_id: String,
    val options_id: String
)
@Entity
data class FacilityData(
    val facilities: List<Facility>,
    val exclusions: List<List<Exclusion>>
)


@Serializable
data class Cache(
    val cacheTimestamp: Long =0
)


