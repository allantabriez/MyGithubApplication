package com.example.my2ndsubmission.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.my2ndsubmission.database.FavoritesDatabase
import com.example.my2ndsubmission.repository.FavoriteUserRepository

class FavoritesListViewModel(application: Application): AndroidViewModel(application) {
    private val favoriteDao = FavoritesDatabase.getDatabase(application).favoritesDao()
    private val roomRepository = FavoriteUserRepository(favoriteDao)
    val favoriteList = roomRepository.userList
}