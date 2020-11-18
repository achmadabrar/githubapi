package com.achmadabrar.githubapi.presentation.fragment

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
import kotlinx.android.synthetic.main.fragment_followers.*
import javax.inject.Inject


/**
 * Abrar
 */
class FollowersFragment : BaseFragment(), SearchUserViewHolder.Listener {

    @Inject
    lateinit var viewModel: SearchUserViewModel

    lateinit var adapter: SearchUserAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_followers, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel =
            ViewModelProviders.of(activity!!, viewModelFactory).get(SearchUserViewModel::class.java)

        viewModel.followersLiveData.observe(viewLifecycleOwner, Observer {
            if (!it.isNullOrEmpty()) {
                adapter = SearchUserAdapter(null, it, this)
                loadRecycler()
            }
        })

        viewModel.networkLiveData.observe(viewLifecycleOwner, Observer {
            if (it.status.equals(NetworkState.LOADING.status)) {
                recycler_followers.visibility = View.GONE
                tv_loading_followers.visibility = View.VISIBLE
                tv_not_found_followers.visibility = View.GONE
            } else if (it.status.equals(NetworkState.LOADED.status)) {
                recycler_followers.visibility = View.VISIBLE
                tv_loading_followers.visibility = View.GONE
                tv_not_found_followers.visibility = View.GONE
            } else if (it.status.equals(NetworkState.EMPTY.status)) {
                recycler_followers.visibility = View.GONE
                tv_loading_followers.visibility = View.GONE
                tv_not_found_followers.visibility = View.VISIBLE
            } else {
                Toast.makeText(requireContext(), "Periksa Koneksi Anda", Toast.LENGTH_SHORT).show()
                recycler_followers.visibility = View.GONE
                tv_loading_followers.visibility = View.GONE
                tv_not_found_followers.visibility = View.VISIBLE
            }
        })
    }

    fun loadRecycler() {
        recycler_followers.adapter = adapter
        recycler_followers.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    }

    override fun onClickUser(item: Item) {
    //
    }

    override fun onClickDeleteFav(item: UserFav) {
        //
    }


}