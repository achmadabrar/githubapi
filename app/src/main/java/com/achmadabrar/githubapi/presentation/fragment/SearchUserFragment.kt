package com.achmadabrar.githubapi.presentation.fragment

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.achmadabrar.gitgubapi.core.base.BaseFragment
import com.achmadabrar.githubapi.R
import com.achmadabrar.githubapi.data.model.Item
import com.achmadabrar.githubapi.data.model.UserFav
import com.achmadabrar.githubapi.data.network.NetworkState
import com.achmadabrar.githubapi.presentation.activity.SettingsActivity
import com.achmadabrar.githubapi.presentation.adapters.SearchUserAdapter
import com.achmadabrar.githubapi.presentation.viewholder.SearchUserViewHolder
import com.achmadabrar.githubapi.presentation.viewmodel.SearchUserViewModel
import kotlinx.android.synthetic.main.fragment_search_user.*
import javax.inject.Inject

/**
 * Abrar
 */
class SearchUserFragment : BaseFragment(), SearchUserViewHolder.Listener {

    @Inject
    lateinit var viewModel: SearchUserViewModel
    lateinit var adapter: SearchUserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel =
            ViewModelProviders.of(activity!!, viewModelFactory).get(SearchUserViewModel::class.java)

        viewModel.usersLiveData.observe(viewLifecycleOwner, Observer {
            adapter = SearchUserAdapter(null, it, this)
            loadRecycler()
        })

        viewModel.networkSearchLiveData.observe(viewLifecycleOwner, Observer {
            if (it.status.equals(NetworkState.LOADED.status)) {
                tv_not_found.visibility = View.GONE
                tv_loading.visibility = View.GONE
                recycler_search_user.visibility = View.VISIBLE
            } else if (it.status.equals(NetworkState.LOADING.status)) {
                tv_not_found.visibility = View.GONE
                tv_loading.visibility = View.VISIBLE
                recycler_search_user.visibility = View.GONE
            } else if (it.status.equals(NetworkState.EMPTY.status)) {
                tv_not_found.visibility = View.VISIBLE
                tv_loading.visibility = View.GONE
                recycler_search_user.visibility = View.GONE
            } else {
                tv_not_found.visibility = View.VISIBLE
                tv_loading.visibility = View.GONE
                recycler_search_user.visibility = View.GONE
                Toast.makeText(requireContext(), "Periksa Koneksi Anda", Toast.LENGTH_SHORT).show()
            }
        })

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.searchUser(query!!)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.searchUser(newText!!)
                return true
            }

        })

        searchView.setOnCloseListener(object : SearchView.OnCloseListener {
            override fun onClose(): Boolean {
                viewModel.getListUser()
                return true
            }
        })

        (activity as AppCompatActivity).setSupportActionBar(toolbar_search_user)
        (activity as AppCompatActivity).supportActionBar?.title = "List User"
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.menu_fav -> {
                viewModel.addToFavorite()
                fragmentManager?.beginTransaction()?.replace(R.id.frame_layout, FavoriteUserFragment())?.addToBackStack(null)?.commit()
                return true
            }
            R.id.menu_setting -> {
                val intent = Intent(requireContext(), SettingsActivity::class.java)
                startActivity(intent)
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun loadRecycler() {
        recycler_search_user.adapter = adapter
        recycler_search_user.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    }

    override fun onClickUser(item: Item) {
        viewModel.getItem(item)
        viewModel.getFollowerList(item.login)
        val transaction = fragmentManager?.beginTransaction()
        transaction?.addToBackStack(null)
        transaction?.replace(R.id.frame_layout, DetailUserFragment())
        transaction?.commit()
    }

    override fun onClickDeleteFav(item: UserFav) {
        //
    }


}