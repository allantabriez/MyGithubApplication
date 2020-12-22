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
import kotlinx.android.synthetic.main.fragment_following.*

class FollowingFragment : Fragment() {
    private lateinit var viewModel: DetailViewModel
    private lateinit var adapter: RecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_following, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        adapter = RecyclerViewAdapter()
        adapter.notifyDataSetChanged()
        followingRecyclerView.layoutManager = LinearLayoutManager(context)
        followingRecyclerView.adapter = adapter

        viewModel = ViewModelProvider(requireActivity(), ViewModelProvider.NewInstanceFactory()).get(DetailViewModel::class.java)
        viewModel.followingList.observe(viewLifecycleOwner, Observer { following ->
            if (following != null){
                if (following.size != 0) followerLine.visibility = View.VISIBLE
                followingBar.visibility = View.GONE
                adapter.setData(following)
            }
            else followingBar.visibility = View.GONE
        })
        viewModel.finishedFollowingLoading.observe(viewLifecycleOwner, Observer { loading ->
            if (loading == false){
                followingBar.visibility = View.GONE
                Toast.makeText(activity, getString(R.string.failed_get_data), Toast.LENGTH_SHORT).show()
            }
        })
    }
}