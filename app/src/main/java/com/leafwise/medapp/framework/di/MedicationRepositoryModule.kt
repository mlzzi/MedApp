package com.leafwise.medapp.framework.di

import com.leafwise.medapp.data.repository.MedicationLocalDataSource
import com.leafwise.medapp.data.repository.MedicationRepository
import com.leafwise.medapp.framework.local.RoomMedicationDataSource
import com.leafwise.medapp.framework.MedicationRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface MedicationRepositoryModule {

    @Binds
    fun bindMedicationRepository(repository: MedicationRepositoryImpl): MedicationRepository

    @Binds
    fun bindLocalDataSource(
        dataSource: RoomMedicationDataSource
    ): MedicationLocalDataSource

}