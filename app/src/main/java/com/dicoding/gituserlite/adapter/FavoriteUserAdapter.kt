package com.dicoding.gituserlite.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.gituserlite.data.local.entity.UserEntity
import com.dicoding.gituserlite.databinding.ItemUserRowBinding
import com.dicoding.gituserlite.ui.view.DetailActivity

class FavoriteUserAdapter : ListAdapter<UserEntity, FavoriteUserAdapter.FavoriteViewHolder>(DIFF_CALLBACK) {

    class FavoriteViewHolder(private val binding: ItemUserRowBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(users : UserEntity){
            binding.apply {
                Glide.with(itemView)
                    .load(users.avatarUrl)
                    .circleCrop()
                    .into(imgItem)
                tvItem.text = users.username
                binding.root.setOnClickListener{
                    val intent = Intent(binding.root.context, DetailActivity::class.java)
                    intent.putExtra(DetailActivity.EXTRA_USERNAME, users.username)
                    binding.root.context.startActivity(intent)
                }
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): FavoriteViewHolder {
        val view =  ItemUserRowBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return FavoriteViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val users = getItem(position)
        holder.bind(users)
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<UserEntity> =
            object : DiffUtil.ItemCallback<UserEntity>() {
                override fun areItemsTheSame(oldUser: UserEntity, newUser: UserEntity): Boolean {
                    return oldUser.username == newUser.username
                }

                @SuppressLint("DiffUtilEquals")
                override fun areContentsTheSame(oldUser: UserEntity, newUser: UserEntity): Boolean {
                    return oldUser == newUser
                }
            }
    }
}