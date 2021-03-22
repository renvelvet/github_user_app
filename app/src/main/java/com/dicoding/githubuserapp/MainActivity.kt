package com.dicoding.githubuserapp

import android.content.res.TypedArray
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.githubuserapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var username: Array<String>
    private lateinit var name: Array<String>
    private lateinit var company: Array<String>
    private lateinit var location: Array<String>
    private lateinit var repository: Array<String>
    private lateinit var following: Array<String>
    private lateinit var followers: Array<String>
    private lateinit var avatar: TypedArray

    private lateinit var userAdapter: UserAdapter

    private var list: ArrayList<User> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvUsers.setHasFixedSize(true)

        prepare()
        list.addAll(addItem())
        showRecyclerList()

    }

    private fun showRecyclerList() {
        binding.rvUsers.layoutManager = LinearLayoutManager(this)
        userAdapter = UserAdapter(list)
        binding.rvUsers.adapter = userAdapter
    }

    private fun addItem(): ArrayList<User> {
        val users = arrayListOf<User>()
        for (position in name.indices) {
            val user = User(
                username[position],
                name[position],
                avatar.getResourceId(position, -1),
                company[position],
                location[position],
                repository[position],
                followers[position],
                following[position]
            )
            users.add(user)
        }
        return users
    }

    private fun prepare() {
        username = resources.getStringArray(R.array.username)
        name = resources.getStringArray(R.array.name)
        avatar = resources.obtainTypedArray(R.array.avatar)
        company = resources.getStringArray(R.array.company)
        location = resources.getStringArray(R.array.location)
        repository = resources.getStringArray(R.array.repository)
        followers = resources.getStringArray(R.array.followers)
        following = resources.getStringArray(R.array.following)
    }
}