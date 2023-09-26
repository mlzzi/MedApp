package com.leafwise.medapp.framework.di

import com.leafwise.medapp.domain.usecase.AddMedicationUseCase
import com.leafwise.medapp.domain.usecase.AddMedicationUseCaseImpl
import com.leafwise.medapp.domain.usecase.GetMedicationsUseCase
import com.leafwise.medapp.domain.usecase.GetMedicationsUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface UseCaseModule {

    @Binds
    fun bindAddMedicationUseCase(useCase: AddMedicationUseCaseImpl): AddMedicationUseCase

    @Binds
    fun bindGetMedicationsUseCase(useCase: GetMedicationsUseCaseImpl): GetMedicationsUseCase

}