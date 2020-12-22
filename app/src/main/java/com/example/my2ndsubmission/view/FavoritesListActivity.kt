package com.example.my2ndsubmission.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.my2ndsubmission.R
import com.example.my2ndsubmission.adapter.FavoritesAdapter
import com.example.my2ndsubmission.database.Favorites
import com.example.my2ndsubmission.view.detailuser.DetailUserActivity
import com.example.my2ndsubmission.viewmodel.FavoritesListViewModel
import kotlinx.android.synthetic.main.activity_favorite_list.*

class FavoritesListActivity : AppCompatActivity() {

    private lateinit var viewModel: FavoritesListViewModel
    private lateinit var adapter: FavoritesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite_list)
        supportActionBar?.title = getString(R.string.favorites)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        adapter = FavoritesAdapter()
        adapter.notifyDataSetChanged()
        favoriteRecyclerView.layoutManager = LinearLayoutManager(this)
        favoriteRecyclerView.adapter = adapter

        adapter.setOnItemClickCallback(object : FavoritesAdapter.OnItemClickCallback{
            override fun onItemClicked(data: Favorites) {
                val intent = Intent(this@FavoritesListActivity, DetailUserActivity::class.java)
                intent.putExtra(DetailUserActivity.EXTRA_NAME, data.userName)
                startActivity(intent)
            }
        })

        viewModel = ViewModelProvider(this).get(FavoritesListViewModel::class.java)
        viewModel.favoriteList.observe(this, Observer { list ->
            if (list != null){
                if (list.isNotEmpty()) {
                    emptyText.visibility = View.GONE
                    followerLine.visibility = View.VISIBLE
                }
                else if (list.isEmpty()) {
                    emptyText.visibility = View.VISIBLE
                    followerLine.visibility = View.INVISIBLE
                }
                favoriteBar.visibility = View.GONE
                adapter.setData(list)
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
    }
}