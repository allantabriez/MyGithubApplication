package com.example.my2ndsubmission.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_table")
class Favorites {
    @PrimaryKey
    @ColumnInfo(name = "userName")
    var userName = ""
    @ColumnInfo(name = "name")
    var name: String? = null
    @ColumnInfo(name = "url")
    var url: String? = null
    @ColumnInfo(name = "avatar")
    var avatar: String? = null
    @ColumnInfo(name = "company")
    var company: String? = null
    @ColumnInfo(name = "location")
    var location: String? = null
    @ColumnInfo(name = "followers")
    var followers: Int? = null
    @ColumnInfo(name = "following")
    var following: Int? = null
}