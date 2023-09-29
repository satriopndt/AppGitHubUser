package com.example.appgithubuser.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.appgithubuser.data.database.FavoriteUser
import com.example.appgithubuser.databinding.ItemUserBinding

class FavoriteAdapter(private val onItemClickCallback: OnItemClickCallback): ListAdapter<FavoriteUser, FavoriteAdapter.FavoriteViewHolder>(
    FavoriteUserDiffCallback()
) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavoriteViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)
    }

    inner class FavoriteViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: FavoriteUser) {
            binding.apply {
                tvItem.text = user.username
                Glide.with(itemView.context)
                    .load(user.avatarUrl)
                    .into(binding.image)
            }
        }

        init {
                itemView.setOnClickListener{
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION){
                        val user = getItem(position)
                        onItemClickCallback.onItemClicked(user)
                    }
                }
            }
        }

    interface OnItemClickCallback {
        fun onItemClicked(user: FavoriteUser)
    }

    private class FavoriteUserDiffCallback : DiffUtil.ItemCallback<FavoriteUser>() {
        override fun areItemsTheSame(oldItem: FavoriteUser, newItem: FavoriteUser): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: FavoriteUser, newItem: FavoriteUser): Boolean {
            return oldItem == newItem
        }
    }
}