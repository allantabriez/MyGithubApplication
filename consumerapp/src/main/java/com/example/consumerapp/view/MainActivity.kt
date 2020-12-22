package com.example.consumerapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.consumerapp.ConsumerAdapter
import com.example.consumerapp.model.ConsumerData
import com.example.consumerapp.R
import com.example.consumerapp.viewmodel.ConsumerViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: ConsumerAdapter
    private lateinit var viewModel: ConsumerViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapter = ConsumerAdapter()
        adapter.notifyDataSetChanged()

        consumerRecyclerView.layoutManager = LinearLayoutManager(this)
        consumerRecyclerView.adapter = adapter

        viewModel = ViewModelProvider(this).get(ConsumerViewModel::class.java)
        viewModel.liveData.observe(this, Observer { list ->
            if (list != null) updateUI(list)
        })
        viewModel.getData(this)
    }

    private fun updateUI(list: ArrayList<ConsumerData>){
        consumerBar.visibility = View.GONE
        adapter.setData(list)
    }
}
