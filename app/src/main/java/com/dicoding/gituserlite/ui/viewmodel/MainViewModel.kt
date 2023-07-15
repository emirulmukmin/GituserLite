package com.dicoding.gituserlite.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.gituserlite.data.remote.response.GithubResponse
import com.dicoding.gituserlite.data.remote.response.ItemsItem
import com.dicoding.gituserlite.data.remote.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {
    private val _items = MutableLiveData<List<ItemsItem>>()
    val items : LiveData<List<ItemsItem>> = _items

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    private val _isError = MutableLiveData<Boolean>()
    val isError: LiveData<Boolean> = _isError

    private val _followers = MutableLiveData<List<ItemsItem>>()
    val followers: LiveData<List<ItemsItem>> = _followers

    private val _following = MutableLiveData<List<ItemsItem>>()
    val following: LiveData<List<ItemsItem>> = _following

    var query: String? = null

    companion object {
        private const val GITHUBUSER_ID = "arif"
    }

    init {
        getListUsers(GITHUBUSER_ID)
    }

    internal fun getListUsers(query: String) {
        _isLoading.value = true
        _isError.value = false
        val client = ApiConfig.getApiService().getUsers(query)
        client.enqueue(object : Callback<GithubResponse> {
            override fun onResponse(
                call: Call<GithubResponse>,
                response: Response<GithubResponse>
            ) {
                _isLoading.value = false
                val responseBody = response.body()
                if(response.isSuccessful){
                    _items.value = responseBody?.items
                }else{
                    _isError.value = true
                }
            }

            override fun onFailure(call: Call<GithubResponse>, t: Throwable) {
                _isLoading.value = false
                _isError.value = true
            }
        })
    }

    internal fun getFollowers(username: String){
        _isLoading.value = true
        _isError.value = false
        val client = ApiConfig.getApiService().getFollowers(username)
        client.enqueue(object  : Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful){
                    _followers.value = response.body()
                }else{
                    _isError.value = true
                }
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                _isLoading.value = false
                _isError.value = true
            }

        })
    }

    internal fun getFollowing(username: String){
        _isLoading.value = true
        _isError.value = false
        val client = ApiConfig.getApiService().getFollowing(username)
        client.enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _following.value = response.body()
                } else {
                    _isError.value = true
                }
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                _isLoading.value = false
                _isError.value = true
            }

        })
    }
}