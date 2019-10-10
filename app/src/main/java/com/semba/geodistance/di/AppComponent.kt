package com.semba.geodistance.di

import android.app.Application
import android.content.Context
import com.semba.geodistance.base.BaseApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

/**
 * Created by SeMbA on 2019-10-08.
 */

@Singleton
@Component(modules = [
    AndroidInjectionModule::class,
    ActivityBindingModule::class,
    ViewModelModule::class,
    UtilsModule::class
])
interface AppComponent : AndroidInjector<BaseApplication> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        @BindsInstance
        fun context(context: Context): Builder

        fun build(): AppComponent
    }
}
