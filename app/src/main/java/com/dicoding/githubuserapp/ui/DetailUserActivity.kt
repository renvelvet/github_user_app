package com.dicoding.githubuserapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.dicoding.githubuserapp.R
import com.dicoding.githubuserapp.adapter.SectionsPagerAdapter
import com.dicoding.githubuserapp.databinding.ActivityDetailUserBinding
import com.dicoding.githubuserapp.viewModel.DetailUserViewModel
import com.google.android.material.tabs.TabLayoutMediator

class DetailUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailUserBinding
    private lateinit var detailUserViewModel: DetailUserViewModel
    private lateinit var username: String
    private lateinit var bundle: Bundle

    companion object {
        const val KEY_USER = "key_user"
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        username = intent.getStringExtra(KEY_USER).toString()
        bundle = Bundle()
        bundle.putString(KEY_USER, username)

        detailUserViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(DetailUserViewModel::class.java)

        detailUserViewModel.setData(username)
        detailUserViewModel.getDetailUser().observe(this, {
            if (it != null) {
                binding.apply {
                    tvName.text = it.name
                    tvUsername.text = it.username
                    if (it.location == null) {
                        tvLocation.text = resources.getString(R.string.text_null)
                    } else {
                        tvLocation.text = it.location
                    }
                    if (it.company == null) {
                        tvCompany.text = resources.getString(R.string.text_null)
                    } else {
                        tvCompany.text = it.company
                    }
                    tvNumberFollowers.text = "${it.followers}"
                    tvNumberFollowing.text = "${it.following}"
                    tvNumberRepository.text = "${it.repository}"

                    Glide.with(this@DetailUserActivity)
                        .load(it.avatar)
                        .transform(CenterCrop())
                        .into(civAvatar)
                }
            }
        })

        val sectionsPagerAdapter = SectionsPagerAdapter(this, bundle)
        binding.apply {
            viewPager.adapter = sectionsPagerAdapter
            TabLayoutMediator(tabs, viewPager) { tab, position ->
                tab.text = resources.getString(TAB_TITLES[position])
            }.attach()
        }

        supportActionBar?.elevation = 0f
    }

//    override fun onClick(v: View?) {
//        val name = detailUser.name
//        val text1 = "Hi,"
//        val text2 = "I found your github p rofile quite interesting. Let's have some chit chat!"
//        val tweetUrl = ("https://twitter.com/intent/tweet?text=$text1&url="
//                + "$name! $text2")
//        val uri: Uri = Uri.parse(tweetUrl)
//        startActivity(Intent(Intent.ACTION_VIEW, uri))
//    }
}