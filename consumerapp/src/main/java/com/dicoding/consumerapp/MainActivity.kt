package com.dicoding.consumerapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.consumerapp.data.viewmodel.FavoriteViewModel
import com.dicoding.consumerapp.databinding.ActivityMainBinding
import com.dicoding.githubuserapp.adapter.UserAdapter

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private lateinit var userAdapter: UserAdapter
    private lateinit var favoriteViewModel: FavoriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userAdapter = UserAdapter()
        userAdapter.notifyDataSetChanged()

        favoriteViewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)
        showLoading(true)
        favoriteViewModel.setFavorite(this)
        getViewModelData()
        setRvUsers()

        supportActionBar?.elevation = 0f
        supportActionBar?.title = "Favorite Buddy"
    }

    private fun getViewModelData() {
        favoriteViewModel.getAll().observe(this, {

            if (it != null) {
                if (it.size > 0) {
                    userAdapter.setList(it)
                    showLoading(false)
                }
            }
        })

    }

    private fun setRvUsers() {
        binding.apply {
            rvUsers.layoutManager = LinearLayoutManager(this@MainActivity)
            rvUsers.setHasFixedSize(true)
            rvUsers.adapter = userAdapter
        }
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