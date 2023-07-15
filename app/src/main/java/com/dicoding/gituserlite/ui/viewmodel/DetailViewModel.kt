package com.dicoding.gituserlite.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.gituserlite.data.UserRepository
import com.dicoding.gituserlite.data.local.entity.UserEntity
import com.dicoding.gituserlite.data.remote.response.DetailUserResponse
import com.dicoding.gituserlite.data.remote.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _detailUsername = MutableLiveData<DetailUserResponse>()
    val detailUsername : LiveData<DetailUserResponse> = _detailUsername

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    private val _isError = MutableLiveData<Boolean>()
    val isError: LiveData<Boolean> = _isError

    internal  fun getUser(username: String) {
        _isLoading.value = true
        _isError.value = false
        val client = ApiConfig.getApiService().getUserDetail(username)
        client.enqueue(object : Callback<DetailUserResponse> {
            override fun onResponse(
                call: Call<DetailUserResponse>,
                response: Response<DetailUserResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful){
                    _detailUsername.value = response.body()
                }else{
                    _isError.value = true
                }
            }

            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                _isLoading.value = false
                _isError.value = true
            }
        })
    }

    fun getFavoriteUsers() = userRepository.getAllFavorite()

    internal fun insertFavorite(favorite: UserEntity) {
        userRepository.insertFavorite(favorite)
    }

    internal fun deleteFavorite(favorite: UserEntity) {
        userRepository.deleteFavorite(favorite)
    }

    fun isFavoriteUser(username: String): LiveData<Boolean> = userRepository.isFavoriteUser(username)
}