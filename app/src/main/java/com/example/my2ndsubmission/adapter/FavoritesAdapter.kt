package com.example.my2ndsubmission.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.my2ndsubmission.R
import com.example.my2ndsubmission.database.Favorites
import com.example.my2ndsubmission.model.User
import kotlinx.android.synthetic.main.item_row_followers.view.*

class FavoritesAdapter : RecyclerView.Adapter<FavoritesAdapter.ListViewHolder>() {
    private var favoriteList = emptyList<Favorites>()
    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    fun setData(items: List<Favorites>) {
        favoriteList = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_row_followers, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) = holder.bind(favoriteList[position])

    override fun getItemCount(): Int = favoriteList.size

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageWidthHeight = 55
        fun bind(favorite: Favorites) {
            with(itemView) {
                followerUsername.text = resources.getString(R.string.username_follow, favorite.userName)
                followerUrl.text = resources.getString(R.string.url_follow, favorite.url)
                Glide.with(itemView)
                    .load(favorite.avatar)
                    .placeholder(R.drawable.logo_people)
                    .error(R.drawable.logo_error)
                    .apply(RequestOptions().override(imageWidthHeight, imageWidthHeight))
                    .into(followerImage)
                itemView.setOnClickListener { onItemClickCallback?.onItemClicked(favorite) }
            }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: Favorites)
    }
}