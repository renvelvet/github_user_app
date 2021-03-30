package com.dicoding.githubuserapp

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.dicoding.githubuserapp.databinding.ActivityDetailUserBinding

class DetailUserActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityDetailUserBinding

    companion object {
        const val KEY_USER = "key_user"
    }

    private lateinit var detailUser: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        detailUser = intent.getParcelableExtra<User>(KEY_USER) as User

        binding.tvName.text = detailUser.name
        binding.tvUsername.text = detailUser.username
        binding.tvLocation.text = detailUser.location
        binding.civAvatar.setImageResource(detailUser.avatar)
        binding.tvCompany.text = detailUser.company
        binding.tvNumberFollowers.text = detailUser.followers
        binding.tvNumberFollowing.text = detailUser.following
        binding.tvNumberRepository.text = detailUser.repository

        binding.btnShare.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        val name = detailUser.name
        val text1 = "Hi,"
        val text2 = "I found your github profile quite interesting. Let's have some chit chat!"
        val tweetUrl = ("https://twitter.com/intent/tweet?text=$text1&url="
                + "$name! $text2")
        val uri: Uri = Uri.parse(tweetUrl)
        startActivity(Intent(Intent.ACTION_VIEW, uri))
    }
}