package com.achmadabrar.githubapi.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.achmadabrar.githubapi.R
import com.achmadabrar.githubapi.data.model.Item
import com.achmadabrar.githubapi.data.model.UserFav
import com.achmadabrar.githubapi.presentation.viewholder.SearchUserViewHolder

class SearchUserAdapter(
    val list: List<UserFav>?,
    val listItem: List<Item>?,
    val listener: SearchUserViewHolder.Listener?
): RecyclerView.Adapter<SearchUserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchUserViewHolder {
        return SearchUserViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false))
    }

    override fun onBindViewHolder(holder: SearchUserViewHolder, position: Int) {
        if (listItem.isNullOrEmpty()) {
            list?.get(position).let {
               holder.bind(null, listener, it)
            }
        } else {
            listItem[position].let {
                holder.bind(it, listener, null)
            }
        }
    }

    override fun getItemCount(): Int {
        if (!listItem.isNullOrEmpty()) {
            return listItem.size
        } else {
            return list?.size!!
        }
    }
}