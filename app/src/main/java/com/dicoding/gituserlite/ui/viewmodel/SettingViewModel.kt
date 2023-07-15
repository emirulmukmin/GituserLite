package com.dicoding.gituserlite.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.gituserlite.data.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class SettingViewModel(private val userRepository: UserRepository) : ViewModel() {
    val getThemeSetting: Flow<Boolean> = userRepository.getThemeSetting()

    fun saveThemeSetting(darkModeState: Boolean) {
        viewModelScope.launch {
            userRepository.saveThemeSetting(darkModeState)
        }
    }
}