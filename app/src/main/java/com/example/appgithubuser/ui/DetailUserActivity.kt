package com.example.appgithubuser.ui

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.appgithubuser.R
import com.example.appgithubuser.adapter.SectionsPagerAdapter
import com.example.appgithubuser.data.database.FavoriteUser
import com.example.appgithubuser.data.response.DetailUserResponse
import com.example.appgithubuser.databinding.ActivityDetailUserBinding
import com.example.appgithubuser.setting.SettingPrefrence
import com.example.appgithubuser.setting.dataStore
import com.example.appgithubuser.ui.insert.FavoriteViewModel
import com.example.appgithubuser.viewModel.DetailViewModel
import com.example.appgithubuser.viewModel.ViewModelFactory
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailUserBinding
    private val detailViewModel by viewModels<DetailViewModel>() {
        ViewModelFactory.getInstance(application)
    }
    private val favoriteViewModel: FavoriteViewModel by viewModels()

    private lateinit var avatar: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_user)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        username = intent.getStringExtra(USER_DATA).toString()
        detailViewModel.getSelectUser(username)

        avatar = intent.getStringExtra(EXTRA_AVATAR).toString()

        detailViewModel.selectedUser.observe(this) { items ->
            setDetailUser(items)
        }
        detailViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        val userFav = FavoriteUser(
            username = username,
            following = binding.detailFollowing.text.toString(),
            follower = binding.detailFollowers.text.toString(),
            avatarUrl = avatar,
            nameId = binding.tvItem.text.toString(),
            getFavorite = true
        )
        binding.fabFav.setOnClickListener{
            val currentIcon = binding.fabFav.contentDescription
            if (currentIcon.equals("true")){
                favIcon(false)
                detailViewModel.deleteFav(userFav)
                binding.fabFav.contentDescription = "false"
            }else{
                favIcon(true)
                detailViewModel.insertFav(userFav)
                binding.fabFav.contentDescription = "true"
            }
        }


    }

    private fun favIcon(isFavorite: Boolean) {
        binding.fabFav.setImageDrawable(
            ContextCompat.getDrawable(
                applicationContext,
                if (isFavorite) {
                    R.drawable.ic_favorite
                } else {
                    R.drawable.baseline_favorite_border_24
                }
            )
        )
    }


    private fun setDetailUser(items: FavoriteUser) {
        binding.apply {
            Glide.with(this@DetailUserActivity)
                .load(items.avatarUrl)
                .into(image)
            tvItem.text = items.nameId
            nameUser.text = items.username
            detailFollowers.text =
                resources.getString(R.string.followers, items.follower.toString())
            detailFollowing.text =
                resources.getString(R.string.following, items.following.toString())

            fabFav.contentDescription = items.getFavorite.toString()
            favIcon(items.getFavorite)
        }
        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        sectionsPagerAdapter.username = username
        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding.foll
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        supportActionBar?.elevation = 0f

    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {

        const val EXTRA_AVATAR = "user_avatar"
        const val USER_DATA = "user_data"
        var username = String()

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_followers,
            R.string.tab_following
        )

    }


}