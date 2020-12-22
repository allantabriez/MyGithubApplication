package com.example.consumerapp.viewmodel

import android.app.Application
import android.content.Context
import android.database.Cursor
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.consumerapp.model.ConsumerData
import kotlinx.coroutines.*

class ConsumerViewModel(application: Application) : AndroidViewModel(application){

    val liveData = MutableLiveData<ArrayList<ConsumerData>>()

    fun getData(context: Context){
        viewModelScope.launch(Dispatchers.IO) {
            var cursor: Cursor?
            var list = ArrayList<ConsumerData>()
            val contentUri = Uri.Builder().scheme("content")
                .authority("com.example.my2ndsubmission")
                .appendPath("favorite_table")
                .build()
            val getData = async {
                cursor = context.contentResolver.query(contentUri, null, null, null, null)
                list = mapCursorToArrayList(cursor)
            }
            getData.await()
            liveData.postValue(list)
        }
    }

    private fun mapCursorToArrayList(cursor: Cursor?): ArrayList<ConsumerData>{
        val list = ArrayList<ConsumerData>()
        cursor?.apply {
            while (moveToNext()){
                val data = ConsumerData(
                    getString(getColumnIndexOrThrow("userName")),
                    getString(getColumnIndexOrThrow("url")),
                    getString(getColumnIndexOrThrow("avatar"))
                )
                list.add(data)
            }
        }
        return list
    }
}