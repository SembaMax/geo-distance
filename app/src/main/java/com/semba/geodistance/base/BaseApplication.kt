package com.semba.geodistance.base

import com.semba.geodistance.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

/**
 * Created by SeMbA on 2019-10-08.
 */
class BaseApplication : DaggerApplication() {

    override fun onCreate() {
        super.onCreate()
    }

    /**
     * Building dagger injection.
     */
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().application(this).context(this).build()
    }
}