package com.dicoding.gituserlite.di

import android.content.Context
import com.dicoding.gituserlite.data.SettingPreferences
import com.dicoding.gituserlite.data.UserRepository
import com.dicoding.gituserlite.data.local.room.FavoriteDatabase
import com.dicoding.gituserlite.data.remote.retrofit.ApiConfig

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val apiService = ApiConfig.getApiService()
        val database = FavoriteDatabase.getInstance(context)
        val dao = database.userDao()
        val preferences = SettingPreferences.getInstance(context)
        return UserRepository.getInstance(apiService, dao, preferences)
    }
}