package com.dicoding.githubuserapp.ui.detail

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.dicoding.githubuserapp.R
import com.dicoding.githubuserapp.adapter.SectionsPagerAdapter
import com.dicoding.githubuserapp.databinding.ActivityDetailUserBinding
import com.google.android.material.tabs.TabLayoutMediator

class DetailUserActivity : AppCompatActivity(), View.OnClickListener {
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

        detailUserViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            DetailUserViewModel::class.java
        )
        showLoading(true)
        detailUserViewModel.setData(username)
        detailUserViewModel.getDetailUser().observe(this, {
            if (it != null) {
                binding.apply {
                    tvName.text = it.name
                    tvUsername.text = resources.getString(R.string.username, it.username)
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
                    tvRepository.text = resources.getQuantityString(
                        R.plurals.repository,
                        it.repository,
                        it.repository
                    )

                    tvLinkUser.text = resources.getString(R.string.link_user, it.username)
                    imgLinkUser.setOnClickListener(this@DetailUserActivity)

                    val tabValue = intArrayOf(
                        it.following,
                        it.followers
                    )

                    viewPager.adapter = SectionsPagerAdapter(this@DetailUserActivity, bundle)
                    TabLayoutMediator(tabs, viewPager) { tab, position ->
                        tab.text = resources.getString(TAB_TITLES[position], tabValue[position])
                    }.attach()

                    Glide.with(this@DetailUserActivity)
                        .load(it.avatar)
                        .transform(CenterCrop())
                        .into(civAvatar)
                }
                showLoading(false)
            }
        })

        supportActionBar?.elevation = 0f
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
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

    override fun onClick(v: View) {
        if (v.id == R.id.img_link_user) {
            val linkIntent = Intent(Intent.ACTION_VIEW)
            linkIntent.data = Uri.parse("https://github.com/${username}")
            startActivity(linkIntent)
        }
    }
}