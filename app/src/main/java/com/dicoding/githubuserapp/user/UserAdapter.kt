package com.dicoding.githubuserapp.user

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.dicoding.githubuserapp.R
import com.dicoding.githubuserapp.databinding.ItemRowUserBinding
import com.dicoding.githubuserapp.network.User
import com.dicoding.githubuserapp.detail.DetailUserActivity

class UserAdapter : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    private val listUser = ArrayList<User>()

    fun setList(users: ArrayList<User>) {
        listUser.clear()
        listUser.addAll(users)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): UserViewHolder {
        val binding =
            ItemRowUserBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(listUser[position])

        holder.itemView.setOnClickListener {
            val intent = Intent(it.context, DetailUserActivity::class.java)
            intent.putExtra(DetailUserActivity.KEY_USER, listUser[position].username)
            it.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = listUser.size

    inner class UserViewHolder(val binding: ItemRowUserBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(user: User) {
            binding.apply {
                tvUsername.text = user.username
                Glide.with(itemView.context)
                    .load(user.avatar)
                    .transform(CenterCrop(), RoundedCorners(30))
                    .apply(
                        RequestOptions().placeholder(R.drawable.ic_baseline_downloading_24)
                            .error(R.drawable.ic_baseline_broken_image_24)
                    )
                    .into(civAvatar)
            }
        }
    }
}