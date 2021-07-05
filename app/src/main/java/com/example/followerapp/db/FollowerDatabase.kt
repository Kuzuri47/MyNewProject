package com.example.followerapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Follower::class],version = 1)
abstract class FollowerDatabase(): RoomDatabase() {
    abstract val followerdao:FollowerDao
    companion object{
        private val Instance:FollowerDatabase ?= null
        fun getInstance(context: Context):FollowerDatabase{
            synchronized(this){
                var instance = Instance
                if (instance == null){
                    instance  = Room.databaseBuilder(
                        context.applicationContext,
                        FollowerDatabase::class.java,
                        "Follower_Data_Database"
                    ).build()
                }
                return instance
            }
        }
    }
}