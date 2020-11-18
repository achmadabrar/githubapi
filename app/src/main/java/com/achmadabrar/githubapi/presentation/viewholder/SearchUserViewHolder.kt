package com.achmadabrar.githubapi.presentation.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.achmadabrar.githubapi.data.model.Item
import com.achmadabrar.githubapi.data.model.UserFav
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.item_user.view.*

class SearchUserViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    fun bind(item: Item?, listener: Listener?, userFav: UserFav?) {
        with(itemView) {
            if (item != null) {
                iv_remove_user.visibility = View.GONE
                text_view_username.text = item.login
                Glide.with(this)
                    .load(item.avatar_url)
                    .centerInside()
                    .apply(RequestOptions().override(250, 250))
                    .into(image_view_profile)

                itemView.setOnClickListener {
                    listener?.onClickUser(item)
                }

            }

            if (userFav != null) {
                text_view_username.text = userFav.login
                Glide.with(this)
                    .load(userFav.avatar_url)
                    .centerInside()
                    .apply(RequestOptions().override(250, 250))
                    .into(image_view_profile)

                iv_remove_user.visibility = View.VISIBLE
                iv_remove_user.setOnClickListener {
                    listener?.onClickDeleteFav(userFav)
                }

            }
        }
    }

    interface Listener{
        fun onClickUser(item: Item)
        fun onClickDeleteFav(item: UserFav)
    }
}