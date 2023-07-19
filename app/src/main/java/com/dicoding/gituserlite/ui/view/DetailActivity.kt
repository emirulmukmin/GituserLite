package com.dicoding.gituserlite.ui.view

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.dicoding.gituserlite.data.remote.response.DetailUserResponse
import com.dicoding.gituserlite.R
import com.dicoding.gituserlite.adapter.SectionsPagerAdapter
import com.dicoding.gituserlite.data.local.entity.UserEntity
import com.dicoding.gituserlite.databinding.ActivityDetailBinding
import com.dicoding.gituserlite.ui.viewmodel.DetailViewModel
import com.dicoding.gituserlite.ui.viewmodel.ViewModelFactory
import com.dicoding.gituserlite.utils.EspressoIdlingResource
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity(), View.OnClickListener {

    companion object {
        const val EXTRA_USERNAME = "user"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }

    private lateinit var binding: ActivityDetailBinding
    private val detailViewModel by viewModels<DetailViewModel> {
        ViewModelFactory.getInstance(application)
    }
    private var detailUsername: UserEntity? = null
    private var user: String? = null
    private var avatarUrl: String? = null
    private var isFavorite: Boolean? = false

    override fun onStart() {
        super.onStart()
        EspressoIdlingResource.increment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        user = intent.getStringExtra(EXTRA_USERNAME) as String
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val username = intent.getStringExtra(EXTRA_USERNAME).toString()

        if (savedInstanceState == null){
            detailViewModel.getUser(username)
        }

        detailViewModel.detailUsername.observe(this){user ->
            onDetailUserReceived(user)
        }

        detailViewModel.isFavoriteUser(username).observe(this) { state ->
            isFavoriteUser(state)
            isFavorite = state
        }

        val sectionsPagerAdapter = SectionsPagerAdapter(this, username)
        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding.tabs
        TabLayoutMediator(tabs, viewPager){tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        detailViewModel.isLoading.observe(this){ showLoading(it) }

        detailViewModel.isError.observe(this){if(it) {
            showToast(this, "Failed to Load API")
        } }

        binding.fabAdd.setOnClickListener(this)
    }

    private fun onDetailUserReceived(user: DetailUserResponse){
        user.let{ username ->
            setUserDetailData(username)

            val userEntity = UserEntity(
                username.login,
                username.avatarUrl,
                true
            )
            detailUsername = userEntity
            avatarUrl = username.htmlUrl
        }
        showLoading(false)
        EspressoIdlingResource.decrement()
    }

    private fun setUserDetailData (username: DetailUserResponse) {
        binding.apply {
            binding.tvUsername.text = username.login
            binding.tvNickname.text = username.name
            Glide.with(binding.root)
                .load(username.avatarUrl)
                .circleCrop()
                .into(binding.imgItem)
            binding.tvFollower.text = buildString {
                append(username.followers)
                append(" Followers")
            }
            binding.tvFollowing.text = buildString {
                append(username.following)
                append(" Following")
            }
        }
    }

    private fun isFavoriteUser(favorite: Boolean) {
        if (favorite) {
            binding.fabAdd.setImageResource(R.drawable.baseline_favorite_24)
        } else {
            binding.fabAdd.setImageResource(R.drawable.baseline_favorite_border_24)
        }
    }

    private fun showLoading(isLoading: Boolean) { binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE }

    private fun showToast(context: Context, message: String?) {
        Toast.makeText(context, "$message", Toast.LENGTH_SHORT).show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onClick(v: View) {
        when(v.id) {
            R.id.fab_add -> {
                if (isFavorite == true) {
                    detailUsername?.let { detailViewModel.deleteFavorite(it) }
                    isFavoriteUser(false)
                    showToast(this, "User Deleted from Favorite")
                } else {
                    detailUsername?.let { detailViewModel.insertFavorite(it) }
                    isFavoriteUser(true)
                    showToast(this, "User Added to Favorite")
                    Log.d("tes", "harusnya ini cuma berubah di experimental branch")
                }
            }
        }
    }
}