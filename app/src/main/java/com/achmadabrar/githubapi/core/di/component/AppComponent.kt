package com.achmadabrar.githubapi.core.di.component

import android.app.Application
import com.achmadabrar.gitgubapi.core.base.BaseApplication
import com.achmadabrar.gitgubapi.core.di.module.ActivityBuildersModule
import com.achmadabrar.gitgubapi.core.di.module.FragmentBuildersModule
import com.achmadabrar.gitgubapi.core.di.module.NetworkModule
import com.achmadabrar.gitgubapi.core.di.module.ViewModelModule
import com.achmadabrar.githubapi.core.di.module.DatabaseModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton


@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        ViewModelModule::class,
        ActivityBuildersModule::class,
        FragmentBuildersModule::class,
        NetworkModule::class,
        DatabaseModule::class
    ]
)
interface AppComponent : AndroidInjector<BaseApplication> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<BaseApplication>() {

        @BindsInstance
        abstract fun application(application: Application): Builder

        abstract fun networkModule(networkModule: NetworkModule): Builder
    }
}