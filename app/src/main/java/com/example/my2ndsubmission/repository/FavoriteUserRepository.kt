package com.example.my2ndsubmission.repository

import android.database.Cursor
import com.example.my2ndsubmission.database.Favorites
import com.example.my2ndsubmission.database.FavoritesDao

class FavoriteUserRepository(private val userDao:FavoritesDao) {

    val userList = userDao.getFavoriteUsers()

    suspend fun insert(user: Favorites){
        userDao.insert(user)
    }

    suspend fun delete(user: Favorites){
        userDao.delete(user)
    }

    suspend fun getUser(username: String?): Favorites?{
        return userDao.getFavoriteUser(username)
    }

    fun getCursor(): Cursor{
        return userDao.getFavoritesCursor()
    }
}