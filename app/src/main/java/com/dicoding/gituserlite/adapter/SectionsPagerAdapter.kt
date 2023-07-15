package com.dicoding.gituserlite.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dicoding.gituserlite.ui.view.DetailFollowFragment

class SectionsPagerAdapter(activity: AppCompatActivity, private var username: String): FragmentStateAdapter(activity) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        val fragment = DetailFollowFragment()
        fragment.arguments = Bundle().apply {
            putInt(DetailFollowFragment.ARG_POSITION, position+1)
            putString(DetailFollowFragment.ARG_USERNAME, username)
        }
        return fragment
    }
}