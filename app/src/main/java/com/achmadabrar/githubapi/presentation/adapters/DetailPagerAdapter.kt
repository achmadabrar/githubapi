package com.achmadabrar.githubapi.presentation.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.achmadabrar.githubapi.presentation.fragment.FollowersFragment
import com.achmadabrar.githubapi.presentation.fragment.FollowingFragment

class DetailPagerAdapter
    (fragmentManager: FragmentManager) :
    FragmentPagerAdapter(fragmentManager) {

    private val list = listOf(
        FollowingFragment(),
        FollowersFragment()
    )

    override fun getItem(position: Int): Fragment {
        return list[position] as Fragment
    }

    override fun getCount(): Int {
        return list.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "Following"
            else -> "Followers"

        }
    }
}