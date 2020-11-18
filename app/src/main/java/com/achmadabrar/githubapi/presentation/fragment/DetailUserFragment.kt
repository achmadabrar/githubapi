package com.achmadabrar.githubapi.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.achmadabrar.gitgubapi.core.base.BaseFragment
import com.achmadabrar.githubapi.R
import com.achmadabrar.githubapi.presentation.adapters.DetailPagerAdapter
import com.achmadabrar.githubapi.presentation.viewmodel.SearchUserViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.fragment_detail_user.*
import javax.inject.Inject


/**
 * Abrar
 */
class DetailUserFragment : BaseFragment() {

    @Inject lateinit var viewModel: SearchUserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(activity!!, viewModelFactory).get(SearchUserViewModel::class.java)

        viewModel.selectedUserLiveData.observe(viewLifecycleOwner, Observer {
            Glide.with(this)
                .load(it.avatar_url)
                .apply(RequestOptions().override(250, 250))
                .centerCrop()
                .into(iv_user_icon)

            viewModel.getFollowingList(it.login)
        })

        view_pager.adapter = DetailPagerAdapter(childFragmentManager)

        fab.setOnClickListener {
            viewModel.addToFavorite()
            fragmentManager?.beginTransaction()?.addToBackStack(null)?.replace(R.id.frame_layout, FavoriteUserFragment())?.commit()
        }
    }
}