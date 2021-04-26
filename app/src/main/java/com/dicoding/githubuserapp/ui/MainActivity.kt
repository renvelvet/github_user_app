package com.dicoding.githubuserapp.ui

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.githubuserapp.R
import com.dicoding.githubuserapp.adapter.UserAdapter
import com.dicoding.githubuserapp.databinding.ActivityMainBinding
import com.dicoding.githubuserapp.ui.favorite.FavoriteActivity
import com.dicoding.githubuserapp.ui.user.SettingsActivity
import com.dicoding.githubuserapp.ui.user.UserViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var userAdapter: UserAdapter
    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userAdapter = UserAdapter()
        userAdapter.notifyDataSetChanged()

        userViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(UserViewModel::class.java)

        binding.apply {
            rvUsers.layoutManager = LinearLayoutManager(this@MainActivity)
            rvUsers.setHasFixedSize(true)
            rvUsers.adapter = userAdapter
        }

        userViewModel.getList.observe(this, {
            if (it != null) {
                userAdapter.setList(it)
                showLoading(false)
                changeWelcomingDisplay()
            }
        })
    }

    private fun changeWelcomingDisplay() {
        binding.apply {
            imgWelcomingBot.visibility = View.GONE
            tvWelcomingMessage.visibility = View.GONE
            rvUsers.visibility = View.VISIBLE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.action_menu, menu)

        actionSearch(menu)

        return true
    }

    private fun actionSearch(menu: Menu) {
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.action_search).actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String): Boolean {
                getListUsers(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                getListUsers(newText)
                return true
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_change_language -> {
                val mIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
                startActivity(mIntent)
                return true
            }
            R.id.action_favorite -> {
                val i = Intent(this, FavoriteActivity::class.java)
                startActivity(i)
                return true
            }
            R.id.action_change_settings -> {
                val i = Intent(this, SettingsActivity::class.java)
                startActivity(i)
                return true
            }
            else -> return true
        }
    }

    private fun getListUsers(query: String) {
        userViewModel.setData(query)
        lifecycleScope.launch(Dispatchers.Default) {
            delay(2000)
            withContext(Dispatchers.Main) {
                displayError(userViewModel.isNotFound)
            }
        }

        binding.apply {
            if (query.isNotEmpty()) {
                showLoading(true)
            }
        }
    }

    private fun displayError(queryNotFound: Boolean) {
        binding.apply {
            if (queryNotFound) {
                tvError.visibility = View.VISIBLE
            } else {
                tvError.visibility = View.GONE
            }
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