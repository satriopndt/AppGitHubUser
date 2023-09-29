package com.example.appgithubuser.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface DaoGitHub {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(favoriteUser: FavoriteUser)

    @Delete
    fun delete(favoriteUser: FavoriteUser)

    @Query("SELECT * from favusers ORDER BY username ASC")
    fun getAllFav(): LiveData<List<FavoriteUser>>

    @Query("SELECT EXISTS (SELECT * FROM favusers WHERE username = :username)")
    fun getFavorite(username: String): Boolean
}