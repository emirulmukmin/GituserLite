package com.dicoding.gituserlite.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.gituserlite.adapter.FavoriteUserAdapter
import com.dicoding.gituserlite.data.local.entity.UserEntity
import com.dicoding.gituserlite.databinding.ActivityFavoriteBinding
import com.dicoding.gituserlite.ui.viewmodel.DetailViewModel
import com.dicoding.gituserlite.ui.viewmodel.ViewModelFactory

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private val detailViewModel by viewModels<DetailViewModel> {
        ViewModelFactory.getInstance(application)
    }
    private lateinit var favoriteAdapter: FavoriteUserAdapter

    companion object {
        const val EXTRA_STATE = "extra_state"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        favoriteAdapter = FavoriteUserAdapter()

        detailViewModel.getFavoriteUsers().observe(this) {
            favoriteAdapter.submitList(it)
            binding.rvFavorite.adapter = favoriteAdapter
            binding.tvNoFavoriteUser.visibility = if (it.isEmpty()) View.VISIBLE else View.GONE
        }

        binding.rvFavorite.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = favoriteAdapter
        }

        if (savedInstanceState != null) {
            @Suppress("DEPRECATION")
            val list = savedInstanceState.getParcelableArrayList<UserEntity>(EXTRA_STATE)
            if (list != null) {
                favoriteAdapter.submitList(list)
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(EXTRA_STATE, ArrayList(favoriteAdapter.currentList))
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
