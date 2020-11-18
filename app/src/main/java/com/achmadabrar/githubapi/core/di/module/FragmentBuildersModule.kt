package com.achmadabrar.gitgubapi.core.di.module

import com.achmadabrar.githubapi.presentation.fragment.*
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuildersModule {

    @ContributesAndroidInjector
    abstract fun contributeSearchUserModule(): SearchUserFragment

    @ContributesAndroidInjector
    abstract fun contributeDetailUserModule(): DetailUserFragment

    @ContributesAndroidInjector
    abstract fun contributeFollowinglModule(): FollowingFragment

    @ContributesAndroidInjector
    abstract fun contributeFollowerModule(): FollowersFragment

    @ContributesAndroidInjector
    abstract fun contributeFavoriteUserFragment(): FavoriteUserFragment
}