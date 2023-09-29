package com.example.appgithubuser.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appgithubuser.R
import com.example.appgithubuser.data.response.ItemsItem
import com.example.appgithubuser.databinding.ActivityMainBinding
import com.example.appgithubuser.setting.SettingActivity
import com.example.appgithubuser.setting.SettingPrefrence
import com.example.appgithubuser.setting.SettingViewModel
import com.example.appgithubuser.setting.dataStore
import com.example.appgithubuser.ui.insert.FavoriteActivity
import com.example.appgithubuser.viewModel.ViewModelFactory
import com.google.android.material.switchmaterial.SwitchMaterial
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel by viewModels<MainViewModel>()


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item?.itemId) {
            R.id.setting_page -> {
                val intent = Intent(this@MainActivity, SettingActivity::class.java)
                startActivity(intent)
                return true
            }
            R.id.favorite_page -> {
                val intent = Intent(this@MainActivity, FavoriteActivity::class.java)
                startActivity(intent)
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return super.onCreateOptionsMenu(menu)
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.show()

        with(binding){
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener{_, _, _ ->
                    searchBar.text = searchView.text
                    mainViewModel.findGitHub(searchBar.text.toString())
                    showLoading(true)
                    false
                }

        }

        val layoutManager = LinearLayoutManager(this)
        binding.rvUsers.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvUsers.addItemDecoration(itemDecoration)

        mainViewModel.listUser.observe(this) { items ->
            setGitHubData(items)
        }
        mainViewModel.isLoading.observe(this) {
            showLoading(false)
        }


    }

    private fun setGitHubData(items: List<ItemsItem>) {
        val adapter = UserAdapter()
        adapter.submitList(items)
        binding.rvUsers.adapter = adapter
        adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallBack {
            override fun onItemClicked(data: ItemsItem) {
                selectUser(data)

            }
        })
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun selectUser(user : ItemsItem){
        val intentDetail = Intent(this@MainActivity, DetailUserActivity::class.java)
        intentDetail.putExtra(DetailUserActivity.USER_DATA, user.login)
        intentDetail.putExtra(DetailUserActivity.EXTRA_AVATAR, user.avatarUrl)
        startActivity(intentDetail)
    }
}
