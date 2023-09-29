package com.example.appgithubuser.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appgithubuser.data.response.ItemsItem
import com.example.appgithubuser.databinding.FragmentPagerBinding
import com.example.appgithubuser.viewModel.FollowersViewModel
import com.example.appgithubuser.viewModel.FollowingViewModel

class FollowersFragment : Fragment() {

    private lateinit var followersViewModel: FollowersViewModel
    private lateinit var followingViewModel: FollowingViewModel

    private lateinit var binding: FragmentPagerBinding
    private val adapter = UserAdapter()




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPagerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        followersViewModel = ViewModelProvider(this).get(FollowersViewModel::class.java)

        val position = arguments?.getInt(POSITION) ?: 0

        if (position == 1) {
            followersViewModel.getFollowers(DetailUserActivity.username)
            followersViewModel.followers.observe(viewLifecycleOwner) { followers ->
                setFollowersViewModel(followers)
            }
            showRecyclerView()
            followersViewModel.isLoading.observe(viewLifecycleOwner) {
                showLoading(it)
            }
        } else {
            followingViewModel = ViewModelProvider(this).get((FollowingViewModel::class.java))
            followingViewModel.getFollowing(DetailUserActivity.username)
            followingViewModel.following.observe(viewLifecycleOwner) { following ->
                setFollowingViewModel(following)
            }
            showRecyclerView()
            followingViewModel.isLoading.observe(viewLifecycleOwner) {
                showLoading(it)
            }
        }

    }

    private fun setFollowersViewModel(followers: List<ItemsItem>){
        if (followers.isNotEmpty()) {
            binding.rvUsers.visibility = View.VISIBLE
            adapter.submitList(followers)
            binding.rvUsers.adapter = adapter
        }
    }

    private fun setFollowingViewModel(following: List<ItemsItem>){
        if (following.isNotEmpty()){
            binding.rvUsers.visibility = View.VISIBLE
            adapter.submitList(following)
            binding.rvUsers.adapter = adapter
        }
    }

    private fun showRecyclerView() {
        val layoutManager = LinearLayoutManager(requireActivity())
        binding.rvUsers.layoutManager = layoutManager

        adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallBack {
            override fun onItemClicked(data: ItemsItem) {
                selectUser(data)
            }
        })
    }

    private fun selectUser(user : ItemsItem){
        val intentDetail = Intent(requireActivity(), DetailUserActivity::class.java)
        intentDetail.putExtra("user_data", user.login)
        startActivity(intentDetail)
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        const val POSITION = "position"
        const val USERNAME = "username"

    }
}