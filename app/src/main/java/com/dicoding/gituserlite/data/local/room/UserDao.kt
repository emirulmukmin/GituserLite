package com.dicoding.gituserlite.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.dicoding.gituserlite.data.local.entity.UserEntity

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertFavorite(favorite: UserEntity)

    @Delete
    fun deleteFavorite(favorite: UserEntity)

    @Query("SELECT * FROM user where favorited = 1")
    fun getAllFavorites(): LiveData<List<UserEntity>>

    @Query("SELECT EXISTS(SELECT * FROM user WHERE username = :username AND favorited = 1)")
    fun isFavoriteUser(username: String): LiveData<Boolean>

}