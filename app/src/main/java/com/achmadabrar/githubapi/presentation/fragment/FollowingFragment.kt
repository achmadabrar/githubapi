package com.achmadabrar.githubapi.presentation.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.achmadabrar.gitgubapi.core.base.BaseFragment
import com.achmadabrar.githubapi.R
import com.achmadabrar.githubapi.data.model.Item
import com.achmadabrar.githubapi.data.model.UserFav
import com.achmadabrar.githubapi.data.network.NetworkState
import com.achmadabrar.githubapi.presentation.adapters.SearchUserAdapter
import com.achmadabrar.githubapi.presentation.viewholder.SearchUserViewHolder
import com.achmadabrar.githubapi.presentation.viewmodel.SearchUserViewModel
import kotlinx.android.synthetic.main.fragment_following.*
import javax.inject.Inject

/**
 * Abrar
 */
class FollowingFragment : BaseFragment(), SearchUserViewHolder.Listener {

    @Inject
    lateinit var viewModel: SearchUserViewModel

    lateinit var adapter: SearchUserAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_following, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel =
            ViewModelProviders.of(activity!!, viewModelFactory).get(SearchUserViewModel::class.java)

        viewModel.followingLiveData.observe(viewLifecycleOwner, Observer {
            if (!it.isNullOrEmpty()) {
                adapter = SearchUserAdapter(
                    null, it, this)

                recycler_following.adapter = adapter
                recycler_following.layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            }
        })

        viewModel.networkLiveData.observe(viewLifecycleOwner, Observer {
            if (it.status.equals(NetworkState.Status.RUNNING)) {
                tv_loading_following.visibility = View.VISIBLE
                tv_not_found_following.visibility = View.GONE
                recycler_following.visibility = View.GONE
            } else if (it.status.equals(NetworkState.Status.SUCCESS)) {
                tv_loading_following.visibility = View.GONE
                tv_not_found_following.visibility = View.GONE
                recycler_following.visibility = View.VISIBLE
            } else if (it.status.equals(NetworkState.Status.EMPTY)) {
                tv_loading_following.visibility = View.GONE
                tv_not_found_following.visibility = View.VISIBLE
                recycler_following.visibility = View.GONE
            } else {
                Toast.makeText(requireContext(), "Periksa Koneksi Anda!", Toast.LENGTH_SHORT).show()
                tv_loading_following.visibility = View.GONE
                tv_not_found_following.visibility = View.VISIBLE
                recycler_following.visibility = View.GONE
            }
        })
    }

    override fun onClickUser(item: Item) {
        //
    }

    override fun onClickDeleteFav(item: UserFav) {
        //
    }


}