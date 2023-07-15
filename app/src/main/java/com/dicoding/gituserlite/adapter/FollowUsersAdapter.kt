package com.dicoding.gituserlite.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.gituserlite.databinding.ItemUserRowBinding
import com.dicoding.gituserlite.ui.view.DetailActivity

class FollowUsersAdapter(
    private val listFollow: ArrayList<String>
): RecyclerView.Adapter<FollowUsersAdapter.ListViewHolder>() {

    class ListViewHolder(private val binding: ItemUserRowBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: String) {
            val arrayUser = item.split(";")
            Glide.with(binding.root)
                .load(arrayUser[0])
                .circleCrop()
                .into(binding.imgItem)
            binding.tvItem.text = arrayUser[1]
            binding.root.setOnClickListener{
                val intent = Intent(binding.root.context, DetailActivity::class.java)
                intent.putExtra(DetailActivity.EXTRA_USERNAME, arrayUser[1])
                binding.root.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemUserRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun getItemCount(): Int = listFollow.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listFollow[position])
    }
}