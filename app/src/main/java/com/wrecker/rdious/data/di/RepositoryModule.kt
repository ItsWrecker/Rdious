package com.wrecker.rdious.data.di

import com.wrecker.rdious.data.repositories.FacilityRepositoryImpl
import com.wrecker.rdious.data.sources.DataSources
import com.wrecker.rdious.data.sources.LocalDataSource
import com.wrecker.rdious.data.sources.RemoteDataSource
import com.wrecker.rdious.domain.repositories.FacilityRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    @Singleton
    fun bindFacilityRepository(
        impl: FacilityRepositoryImpl
    ): FacilityRepository

    @Binds
    @Singleton
//    @Named("local")
    fun bindLocalDataSource(
        impl: LocalDataSource
    ): DataSources

    @Binds
    @Singleton
//    @Named("remote")
    fun bindRemoteDataSource(
        impl: RemoteDataSource
    ): DataSources
}