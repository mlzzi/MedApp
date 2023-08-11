package com.leafwise.medapp.framework.di

import com.leafwise.medapp.domain.usecase.base.AppCoroutinesDispatchers
import com.leafwise.medapp.domain.usecase.base.CoroutinesDispatchers
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface CoroutineModule {

    @Binds
    fun bindDispatchers(dispatchers: AppCoroutinesDispatchers): CoroutinesDispatchers
}