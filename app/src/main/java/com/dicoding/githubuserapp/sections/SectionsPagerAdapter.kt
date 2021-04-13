package com.dicoding.githubuserapp.sections

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dicoding.githubuserapp.detail.DetailUserActivity

class SectionsPagerAdapter(activity: AppCompatActivity, bundle: Bundle) : FragmentStateAdapter(activity) {

    private var data : Bundle

    init {
        data = bundle
    }
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        val username = this.data.getString(DetailUserActivity.KEY_USER).toString()
        return SectionsPagerFragment.newInstance(position, username)
//        var fragment: Fragment? = null
//        when (position) {
//            0 -> fragment = SectionsPagerFragment.newInstance(0)
//            1 -> fragment = SectionsPagerFragment.newInstance(1)
//        }
//
//        fragment?.arguments = this.data
//        return fragment as Fragment
    }

}