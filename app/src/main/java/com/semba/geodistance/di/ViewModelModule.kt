package com.semba.geodistance.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.semba.geodistance.ui.MainViewModel
import com.semba.geodistance.utils.ViewModelProviderFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * Created by SeMbA on 2019-10-08.
 */
@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(viewModel: MainViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(viewModelFactory: ViewModelProviderFactory): ViewModelProvider.Factory
}