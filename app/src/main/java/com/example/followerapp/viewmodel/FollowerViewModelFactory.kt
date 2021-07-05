package com.example.followerapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.followerapp.db.FollowerRepository
import java.lang.IllegalArgumentException

class FollowerViewModelFactory(val repository: FollowerRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FollowerViewModel::class.java)){
            return FollowerViewModel(repository) as T
        }
        throw IllegalArgumentException("UnKnown ViewModel Class")
    }
}