package com.achmadabrar.githubapi.presentation.fragment

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
import kotlinx.android.synthetic.main.fragment_favorite_user.*
import javax.inject.Inject

/**
 * Abrar
 *
 * */
class FavoriteUserFragment : BaseFragment(), SearchUserViewHolder.Listener {

    @Inject lateinit var viewModel: SearchUserViewModel
    lateinit var adapter: SearchUserAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite_user, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(activity!!, viewModelFactory).get(SearchUserViewModel::class.java)

        viewModel.favUserLiveData.observe(viewLifecycleOwner, Observer {
            if (!it.isNullOrEmpty()) {
                adapter = SearchUserAdapter(it, null, this)
                loadRecycler()
                tv_empty_fav.visibility = View.GONE
            } else {
                tv_empty_fav.visibility = View.VISIBLE
                adapter = SearchUserAdapter(it, null, this)
                loadRecycler()
            }
        })

        viewModel.netWorkFavLiveData.observe(viewLifecycleOwner, Observer {
            if (it.status.equals(NetworkState.Status.RUNNING)) {
                recycler_view_favorite.visibility = View.GONE
                tv_empty_fav.visibility = View.GONE
                tv_loading_fav.visibility = View.VISIBLE
            } else if (it.status.equals(NetworkState.Status.SUCCESS)) {
                tv_loading_fav.visibility = View.GONE
                tv_empty_fav.visibility = View.GONE
                recycler_view_favorite.visibility = View.VISIBLE
            } else if (it.status.equals(NetworkState.Status.EMPTY)) {
                tv_empty_fav.visibility = View.VISIBLE
                tv_loading_fav.visibility = View.GONE
                recycler_view_favorite.visibility = View.GONE
            } else {
                tv_empty_fav.visibility = View.VISIBLE
                tv_loading_fav.visibility = View.GONE
                recycler_view_favorite.visibility = View.GONE
                Toast.makeText(requireContext(), "Terjadi Keasalahan", Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun loadRecycler() {
        recycler_view_favorite.adapter = adapter
        recycler_view_favorite.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    }

    override fun onClickUser(item: Item) {
        //
    }

    override fun onClickDeleteFav(item: UserFav) {
        viewModel.deleteUserFav(item)
    }

}