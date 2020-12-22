package com.example.my2ndsubmission.view.detailuser

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.my2ndsubmission.R
import com.example.my2ndsubmission.adapter.SectionsPagerAdapter
import com.example.my2ndsubmission.viewmodel.DetailViewModel
import kotlinx.android.synthetic.main.activity_detail_user.*

class DetailUserActivity : AppCompatActivity() {

    companion object{
        const val EXTRA_NAME = "extra_name"
    }

    private lateinit var detailViewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_user)

        supportActionBar?.title = getString(R.string.user_info)

        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        sectionsPagerAdapter.notifyDataSetChanged()
        viewPager.adapter = sectionsPagerAdapter
        tabs.setupWithViewPager(viewPager)
        supportActionBar?.elevation = 0F
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        detailViewModel = ViewModelProvider(this).get(DetailViewModel::class.java)
        detailViewModel.getUser(intent.getStringExtra(EXTRA_NAME)!!)
        detailViewModel.getUserDetail()
        detailViewModel.getFollowers()
        detailViewModel.getFollowing()
        detailViewModel.checkFavoriteUser()
        detailViewModel.detailLiveData.observe(this, Observer { user ->
            if (user != null) {
                sectionsPagerAdapter.setTitle(user.followers, user.following)
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
    }
}