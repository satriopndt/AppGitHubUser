package com.example.appgithubuser.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.appgithubuser.data.response.ItemsItem
import com.example.appgithubuser.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class FollowersViewModel : ViewModel() {
    private val _followers = MutableLiveData<List<ItemsItem>>()
    val followers: LiveData<List<ItemsItem>> = _followers

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getFollowers(username: String) {
        try {
            _isLoading.value = true
            val client = ApiConfig.getApiService().getFollowers(username)
            client.enqueue(object : Callback<List<ItemsItem>> {
                override fun onResponse(
                    call: Call<List<ItemsItem>>,
                    response: Response<List<ItemsItem>>
                ) {
                    _isLoading.value = false
                    if (response.isSuccessful) {
                        _followers.value = response.body()
                    } else {
                        Log.e("FollowersViewModel", "onFailure: ${response.message()} ")
                    }
                }

                override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                    _isLoading.value = false
                    Log.e("FollowersViewModel", "onFailure: ${t.message}")
                }

            })
        } catch (e: Exception) {
            Log.d("FollowersViewModel", e.toString())
        }
    }

}
