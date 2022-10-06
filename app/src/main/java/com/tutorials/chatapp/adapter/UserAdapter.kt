package com.tutorials.chatapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tutorials.chatapp.R
import com.tutorials.chatapp.User
import com.tutorials.chatapp.databinding.ItemProfileBinding

class UserAdapter(var context: Context,var userList: ArrayList<User>) :
RecyclerView.Adapter<UserAdapter.UserViewHolder>()

{

    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val binding: ItemProfileBinding = ItemProfileBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserAdapter.UserViewHolder {
        var view = LayoutInflater.from(context).inflate(R.layout.item_profile,parent,false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserAdapter.UserViewHolder, position: Int) {
        val user = userList[position]
        holder.binding.userName.text = user.name
        Glide.with(context).load(user.profileImage)
            .placeholder(R.drawable.ic_avatar)
            .into(holder.binding.profile)
    }

    override fun getItemCount(): Int {
        return userList.size
    }
}