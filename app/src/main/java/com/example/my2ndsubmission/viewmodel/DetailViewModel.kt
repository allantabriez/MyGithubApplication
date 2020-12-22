package com.example.my2ndsubmission.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.my2ndsubmission.database.Favorites
import com.example.my2ndsubmission.database.FavoritesDatabase
import com.example.my2ndsubmission.model.User
import com.example.my2ndsubmission.model.UserDetails
import com.example.my2ndsubmission.repository.FavoriteUserRepository
import com.example.my2ndsubmission.repository.LoopJ
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject

class DetailViewModel(application: Application): AndroidViewModel(application) {
    private var client = LoopJ()
    private var username: String? = null
    var detailLiveData = MutableLiveData<UserDetails>()
    var followersList = MutableLiveData<ArrayList<User>>()
    var followingList = MutableLiveData<ArrayList<User>>()
    val isFavorite = MutableLiveData<Boolean>()
    val finishedUserLoading = MutableLiveData<Boolean>()
    val finishedFollowersLoading = MutableLiveData<Boolean>()
    val finishedFollowingLoading = MutableLiveData<Boolean>()
    private val userDao = FavoritesDatabase.getDatabase(application).favoritesDao()
    private val roomRepository = FavoriteUserRepository(userDao)
    val tagDetailViewModel ="DetailViewModel"

    fun getUser(text: String){
        username = text
    }

    fun getUserDetail(){
        val url = "https://api.github.com/users/${username}"
        val asyncHttpResponseHandler = object : AsyncHttpResponseHandler(){
            override fun onSuccess(statusCode: Int, headers: Array<out Header>, responseBody: ByteArray) {
                viewModelScope.launch {
                    withContext(Dispatchers.IO){
                        val result = String(responseBody)
                        try {
                            val userDetail = UserDetails()
                            val responseObject = JSONObject(result)
                            userDetail.username = responseObject.getString("login")
                            userDetail.name = responseObject.getString("name")
                            userDetail.avatar = responseObject.getString("avatar_url")
                            userDetail.company = responseObject.getString("company")
                            userDetail.location = responseObject.getString("location")
                            userDetail.repository = responseObject.getString("html_url")
                            userDetail.followers = responseObject.getInt("followers")
                            userDetail.following = responseObject.getInt("following")

                            withContext(Dispatchers.Main){
                                detailLiveData.value = userDetail
                            }
                        }
                        catch (e: Exception){
                            Log.d(tagDetailViewModel, e.message.toString())
                            finishedUserLoading.postValue(false)
                        }
                    }
                }
            }

            override fun onFailure(statusCode: Int, headers: Array<out Header>, responseBody: ByteArray, error: Throwable) {
                Log.d(tagDetailViewModel, error.toString())
                finishedUserLoading.postValue(false)
            }
        }
        client.getData(url, asyncHttpResponseHandler)
    }

    fun getFollowing(){
        val url = "https://api.github.com/users/${username}/following"
        val asyncHttpResponseHandler = object : AsyncHttpResponseHandler(){
            override fun onSuccess(statusCode: Int, headers: Array<out Header>, responseBody: ByteArray) {
                viewModelScope.launch {
                    withContext(Dispatchers.IO){
                        val result = String(responseBody)
                        try {
                            val listItems = ArrayList<User>()
                            val responseArray = JSONArray(result)
                            for (i in 0 until responseArray.length()){
                                val user = User()
                                val objects = responseArray.getJSONObject(i)
                                user.user = objects.getString("login")
                                user.imageUrl = objects.getString("avatar_url")
                                user.url = objects.getString("html_url")
                                listItems.add(user)
                            }
                            withContext(Dispatchers.Main){
                                followingList.value = listItems
                            }
                        }
                        catch (e: Exception){
                            Log.d(tagDetailViewModel, e.message.toString())
                            finishedFollowingLoading.postValue(false)
                        }
                    }
                }
            }

            override fun onFailure(statusCode: Int, headers: Array<out Header>, responseBody: ByteArray, error: Throwable) {
                Log.d(tagDetailViewModel, error.toString())
                finishedFollowingLoading.postValue(false)
            }
        }
        client.getData(url, asyncHttpResponseHandler)
    }

    fun getFollowers(){
        val url = "https://api.github.com/users/${username}/followers"
        val asyncHttpResponseHandler = object : AsyncHttpResponseHandler(){
            override fun onSuccess(statusCode: Int, headers: Array<out Header>, responseBody: ByteArray) {
                viewModelScope.launch {
                    withContext(Dispatchers.IO){
                        val result = String(responseBody)
                        try {
                            val listItems = ArrayList<User>()
                            val responseArray = JSONArray(result)
                            for (i in 0 until responseArray.length()){
                                val user = User()
                                val objects = responseArray.getJSONObject(i)
                                user.user = objects.getString("login")
                                user.imageUrl = objects.getString("avatar_url")
                                user.url = objects.getString("html_url")
                                listItems.add(user)
                            }
                            withContext(Dispatchers.Main) {
                                followersList.value = listItems
                            }
                        }
                        catch (e: Exception){
                            Log.d(tagDetailViewModel, e.message.toString())
                            finishedFollowersLoading.postValue(false)
                        }
                    }
                }
            }

            override fun onFailure(statusCode: Int, headers: Array<out Header>, responseBody: ByteArray, error: Throwable) {
                Log.d(tagDetailViewModel, error.toString())
                finishedFollowersLoading.postValue(false)
            }
        }
        client.getData(url, asyncHttpResponseHandler)
    }

    fun checkFavoriteUser(){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                val user = roomRepository.getUser(username)
                if (user == null) isFavorite.postValue(false)
                else if (user.userName != null){
                    isFavorite.postValue(true)
                }
            }
        }
    }

    fun insertFavoriteUser(){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                val favorite = Favorites()
                favorite.userName = detailLiveData.value?.username!!
                favorite.name = detailLiveData.value?.name
                favorite.url = detailLiveData.value?.repository
                favorite.avatar = detailLiveData.value?.avatar
                favorite.company = detailLiveData.value?.company
                favorite.location = detailLiveData.value?.location
                favorite.followers = detailLiveData.value?.followers
                favorite.following = detailLiveData.value?.following
                roomRepository.insert(favorite)
            }
        }
    }

    fun deleteFavoriteUser(){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                val favorite = Favorites()
                favorite.userName = detailLiveData.value?.username!!
                favorite.name = detailLiveData.value?.name
                favorite.url = detailLiveData.value?.repository
                favorite.avatar = detailLiveData.value?.avatar
                favorite.company = detailLiveData.value?.company
                favorite.location = detailLiveData.value?.location
                favorite.followers = detailLiveData.value?.followers
                favorite.following = detailLiveData.value?.following
                roomRepository.delete(favorite)
            }
        }
    }
}