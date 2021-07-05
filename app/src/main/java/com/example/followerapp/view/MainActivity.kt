package com.example.followerapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.followerapp.FollowerRecyclerView
import com.example.followerapp.R
import com.example.followerapp.databinding.ActivityMainBinding
import com.example.followerapp.db.Follower
import com.example.followerapp.db.FollowerDatabase
import com.example.followerapp.db.FollowerRepository
import com.example.followerapp.viewmodel.FollowerViewModel
import com.example.followerapp.viewmodel.FollowerViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var followerViewModel: FollowerViewModel
   // lateinit var myRecycleView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        //myRecyclerView.setBackgroundColor(Color.GRAY)
       // myRecycleView = findViewById(R.id.my_recycler_view)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val dao = FollowerDatabase.getInstance(application).followerdao
        val repository = FollowerRepository(dao)
        val factory = FollowerViewModelFactory(repository)
        followerViewModel = ViewModelProvider(this,factory).get(FollowerViewModel::class.java)
        //Now assigning viewmodel Instance to databinding
        binding.myViewModel = followerViewModel
        //Since using livedata with binding we need lifecycle owner
        binding.lifecycleOwner = this
        myRecyclerView()
        followerViewModel.message.observe(this, Observer {
            it.getContentIfNotHandled()?.let {
                Toast.makeText(this,it,Toast.LENGTH_LONG).show()
            }
        })
    }
    fun myRecyclerView(){
        binding.myRecyclerView.layoutManager = LinearLayoutManager(this)
        displayFollowerList()
    }

    private fun displayFollowerList(){
        followerViewModel.listOfFollowers.observe(this, Observer {
            Log.i("MyTag",it.toString())
            binding.myRecyclerView.adapter = FollowerRecyclerView(it,{selectedItem:Follower -> ItemClicked(selectedItem)})
        })
    }
    private fun ItemClicked(follower: Follower){
       // Toast.makeText(this,"Follower name is: ${follower.username}",Toast.LENGTH_LONG).show()
        followerViewModel.toUpdateOrDeleteFollower(follower)
    }
}