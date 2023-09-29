package com.example.appgithubuser.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appgithubuser.data.database.FavoriteUser
import com.example.appgithubuser.data.response.DetailUserResponse
import com.example.appgithubuser.data.retrofit.ApiConfig
import com.example.appgithubuser.repository.FavoriteRepo
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(application: Application) : ViewModel() {

    private val _selectedUser = MutableLiveData<FavoriteUser>()
    val selectedUser: LiveData<FavoriteUser> get() = _selectedUser

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val mFavoriteRepo: FavoriteRepo = FavoriteRepo(application)

    fun insertFav(favoriteUser: FavoriteUser){
        viewModelScope.launch {
            mFavoriteRepo.insert(favoriteUser)
        }
    }

    fun deleteFav(favoriteUser: FavoriteUser){
        viewModelScope.launch {
            mFavoriteRepo.delete(favoriteUser)
        }
    }

    companion object {
        private const val TAG = "com.example.appgithubuser.viewModel.DetailViewModel"
    }

    fun getSelectUser(username: String) {
        val client = ApiConfig.getApiService().getDetailUser(username)
        client.enqueue(object : Callback<DetailUserResponse> {
            override fun onResponse(
                call: Call<DetailUserResponse>,
                response: Response<DetailUserResponse>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null){
                        viewModelScope.launch {
                            val isFavorite = mFavoriteRepo.getFavorite(username)
                            val userFav = FavoriteUser(
                                username = responseBody.login,
                                avatarUrl = responseBody.avatarUrl,
                                nameId = responseBody.name,
                                follower = responseBody.followers.toString(),
                                following = responseBody.following.toString(),
                                getFavorite = isFavorite
                            )
                            _selectedUser.postValue(userFav)
                    }

                    }
                    _isLoading.value = false

                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
                _isLoading.value = false
            }

            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message}")
                _isLoading.value = false
            }
        })
    }


}
