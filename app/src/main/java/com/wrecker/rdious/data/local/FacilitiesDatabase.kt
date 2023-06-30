package com.wrecker.rdious.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.wrecker.rdious.domain.entities.Exclusion
import com.wrecker.rdious.domain.entities.Facility
import com.wrecker.rdious.domain.entities.FacilityOption

@Database(
    entities = [FacilityOption::class, Facility::class, Exclusion::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(TypeConverter::class)
abstract class FacilitiesDatabase: RoomDatabase() {

    abstract val dao: FacilityDao
}