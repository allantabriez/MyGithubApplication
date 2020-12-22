package com.example.my2ndsubmission.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.my2ndsubmission.model.User
import com.example.my2ndsubmission.repository.LoopJ
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject

class SearchViewModel : ViewModel() {
    private var client = LoopJ()
    val tagSearchViewModel ="SearchViewModel"
    private val listSearch = MutableLiveData<ArrayList<User>>()
    private val finishedLoading = MutableLiveData<Boolean>()

    fun getLoading(): MutableLiveData<Boolean> {
        return finishedLoading
    }

    fun getUsers(name: String) {
        val url = "https://api.github.com/search/users?q=${name}"
        val asyncHttpResponseHandler = object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<out Header>, responseBody: ByteArray) {
                viewModelScope.launch {
                    withContext(Dispatchers.IO){
                        val result = String(responseBody)
                        try {
                            val listItems = ArrayList<User>()
                            val responseObject = JSONObject(result)
                            val datayArray = responseObject.getJSONArray("items")
                            for (i in 0 until datayArray.length()){
                                val user = User()
                                val userObject = datayArray.getJSONObject(i)
                                user.user = userObject.getString("login")
                                user.url = userObject.getString("html_url")
                                user.imageUrl = userObject.getString("avatar_url")
                                listItems.add(user)
                            }
                            withContext(Dispatchers.Main){
                                listSearch.value = listItems
                                finishedLoading.value = true
                            }
                        }
                        catch (e:Exception){
                            Log.d(tagSearchViewModel, e.message.toString())
                            finishedLoading.postValue(false)
                        }
                    }
                }
            }
            override fun onFailure(
                statusCode: Int, headers: Array<out Header>, responseBody: ByteArray, error: Throwable) {
                Log.d(tagSearchViewModel, error.message.toString())
                finishedLoading.postValue(false)
            }
        }
        client.getData(url, asyncHttpResponseHandler)
    }

    fun getList(): MutableLiveData<ArrayList<User>> {
        return listSearch
    }
}