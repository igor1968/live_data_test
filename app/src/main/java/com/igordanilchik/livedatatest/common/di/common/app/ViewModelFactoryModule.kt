package com.igordanilchik.livedatatest.common.di.common.app

import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module

/**
 * @author Igor Danilchik
 */
@Module
abstract class ViewModelFactoryModule {
    @Binds
    abstract fun bindViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory
}