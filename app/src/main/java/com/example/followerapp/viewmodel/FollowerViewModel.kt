package com.example.followerapp.viewmodel

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.followerapp.Event
import com.example.followerapp.db.Follower
import com.example.followerapp.db.FollowerRepository
import kotlinx.coroutines.launch

class FollowerViewModel(val repository: FollowerRepository): ViewModel() {
    val listOfFollowers = repository.followers
    val inputUsername = MutableLiveData<String>()
    val inputPassword = MutableLiveData<String>()
    val saveOrUpdateButton = MutableLiveData<String>()
    val clearAllOrDeleteButton = MutableLiveData<String>()


    var updateOrDelete = false
    lateinit var followerUpdateOrDeleteVariable:Follower

    //variable for Event
    private val statusMessage = MutableLiveData<Event<String>>()
    val message:LiveData<Event<String>>
    get() = statusMessage  //Status message is mutablelivedata it's value can change but being private we cannot access it outside the class
    init {
        saveOrUpdateButton.value = "Save"
        clearAllOrDeleteButton.value = "Clear All"
    }
    fun saveOrUpdate(){
        //To apply validation
        if(inputUsername.value == null){
            statusMessage.value = Event("Please enter valid UserName")
        }else if(inputPassword.value == null){
            statusMessage.value = Event("Please enter valid Password")
        }else if (!Patterns.EMAIL_ADDRESS.matcher(inputUsername.value!!).matches()){
            statusMessage.value = Event("Please enter valid UserName")
        }else {
            if (updateOrDelete) {
                followerUpdateOrDeleteVariable.username = inputUsername.value!!
                followerUpdateOrDeleteVariable.userPassword = inputPassword.value!!
                viewmodelupdateFollower(followerUpdateOrDeleteVariable)
            } else {
                val username = inputUsername.value!!
                val userpassword = inputPassword.value!!
                viewmodelinsertFollower(Follower(0, username, userpassword))
                inputUsername.value = ""
                inputPassword.value = ""
            }
        }

    }
    fun clearAllOrDelete(){
        if (updateOrDelete){
            viewmodelDeleteFollower(followerUpdateOrDeleteVariable)
        }else{
            viewmodelclearAllFollower()
        }

    }
    //Functions to to get access functions from repository
    //We use viewModelScope to run it in background thread
    fun viewmodelinsertFollower(follower: Follower){
        viewModelScope.launch {
            var newRowId = repository.insert(follower)
            if (newRowId > -1) {
                statusMessage.value = Event("Data is inserted Successfully at ${newRowId}")
            }else{
                statusMessage.value = Event("Data is not inserted correctly")
            }
        }
    }
    fun viewmodelupdateFollower(follower: Follower){
        viewModelScope.launch {
            repository.update(follower)
            inputUsername.value = ""
            inputPassword.value = ""
            updateOrDelete = false
            //To change button text
            saveOrUpdateButton.value = "SAVE"
            clearAllOrDeleteButton.value = "CLEAR ALL"
            statusMessage.value = Event("Data is updated Successfully")
        }
    }
    fun viewmodelDeleteFollower(follower: Follower){
        viewModelScope.launch {
            repository.delete(follower)
            inputUsername.value = ""
            inputPassword.value = ""
            updateOrDelete = false
            //To change button text
            saveOrUpdateButton.value = "SAVE"
            clearAllOrDeleteButton.value = "CLEAR ALL"
            statusMessage.value = Event("Data is deleted Successfully")
        }
    }
    fun viewmodelclearAllFollower(){
        viewModelScope.launch {
            repository.deleteAll()
            statusMessage.value = Event("All followers deleted Successfully")

        }
    }
    fun toUpdateOrDeleteFollower(follower: Follower){
        inputUsername.value = follower.username!!
        inputPassword.value = follower.userPassword!!
        updateOrDelete = true
        followerUpdateOrDeleteVariable = follower
        //To change button text
        saveOrUpdateButton.value = "UPDATE"
        clearAllOrDeleteButton.value = "DELETE"

    }

}