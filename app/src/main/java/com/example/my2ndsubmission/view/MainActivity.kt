package com.example.my2ndsubmission.view

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.my2ndsubmission.R
import com.example.my2ndsubmission.adapter.GridSearchAdapter
import com.example.my2ndsubmission.model.User
import com.example.my2ndsubmission.view.detailuser.DetailUserActivity
import com.example.my2ndsubmission.viewmodel.SearchViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var searchViewModel: SearchViewModel
    private lateinit var adapter: GridSearchAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.title = getString(R.string.search_user)

        adapter = GridSearchAdapter()
        adapter.notifyDataSetChanged()
        rvSearch.layoutManager = GridLayoutManager(this, 2)
        rvSearch.adapter = adapter

        adapter.setOnItemClickCallback(object : GridSearchAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User) {
                val intent = Intent(this@MainActivity, DetailUserActivity::class.java)
                intent.putExtra(DetailUserActivity.EXTRA_NAME, data.user)
                startActivity(intent)
            }
        })

        searchViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(SearchViewModel::class.java)
        searchViewModel.getList().observe(this, Observer { list ->
            if (list != null) {
                adapter.setData(list)
                showLoading(false)
            } else {
                searchBar.visibility = View.GONE
            }
        })

        searchViewModel.getLoading().observe(this, Observer { loading ->
            if (loading == false) {
                searchBar.visibility = View.GONE
                Toast.makeText(this, getString(R.string.failed_get_data), Toast.LENGTH_LONG).show()
            }
        })

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) searchViewModel.getUsers(query.toString())
                searchView.clearFocus()
                showLoading(true)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_language ->{
                val intent = Intent(Settings.ACTION_LOCALE_SETTINGS)
                startActivity(intent)
            }
            R.id.action_favorite_list ->{
                val intent = Intent(this, FavoritesListActivity::class.java)
                startActivity(intent)
            }
            R.id.action_settings ->{
                val intent = Intent(this, SettingActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun showLoading(state: Boolean) {
        if (state) {
            mainLogo.visibility = View.VISIBLE
            rvSearch.visibility = View.INVISIBLE
            searchBar.visibility = View.VISIBLE
        } else {
            mainLogo.visibility = View.GONE
            rvSearch.visibility = View.VISIBLE
            searchBar.visibility = View.GONE
        }
    }
}