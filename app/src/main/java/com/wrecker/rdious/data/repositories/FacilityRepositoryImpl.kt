package com.wrecker.rdious.data.repositories

import androidx.datastore.core.DataStore
import com.wrecker.rdious.data.sources.LocalDataSource
import com.wrecker.rdious.data.sources.RemoteDataSource
import com.wrecker.rdious.domain.entities.Cache
import com.wrecker.rdious.domain.entities.EventStatus
import com.wrecker.rdious.domain.entities.Exclusion
import com.wrecker.rdious.domain.entities.FacilityData
import com.wrecker.rdious.domain.repositories.FacilityRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject
import javax.inject.Named

class FacilityRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Cache>,
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) : FacilityRepository {

    override suspend fun getFacility(): Flow<EventStatus<FacilityData>> = flow {
        try {
            emit(EventStatus.Loading("Data is being fetched please wait."))

            val cacheUpdateTime = dataStore.data.first().cacheTimestamp

            if (isToday(cacheUpdateTime)) {
                val data = localDataSource.getFacilities()
                    ?: fetchRemoteDataAndUpdateCache()
                    ?: return@flow emit(EventStatus.Error("Error while fetching the data"))
                return@flow emit(EventStatus.Success(data))
            } else return@flow emit(
                EventStatus.Success(
                    fetchRemoteDataAndUpdateCache()
                        ?: return@flow emit(EventStatus.Error("Error while fetching the data"))
                )
            )
        } catch (exception: Exception) {
            return@flow emit(EventStatus.Error(exception.message))
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun getExclusion(): Flow<EventStatus<List<Exclusion>>> = flow {
        try {
            emit(EventStatus.Loading("Loading the exclusion data"))
            val data = localDataSource.getExclusion()
            if (data.isNotEmpty()) return@flow emit(EventStatus.Success(data))
            else emit(EventStatus.Error())
        }catch (exception: Exception){
            exception.printStackTrace()
            return@flow emit(EventStatus.Error(exception.message))
        }
    }.flowOn(Dispatchers.IO)

    private suspend fun fetchRemoteDataAndUpdateCache(): FacilityData? {
        try {
            val response = remoteDataSource.getFacilities() ?: return null
            dataStore.updateData {
                it.copy(cacheTimestamp = System.currentTimeMillis())
            }
            localDataSource.updateFacilities(response)
            return response
        } catch (exception: Exception) {
            return null
        }
    }

    private fun isToday(timestamp: Long): Boolean {
        val currentDate = Date()
        val timestampDate = Date(timestamp)
        val dateFormat = SimpleDateFormat("yyyMMdd", Locale.getDefault())
        val currentDateString = dateFormat.format(currentDate)
        val timestampDateString = dateFormat.format(timestampDate)
        return currentDateString == timestampDateString
    }
}