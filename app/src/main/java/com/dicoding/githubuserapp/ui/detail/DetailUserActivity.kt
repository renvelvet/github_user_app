package com.dicoding.githubuserapp.ui.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.dicoding.githubuserapp.R
import com.dicoding.githubuserapp.adapter.SectionsPagerAdapter
import com.dicoding.githubuserapp.databinding.ActivityDetailUserBinding
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailUserActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityDetailUserBinding
    private lateinit var detailUserViewModel: DetailUserViewModel
    private lateinit var username: String
    private lateinit var bundle: Bundle

    companion object {
        const val KEY_USER = "key_user"
        const val KEY_ID = "key_id"
        const val KEY_AVA = "key_ava"

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
        val id = intent.getIntExtra(KEY_ID, 0)
        val ava = intent.getStringExtra(KEY_AVA)
        bundle = Bundle()
        bundle.putString(KEY_USER, username)

        detailUserViewModel = ViewModelProvider(this).get(
            DetailUserViewModel::class.java
        )
        detailUserViewModel.setData(username)
        showLoading(true)
        detailUserViewModel.getDetailUser().observe(this, {
            if (it != null) {
                binding.apply {
                    tvName.text = it.name
                    tvUsername.text = resources.getString(R.string.username, it.username)
                    if (it.location == null) {
                        tvLocation.text = resources.getString(R.string.text_null)
                    } else {
                        tvLocation.text = resources.getString(R.string.location, it.location)
                    }
                    if (it.company == null) {
                        tvCompany.text = resources.getString(R.string.text_null)
                    } else {
                        tvCompany.text = resources.getString(R.string.company, it.company)
                    }
                    tvRepository.text = resources.getQuantityString(
                        R.plurals.repository,
                        it.repository,
                        it.repository
                    )
                    tvRepository.setOnClickListener(this@DetailUserActivity)

                    tvLinkUser.text = resources.getString(R.string.link_user, it.username)
                    imgLinkUser.setOnClickListener(this@DetailUserActivity)
                    btnShare.setOnClickListener(this@DetailUserActivity)

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

        var statusFavorite = false
        CoroutineScope(Dispatchers.IO).launch {
            val found = detailUserViewModel.checkUser(id)
            withContext(Dispatchers.Main) {
                statusFavorite = found > 0
                setStatusFavorite(statusFavorite)
            }
        }

        binding.fabFavorite.setOnClickListener {
            statusFavorite = !statusFavorite
            if (statusFavorite) {
                detailUserViewModel.addUser(username, id, ava)
                Toast.makeText(
                    this@DetailUserActivity,
                    R.string.add_to_favorite,
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                detailUserViewModel.deleteUser(username, id, ava)
            }

            setStatusFavorite(statusFavorite)
        }

        supportActionBar?.elevation = 0f
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.subtitle = username
    }

    private fun setStatusFavorite(statusFavorite: Boolean) {
        binding.apply {
            if (statusFavorite) {
                fabFavorite.setImageResource(R.drawable.ic_on_favorite_24)
            } else {
                fabFavorite.setImageResource(R.drawable.ic_off_favorite_24)
            }
        }
    }

    override fun onClick(v: View) {
        val linkIntent = Intent(Intent.ACTION_VIEW)
        val url = "https://github.com"

        when (v.id) {
            R.id.img_link_user -> {
                linkIntent.data = Uri.parse("$url/$username")
                startActivity(linkIntent)
            }
            R.id.tv_repository -> {
                linkIntent.data = Uri.parse("$url/$username?tab=repositories")
                startActivity(linkIntent)
            }
            R.id.btn_share -> {
                val tweet = "Meet @$username, my buddy from Github!"
                val tweetUrl = ("https://twitter.com/intent/tweet?text=$tweet &url=")
                val uri: Uri = Uri.parse(tweetUrl)
                startActivity(Intent(Intent.ACTION_VIEW, uri))
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