package com.example.my2ndsubmission.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.my2ndsubmission.R
import com.example.my2ndsubmission.model.User
import kotlinx.android.synthetic.main.item_row_followers.view.*

class RecyclerViewAdapter: RecyclerView.Adapter<RecyclerViewAdapter.ListViewHolder>() {
    private val listFollow = ArrayList<User>()


    fun setData(items: ArrayList<User>){
        listFollow.clear()
        listFollow.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_row_followers, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) = holder.bind(listFollow[position])

    override fun getItemCount(): Int = listFollow.size

    class ListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val imageWidthHeight = 55
        fun bind(follow: User){
            with(itemView){
                followerUsername.text = resources.getString(R.string.username_follow, follow.user)
                followerUrl.text = resources.getString(R.string.url_follow, follow.url)
                Glide.with(itemView)
                    .load(follow.imageUrl)
                    .placeholder(R.drawable.logo_people)
                    .error(R.drawable.logo_error)
                    .apply(RequestOptions().override(imageWidthHeight, imageWidthHeight))
                    .into(followerImage)
            }
        }
    }
}