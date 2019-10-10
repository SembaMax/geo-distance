package com.semba.geodistance.di

import com.semba.geodistance.ui.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by SeMbA on 2019-10-08.
 */
@Module
abstract class ActivityBindingModule {

    @ContributesAndroidInjector(modules = [ViewModelModule::class])
    abstract fun contributeMainActivity(): MainActivity

}