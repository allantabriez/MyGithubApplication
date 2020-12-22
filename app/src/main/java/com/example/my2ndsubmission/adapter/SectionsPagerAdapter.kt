package com.example.my2ndsubmission.adapter

import android.content.Context
import androidx.annotation.Nullable
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.my2ndsubmission.R
import com.example.my2ndsubmission.view.detailuser.DetailFragment
import com.example.my2ndsubmission.view.detailuser.FollowersFragment
import com.example.my2ndsubmission.view.detailuser.FollowingFragment

class SectionsPagerAdapter(private val context: Context, fm: FragmentManager): FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    @StringRes
    private var tabTitles = intArrayOf(R.string.tab_text_1, R.string.tab_text_2, R.string.tab_text_3)

    private var followers = 0
    private var following = 0

    fun setTitle(followers: Int, following: Int){
        this.followers = followers
        this.following = following
        notifyDataSetChanged()
    }

    @Nullable
    override fun getPageTitle(position: Int): CharSequence? {
        when (position){
            0 -> return context.resources.getString(tabTitles[position])
            1 -> return context.resources.getString(tabTitles[position]) + "($following)"
            2 -> return context.resources.getString(tabTitles[position]) + "($followers)"
        }
        return ""
    }

    override fun getCount(): Int {
        return 3
    }

    override fun getItem(position: Int): Fragment {
        var fragment: Fragment? = null
        when(position){
            0 -> fragment = DetailFragment()
            1 -> fragment = FollowingFragment()
            2 -> fragment = FollowersFragment()
        }

        return fragment as Fragment
    }

}