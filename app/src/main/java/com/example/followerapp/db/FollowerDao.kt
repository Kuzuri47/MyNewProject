package com.example.followerapp.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FollowerDao {
    @Insert
    suspend fun insertFollower(follower: Follower):Long

    @Update
    suspend fun updateFollower(follower: Follower)

    @Delete
    suspend fun deleteFollower(follower: Follower)

    @Query("Delete FROM follower_table")
    suspend fun deleteAll()

    @Query("Select * FROM follower_table")
    fun getAllSubscriber(): LiveData<List<Follower>>
}