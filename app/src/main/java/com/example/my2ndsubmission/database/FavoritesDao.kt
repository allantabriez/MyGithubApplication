package com.example.my2ndsubmission.database

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FavoritesDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(favorites: Favorites)

    @Delete(entity = Favorites::class)
    suspend fun delete(favorites: Favorites)

    @Query("SELECT * from favorite_table ORDER BY userName ASC")
    fun getFavoriteUsers(): LiveData<List<Favorites>>

    @Query("SELECT * from favorite_table ORDER BY userName ASC")
    fun getFavoritesCursor(): Cursor

    @Query("SELECT * from favorite_table WHERE userName LIKE :searchedName")
    suspend fun getFavoriteUser(searchedName: String?): Favorites?
}