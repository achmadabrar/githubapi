package com.achmadabrar.gitgubapi.core.di.module

import com.achmadabrar.githubapi.presentation.activity.SearchUserActivity
import com.achmadabrar.githubapi.presentation.activity.SettingsActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuildersModule {

    @ContributesAndroidInjector
    abstract fun contributesMainActivity(): SearchUserActivity

    @ContributesAndroidInjector
    abstract fun contributesSettingsActivity(): SettingsActivity
}