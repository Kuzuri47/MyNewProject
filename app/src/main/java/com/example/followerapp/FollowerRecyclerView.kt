package com.example.followerapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.followerapp.databinding.ListItemBinding
import com.example.followerapp.db.Follower

class FollowerRecyclerView(val followerList:List<Follower>,val clickEvent:(Follower)->Unit): RecyclerView.Adapter<MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        //Then we need to get the databinding object of list_item layout
        val binding:ListItemBinding = DataBindingUtil.inflate(layoutInflater,R.layout.list_item,parent,false)
        return MyViewHolder(binding)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        //here we get value in row and will pass it to bind function
        holder.bind(followerList[position],clickEvent)
    }

    override fun getItemCount(): Int {
        return followerList.size
    }
}
class MyViewHolder(val binding: ListItemBinding):RecyclerView.ViewHolder(binding.root){
    fun bind(follower: Follower,clickEvent:(Follower)->Unit){
        binding.userTextview.text = follower.username
        binding.userPasswordTextview.text = follower.userPassword
        binding.listItemLayout.setOnClickListener {
            //Write codes to pass the selected follower instance
            clickEvent(follower)
        }
    }
}