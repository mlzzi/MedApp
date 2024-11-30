package com.leafwise.medapp.framework.di

import android.content.Context
import com.leafwise.medapp.util.AlarmManagement
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

//    @Provides
//    @ViewModelScoped
//    fun provideAlarmManagement(@ApplicationContext context: Context): AlarmManagement {
//        return AlarmManagement(context)
//    }

    @Provides
    @ViewModelScoped
    fun providePermissions(@ApplicationContext context: Context): Permissions {
        return Permissions(context)
    }
}