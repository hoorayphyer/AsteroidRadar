package com.udacity.asteroidradar.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.databinding.ListItemAsteroidBinding

class AsteroidAdapter(private val clickListener: AsteroidItemClickListener) :
    ListAdapter<Asteroid, AsteroidAdapter.ViewHolder>(DiffCallback()) {

    class ViewHolder private constructor(val binding: ListItemAsteroidBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Asteroid, clickListener: AsteroidItemClickListener) {
            binding.asteroidCodename.apply {
                text = item.codename
                contentDescription = item.codename
            }
            binding.asteroidCloseApproachDate.apply {
                text = item.closeApproachDate
                contentDescription = item.closeApproachDate
            }
            binding.asteroid = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = ListItemAsteroidBinding.inflate(inflater, parent, false)
                return ViewHolder(binding)
            }
        }

    }

    class DiffCallback : DiffUtil.ItemCallback<Asteroid>() {
        override fun areItemsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, clickListener)
    }
}

class AsteroidItemClickListener(val callback: (asteroid: Asteroid) -> Unit) {
    fun onClick(asteroid: Asteroid) = callback(asteroid)
}