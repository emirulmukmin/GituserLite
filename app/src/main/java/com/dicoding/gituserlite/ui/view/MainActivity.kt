package com.dicoding.gituserlite.ui.view

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.gituserlite.data.remote.response.ItemsItem
import com.dicoding.gituserlite.ui.viewmodel.MainViewModel
import com.dicoding.gituserlite.R
import com.dicoding.gituserlite.adapter.ListUsersAdapter
import com.dicoding.gituserlite.databinding.ActivityMainBinding
import com.dicoding.gituserlite.utils.EspressoIdlingResource

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainViewModel.items.observe(this) {items ->
            setUsersData(items)
        }

        mainViewModel.isLoading.observe(this) { showLoading(it) }

        mainViewModel.isError.observe(this){
            if(it) {
            Toast.makeText(this, "Failed to load API", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setUsersData(items: List<ItemsItem>) {
        if(items.isNotEmpty()) {
            val adapter = ListUsersAdapter(items)
            val layoutManager = LinearLayoutManager(this)
            binding.rvUsers.layoutManager = layoutManager
            binding.rvUsers.setHasFixedSize(true)
            binding.rvUsers.adapter = adapter
        } else {
            binding.tvNotFound.visibility = View.VISIBLE
            binding.tvNotFound.text = getString(R.string.users_not_found)
        }
    }


    private fun showLoading(isLoading: Boolean) { binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE}

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(q: String): Boolean {
                if (q.isNotEmpty()) {
                    EspressoIdlingResource.increment()
                    mainViewModel.query = q
                    mainViewModel.getListUsers(q)
                    searchView.clearFocus()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.favorite_page -> {
                startActivity(Intent(this@MainActivity, FavoriteActivity::class.java))
            }
            R.id.setting_page -> {
                startActivity(Intent(this@MainActivity, SettingActivity::class.java))
            }
        }
        return super.onOptionsItemSelected(item)
    }
}