package com.example.appgithubuser.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.appgithubuser.data.database.DaoGitHub
import com.example.appgithubuser.data.database.FavoriteUser
import com.example.appgithubuser.data.database.GhRoomDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteRepo(application: Application) {
    private val mDaoGitHub: DaoGitHub
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = GhRoomDatabase.getDatabase(application)
        mDaoGitHub = db.daoGithub()
    }

    fun getAllFav(): LiveData<List<FavoriteUser>> = mDaoGitHub.getAllFav()


    fun getFavorite(username: String): Boolean {
        return mDaoGitHub.getFavorite(username)
    }

    fun insert(favoriteUser: FavoriteUser){
        executorService.execute { mDaoGitHub.insert(favoriteUser) }
    }

    fun delete(favoriteUser: FavoriteUser){
        executorService.execute { mDaoGitHub.delete(favoriteUser) }
    }


    companion object{
        var username = "KEY_DATA"

        @Volatile
        private var instance: FavoriteRepo? = null
        fun getInstance(
            application: Application
        ) : FavoriteRepo = instance ?: synchronized(this){
            instance ?: FavoriteRepo(application)
        }.also { instance = it}
    }
}
