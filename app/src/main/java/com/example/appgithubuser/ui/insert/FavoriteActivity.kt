package com.example.appgithubuser.ui.insert

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appgithubuser.adapter.FavoriteAdapter
import com.example.appgithubuser.data.database.FavoriteUser
import com.example.appgithubuser.databinding.ActivityFavoriteBinding
import com.example.appgithubuser.setting.SettingPrefrence
import com.example.appgithubuser.ui.DetailUserActivity
import com.example.appgithubuser.viewModel.DetailViewModel
import com.example.appgithubuser.viewModel.ViewModelFactory

class FavoriteActivity : AppCompatActivity() {

    private val favoriteViewModel by viewModels<FavoriteViewModel>() {
        ViewModelFactory.getInstance(application)
    }
    private var _activityFavoriteBinding: ActivityFavoriteBinding? = null
    private  val binding get() = _activityFavoriteBinding
    private lateinit var adapter:FavoriteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _activityFavoriteBinding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding?.root)

     adapter = FavoriteAdapter(object : FavoriteAdapter.OnItemClickCallback{
         override fun onItemClicked(user: FavoriteUser) {
             val intent = Intent(this@FavoriteActivity, DetailUserActivity::class.java)
//             intent.putExtra(DetailUserActivity.EXTRA_ID, user)
             intent.putExtra(DetailUserActivity.USER_DATA, user.username)
             intent.putExtra(DetailUserActivity.EXTRA_AVATAR, user.avatarUrl)
             startActivity(intent)
         }
     })
        binding?.rvUsers?.layoutManager = LinearLayoutManager(this)
        binding?.rvUsers?. adapter = adapter

        favoriteViewModel.getAllFav().observe(this){
            adapter.submitList(it)
        }

//        favoriteViewModel.getFavoriteByUsername().observe(this) { users ->
//            if (!users.isNullOrEmpty()) {
//                binding?.rvUsers?.visibility = View.VISIBLE
//                adapter.submitList(users)
//            } else {
//                binding?.rvUsers?.visibility = View.INVISIBLE
//            }
//        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _activityFavoriteBinding = null
    }

//    private fun obtainViewModel(activity: AppCompatActivity): FavoriteViewModel {
//        val factory = ViewModelFactory.getInstance(activity.application)
//        return ViewModelProvider(activity, factory).get(FavoriteViewModel::class.java)
//    }
}