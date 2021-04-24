package com.dicoding.githubuserapp.ui.favorite

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.githubuserapp.adapter.UserAdapter
import com.dicoding.githubuserapp.data.local.entity.FavoriteUser
import com.dicoding.githubuserapp.data.remote.User
import com.dicoding.githubuserapp.databinding.ActivityFavoriteBinding
import java.util.*
import kotlin.collections.ArrayList

class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var userAdapter: UserAdapter
    private lateinit var favoriteViewModel: FavoriteViewModel
    private lateinit var listFavorite: ArrayList<User>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userAdapter = UserAdapter()
        userAdapter.notifyDataSetChanged()

        favoriteViewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)
        showLoading(true)
        getViewModelData()
        setRvUsers()

        supportActionBar?.elevation = 0f
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Favorite Buddy"
    }

    private fun getViewModelData() {
        favoriteViewModel.favoriteUsers.observe(this, {
            if (it != null) {
                if (it.size > 0) {
                    listFavorite = mapUsers(it)
                    userAdapter.setList(listFavorite)
                    showLoading(false)
                    binding.tvNoFavorite.visibility = View.GONE
                } else {
                    binding.tvNoFavorite.visibility = View.VISIBLE
                }
            }
        })
    }

    override fun onStart() {
        super.onStart()
        favoriteViewModel.getAll()
    }

    override fun onResume() {
        super.onResume()
        favoriteViewModel.getAll()
    }

    private fun setRvUsers() {
        binding.apply {
            rvUsers.layoutManager = LinearLayoutManager(this@FavoriteActivity)
            rvUsers.setHasFixedSize(true)
            rvUsers.adapter = userAdapter
        }
    }

    private fun mapUsers(list: List<FavoriteUser>): ArrayList<User> {
        val mappedUsers = ArrayList<User>()
        for (user in list) {
            val setMap = User(user.username, user.avatar_url, user.uid)
            mappedUsers.add(setMap)
        }

        return mappedUsers
    }

    private fun showLoading(state: Boolean) {
        binding.apply {
            if (state) {
                progressBar.visibility = View.VISIBLE
            } else {
                progressBar.visibility = View.GONE
            }
        }
    }
}