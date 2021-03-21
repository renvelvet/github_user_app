package com.dicoding.githubuserapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.githubuserapp.databinding.ItemRowUserBinding

class UserAdapter(private val listUser: ArrayList<User>) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    inner class UserViewHolder(val binding: ItemRowUserBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = ItemRowUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = listUser[position]
        with(holder) {
            with(user) {
                binding.tvName.text = name
                binding.tvUsername.text = username
                Glide.with(holder.itemView.context).load(user.avatar).apply(RequestOptions().override(90,90)).into(binding.civAvatar)
            }
        }
    }

    override fun getItemCount(): Int = listUser.size
}