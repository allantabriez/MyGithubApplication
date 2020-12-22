package com.example.my2ndsubmission.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import android.util.Log
import com.example.my2ndsubmission.database.FavoritesDao
import com.example.my2ndsubmission.database.FavoritesDatabase
import com.example.my2ndsubmission.repository.FavoriteUserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ConsumerProvider : ContentProvider() {

    companion object {
        private lateinit var roomRepository: FavoriteUserRepository
        private lateinit var favoritesDao: FavoritesDao
        private lateinit var cursor: Cursor
        private const val authority = "com.example.my2ndsubmission"
        private const val favoriteTable = "favorite_table"
        private const val favoriteData = 1
        private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH)

        init {
            uriMatcher.addURI(authority, favoriteTable, favoriteData)
        }
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        return 0
    }

    override fun getType(uri: Uri): String? {
        TODO(
            "Implement this to handle requests for the MIME type of the data" +
                    "at the given URI"
        )
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        TODO("Implement this to handle requests to insert a new row.")
    }

    override fun onCreate(): Boolean {
        favoritesDao = FavoritesDatabase.getDatabase(context!!).favoritesDao()
        roomRepository = FavoriteUserRepository(favoritesDao)
        return true
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
        if (uriMatcher.match(uri) == favoriteData) {
            cursor = roomRepository.getCursor()
            return cursor
        } else return null
    }

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        TODO("Implement this to handle requests to update one or more rows.")
    }
}
