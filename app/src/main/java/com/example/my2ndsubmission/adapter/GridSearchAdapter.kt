package com.example.my2ndsubmission.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.my2ndsubmission.R
import com.example.my2ndsubmission.model.User
import kotlinx.android.synthetic.main.item_grid_user.view.*

class GridSearchAdapter: RecyclerView.Adapter<GridSearchAdapter.GridViewHolder>() {
    private val listUser = ArrayList<User>()
    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }
    fun setData(items: ArrayList<User>){
        listUser.clear()
        listUser.addAll(items)
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GridViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_grid_user, parent, false)
        return GridViewHolder(view)
    }

    override fun onBindViewHolder(holder: GridViewHolder, position: Int) {
        holder.bind(listUser[position])
    }

    override fun getItemCount(): Int = listUser.size

    inner class GridViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        private val imageWidthHeight = 75
        fun bind(user: User){
            with(itemView){
                itemUserName.text = user.user
                itemUserUrl.text = user.url
                Glide.with(itemView.context)
                    .load(user.imageUrl)
                    .placeholder(R.drawable.logo_people)
                    .error(R.drawable.logo_error)
                    .apply(RequestOptions().override(imageWidthHeight, imageWidthHeight))
                    .into(itemUserImage)
                itemView.setOnClickListener { onItemClickCallback?.onItemClicked(user) }
            }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: User)

    }
}