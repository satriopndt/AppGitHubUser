package com.example.appgithubuser.ui.insert

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.appgithubuser.data.database.FavoriteUser
import com.example.appgithubuser.repository.FavoriteRepo

class FavoriteViewModel(application: Application): AndroidViewModel(application) {


    private val mFavoriteRepo: FavoriteRepo = FavoriteRepo(application)

    fun getAllFav(): LiveData<List<FavoriteUser>> = mFavoriteRepo.getAllFav()

//    init {
//        val ghRoomDatabase = GhRoomDatabase.getDatabase(application)!!
//        val daoGitHub = ghRoomDatabase.daoGithub()
//        mFavoriteRepo = FavoriteRepo(daoGitHub)
//    }


//    suspend fun insert(username: String, avatar: String, id: Int){
//        withContext(Dispatchers.IO){
//            mFavoriteRepo.getFavorite(username, avatar, id, true)
//        }
//    }

//    suspend fun update(id: Int): Int{
//        return withContext(Dispatchers.IO){
//            mFavoriteRepo.update(id)
//        }
//    }

//    fun delete(id: Int){
//        mFavoriteRepo.delete(id)
//    }

//    fun getFavoriteByUsername(): LiveData<List<FavoriteUser>> {
//        return mFavoriteRepo.getAllFav()
//    }
}