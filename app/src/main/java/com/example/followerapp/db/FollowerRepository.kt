package com.example.followerapp.db

class FollowerRepository(private val dao:FollowerDao) {
    var followers = dao.getAllSubscriber()

    suspend fun insert(follower: Follower):Long{
        return dao.insertFollower(follower)  //it return new rowId
    }
    suspend fun update(follower:Follower){
        dao.updateFollower(follower)
    }
    suspend fun delete(follower: Follower){
        dao.deleteFollower(follower)
    }
    suspend fun deleteAll(){
        dao.deleteAll()
    }
}