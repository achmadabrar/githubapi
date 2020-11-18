package com.achmadabrar.gitgubapi.core.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.achmadabrar.gitgubapi.core.di.ViewModelFactory
import com.achmadabrar.gitgubapi.core.di.ViewModelKey
import com.achmadabrar.githubapi.presentation.viewmodel.SearchUserViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(SearchUserViewModel::class)
    internal abstract fun mainViewModel(viewModel: SearchUserViewModel): ViewModel

}