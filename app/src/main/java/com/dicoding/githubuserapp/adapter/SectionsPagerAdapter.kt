package com.dicoding.githubuserapp.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dicoding.githubuserapp.ui.detail.DetailUserActivity
import com.dicoding.githubuserapp.ui.sections.SectionsPagerFragment

class SectionsPagerAdapter(activity: AppCompatActivity, bundle: Bundle) :
    FragmentStateAdapter(activity) {

    private var data: Bundle = bundle

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        val username = this.data.getString(DetailUserActivity.KEY_USER).toString()
        return SectionsPagerFragment.newInstance(position, username)
    }

}