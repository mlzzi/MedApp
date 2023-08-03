package com.leafwise.medapp.framework.di

import android.content.Context
import com.leafwise.medapp.util.AlarmUtil
import com.leafwise.medapp.util.Permissions
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object ViewModelModule {
    @Provides
    @ViewModelScoped
    fun provideAlarmUtil(@ApplicationContext context: Context): AlarmUtil {
        return AlarmUtil(context)
    }

    @Provides
    @ViewModelScoped
    fun providePermissions(@ApplicationContext context: Context): Permissions {
        return Permissions(context)
    }
}