package com.wrecker.rdious.data.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.room.Room
import androidx.room.RoomDatabase
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.wrecker.rdious.data.api.FacilitiesApi
import com.wrecker.rdious.data.cache.CacheSerializer
import com.wrecker.rdious.data.local.FacilitiesDatabase
import com.wrecker.rdious.data.repositories.FacilityRepositoryImpl
import com.wrecker.rdious.domain.entities.Cache
import com.wrecker.rdious.domain.repositories.FacilityRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttp
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DataModule {


    private val interceptor = HttpLoggingInterceptor().also {
        it.setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Provides
    @Singleton
    fun providesRetrofitApi(): FacilitiesApi = Retrofit.Builder()
        .baseUrl("https://my-json-server.typicode.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(OkHttpClient.Builder().readTimeout(60, TimeUnit.SECONDS)
            .addInterceptor( interceptor)
            .build())
        .build()
        .create(FacilitiesApi::class.java)


    private val Context.dataStore by dataStore("cache.json", CacheSerializer)

    @Provides
    @Singleton
    fun provideDatStore (
        context: Context
    ): DataStore<Cache> = context.dataStore

    @Provides
    @Singleton
    fun provideFacilityDb(context: Context): FacilitiesDatabase {
      return  Room.databaseBuilder(
            context, FacilitiesDatabase::class.java,
            "facility.db"
        ).build()
    }



}