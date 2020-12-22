package com.example.my2ndsubmission.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Favorites::class], version = 1, exportSchema = false)
abstract class FavoritesDatabase: RoomDatabase() {

    abstract fun favoritesDao(): FavoritesDao

    companion object{
        @Volatile
        private var INSTANCE : FavoritesDatabase? = null

        fun getDatabase(context: Context): FavoritesDatabase{
            val instance = INSTANCE
            if (instance != null) return instance
            synchronized(this){
                val inst = Room.databaseBuilder(
                    context,
                    FavoritesDatabase::class.java,
                    "favorite_database"
                ).build()
                INSTANCE = inst
                return inst
            }
        }
    }
}