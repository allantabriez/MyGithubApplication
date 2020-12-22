package com.example.my2ndsubmission.view.detailuser

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.my2ndsubmission.R
import com.example.my2ndsubmission.adapter.RecyclerViewAdapter
import com.example.my2ndsubmission.viewmodel.DetailViewModel
import kotlinx.android.synthetic.main.fragment_followers.*


class FollowersFragment : Fragment() {
    private lateinit var viewModel: DetailViewModel
    private lateinit var adapter: RecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_followers, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        adapter = RecyclerViewAdapter()
        adapter.notifyDataSetChanged()
        followersRecyclerView.layoutManager = LinearLayoutManager(context)
        followersRecyclerView.adapter = adapter

        viewModel = ViewModelProvider(requireActivity(), ViewModelProvider.NewInstanceFactory()).get(DetailViewModel::class.java)
        viewModel.followersList.observe(viewLifecycleOwner, Observer { followers ->
            if (followers != null){
                if (followers.size != 0) followerLine.visibility = View.VISIBLE
                followersBar.visibility = View.GONE
                adapter.setData(followers)
            }
            else followersBar.visibility = View.GONE
        })
        viewModel.finishedFollowersLoading.observe(viewLifecycleOwner, Observer { loading ->
            if (loading == false){
                followersBar.visibility = View.GONE
                Toast.makeText(activity, getString(R.string.failed_get_data), Toast.LENGTH_SHORT).show()
            }
        })
    }
}