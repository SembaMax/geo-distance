package com.semba.geodistance.di

import android.content.Context
import com.semba.geodistance.utils.FileManager
import com.semba.geodistance.utils.rx.AppSchedulerProvider
import com.semba.geodistance.utils.rx.ISchedulerProvider
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by SeMbA on 2019-10-08.
 */

@Module
class UtilsModule {

    @Singleton
    @Provides
    fun provideFileManager(context: Context) : FileManager
    {
        return FileManager(context)
    }

    @Singleton
    @Provides
    fun provideRxScheduler() : ISchedulerProvider
    {
        return AppSchedulerProvider()
    }
}