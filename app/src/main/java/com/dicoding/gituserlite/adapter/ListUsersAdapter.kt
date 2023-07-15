package com.dicoding.gituserlite.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.gituserlite.ui.view.DetailActivity
import com.dicoding.gituserlite.data.remote.response.ItemsItem
import com.dicoding.gituserlite.databinding.ItemUserRowBinding

class ListUsersAdapter(
    private val listUsers: List<ItemsItem>
) : RecyclerView.Adapter<ListUsersAdapter.ListViewHolder>() {

    class ListViewHolder(private val binding: ItemUserRowBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(item: ItemsItem){
            binding.tvItem.text = item.login
            Glide.with(binding.root)
                .load(item.avatarUrl)
                .circleCrop()
                .into(binding.imgItem)
            binding.root.setOnClickListener{
                val intent = Intent(binding.root.context, DetailActivity::class.java)
                intent.putExtra(DetailActivity.EXTRA_USERNAME, item.login)
                binding.root.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemUserRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun getItemCount(): Int = listUsers.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listUsers[position])
    }
}
