package com.achmadabrar.githubapi.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.achmadabrar.gitgubapi.core.base.BaseViewModel
import com.achmadabrar.githubapi.data.database.UserDao
import com.achmadabrar.githubapi.data.database.UserFavDao
import com.achmadabrar.githubapi.data.model.Item
import com.achmadabrar.githubapi.data.model.UserFav
import com.achmadabrar.githubapi.data.network.GithubApiService
import com.achmadabrar.githubapi.data.network.NetworkState
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val ONE_HOUR_CACHE = 3600 * 1000

class SearchUserViewModel @Inject constructor(
    val githubApiService: GithubApiService,
    val userFavDao: UserFavDao,
    val userDao: UserDao
): BaseViewModel<SearchUserViewModel>() {

    val networkLiveData: MutableLiveData<NetworkState> = MutableLiveData()
    val networkSearchLiveData: MutableLiveData<NetworkState> = MutableLiveData()
    val netWorkFavLiveData: MutableLiveData<NetworkState> = MutableLiveData()

    private val supervisorJob = SupervisorJob()
    val usersLiveData: MutableLiveData<List<Item>> = MutableLiveData()
    var followersLiveData: MutableLiveData<List<Item>> = MutableLiveData()
    var followingLiveData: MutableLiveData<List<Item>> = MutableLiveData()
    var selectedUserLiveData: MutableLiveData<Item> = MutableLiveData()
    var favUserLiveData: MutableLiveData<List<UserFav>> = MutableLiveData()

    init {
        getListUser()
    }

    fun searchUser(query: String) {
        ioScope.launch(getJobErrorHandler() +  supervisorJob) {
            val users = githubApiService.getUser(query)
            if (users.items.isNotEmpty()) {
                networkLiveData.postValue(NetworkState.LOADED)
                usersLiveData.postValue(users.items)
            } else {
                networkLiveData.postValue(NetworkState.FAILED)
            }
        }
    }

    fun getListUser() {
        ioScope.launch(getJobErrorHandler() + supervisorJob) {
            val listByCache = userDao.getAllListUser()
            if (listByCache.isNullOrEmpty()) {
                networkSearchLiveData.postValue(NetworkState.LOADING)
                val listUser = githubApiService.getListUser(0)
                if (listUser.isNotEmpty()) {
                    networkSearchLiveData.postValue(NetworkState.LOADED)
                    val users: MutableList<Item> = mutableListOf()
                    listUser.forEach {
                        users.add(it!!)
                    }
                    usersLiveData.postValue(users)
                    userDao.insertUser(users)
                } else {
                    networkSearchLiveData.postValue(NetworkState.FAILED)
                }
            } else {
                usersLiveData.postValue(listByCache)
                networkSearchLiveData.postValue(NetworkState.LOADED)

            }
        }
    }

    fun getItem(item: Item) {
        selectedUserLiveData.postValue(item)
    }

    fun getFollowerList(username: String) {
        ioScope.launch(getJobErrorHandler() + supervisorJob) {
            networkLiveData.postValue(NetworkState.LOADING)
            val followers = githubApiService.getFollower(username)
            if (followers.isNotEmpty()) {
                val newFollowers: MutableList<Item> = mutableListOf()
                followers.forEach {
                    newFollowers.add(it!!)
                }
                followersLiveData.postValue(newFollowers)
                networkLiveData.postValue(NetworkState.LOADED)
            }
        }
    }

    fun getFollowingList(username: String) {
        ioScope.launch(getJobErrorHandler() + supervisorJob) {
            networkLiveData.postValue(NetworkState.LOADING)
            val following = githubApiService.getFollowing(username)
            if (following.isNotEmpty()) {
                networkLiveData.postValue(NetworkState.LOADED)
                val newFollowing: MutableList<Item> = mutableListOf()
                following.forEach {
                    newFollowing.add(it!!)
                }
                followingLiveData.postValue(newFollowing)
            }
        }
    }

    fun addToFavorite() {
        val favoriteList: MutableList<UserFav> = mutableListOf()
        if (selectedUserLiveData.value != null) {
            val fav = UserFav(
                selectedUserLiveData.value?.login,
                selectedUserLiveData.value?.id,
                selectedUserLiveData.value?.node_id,
                selectedUserLiveData.value?.avatar_url,
                selectedUserLiveData.value?.url,
                selectedUserLiveData.value?.followers_url,
                selectedUserLiveData.value?.following_url,
                selectedUserLiveData.value?.gists_url,
                selectedUserLiveData.value?.starred_url,
                selectedUserLiveData.value?.subscriptions_url,
                selectedUserLiveData.value?.organizations_url,
                selectedUserLiveData.value?.repos_url,
                selectedUserLiveData.value?.events_url,
                selectedUserLiveData.value?.received_events_url,
                selectedUserLiveData.value?.type,
                selectedUserLiveData.value?.score)
            favoriteList.add(fav)
        }
        userFavDao.insertUser(favoriteList)
        val favorite = userFavDao.getAllListUser()
        if (!favorite.isNullOrEmpty()) {
            favUserLiveData.postValue(favorite)
            netWorkFavLiveData.postValue(NetworkState.LOADED)
        } else {
            netWorkFavLiveData.postValue(NetworkState.EMPTY)
        }
    }

    fun deleteUserFav(item: UserFav) {
        userFavDao.deleteUserFav(item.id)
        val favList = userFavDao.getAllListUser()
        favUserLiveData.postValue(favList)
    }

    private fun getJobErrorHandler() = CoroutineExceptionHandler { _, e ->
        Log.e(SearchUserViewModel::class.simpleName, "An error happened: $e")
        networkLiveData.postValue(NetworkState.fialed(e.localizedMessage))
        networkLiveData.postValue(NetworkState.FAILED)
    }
    
}