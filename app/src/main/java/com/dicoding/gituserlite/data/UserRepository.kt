package com.dicoding.gituserlite.data

import androidx.lifecycle.LiveData
import com.dicoding.gituserlite.data.local.room.UserDao
import com.dicoding.gituserlite.data.local.entity.UserEntity
import com.dicoding.gituserlite.data.remote.retrofit.ApiService
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import kotlinx.coroutines.flow.Flow

class UserRepository private constructor(
    private val apiService: ApiService,
    private val userDao: UserDao,
    private val preferences: SettingPreferences
){
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    fun getAllFavorite(): LiveData<List<UserEntity>> = userDao.getAllFavorites()

    fun insertFavorite(favorite: UserEntity) {
        executorService.execute { userDao.insertFavorite(favorite) }
    }

    fun deleteFavorite(userEntity: UserEntity) {
        executorService.execute {userDao.deleteFavorite(userEntity) }
    }

    fun isFavoriteUser(username: String): LiveData<Boolean> = userDao.isFavoriteUser(username)

    fun getThemeSetting(): Flow<Boolean> = preferences.getThemeSetting()

    suspend fun saveThemeSetting(isDarkModeActive: Boolean) {
        preferences.saveThemeSetting(isDarkModeActive)
    }

    companion object {
        @Volatile
        private var instance: UserRepository? = null
        fun getInstance(
            apiService: ApiService,
            userDao: UserDao,
            preferences: SettingPreferences
        ): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(apiService, userDao, preferences)
            }.also { instance = it }
    }
}