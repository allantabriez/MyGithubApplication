package com.example.consumerapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.consumerapp.model.ConsumerData
import kotlinx.android.synthetic.main.item_row_consumer.view.*

class ConsumerAdapter: RecyclerView.Adapter<ConsumerAdapter.ListViewHolder>(){
    private var favoriteList = emptyList<ConsumerData>()

    fun setData(items: List<ConsumerData>){
        favoriteList = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_row_consumer, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) = holder.bind(favoriteList[position])

    override fun getItemCount(): Int = favoriteList.size

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private val imageWidthHeight = 55
        fun bind(favorite: ConsumerData){
            with(itemView){
                consumerName.text = resources.getString(R.string.username_follow, favorite.userName)
                consumerUrl.text = resources.getString(R.string.url_follow, favorite.url)
                Glide.with(itemView)
                    .load(favorite.avatar)
                    .placeholder(R.drawable.logo_people)
                    .error(R.drawable.logo_error)
                    .apply(RequestOptions().override(imageWidthHeight, imageWidthHeight))
                    .into(consumerImage)
            }
        }
}

}