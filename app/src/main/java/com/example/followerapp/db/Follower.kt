package com.example.followerapp.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "follower_table")
data class Follower (

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "follower_id")
    var id:Int,

    @ColumnInfo(name = "follower_name")
    var username:String?,

    @ColumnInfo(name = "follower_password")
    var userPassword:String?)