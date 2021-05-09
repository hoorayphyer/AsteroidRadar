package com.udacity.asteroidradar

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.databinding.ListItemAsteroidBinding

class AsteroidAdapter : ListAdapter<Asteroid, AsteroidAdapter.ViewHolder,>(AsteroidAdapter.DiffCallback()) {

    class ViewHolder private constructor( val binding : ListItemAsteroidBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Asteroid) {
            binding.asteroidSerialNumber.text = item.id.toString()
            binding.asteroidCloseApproachDate.text = item.closeApproachDate
            binding.asteroid = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup) : ViewHolder {
                val inflater =LayoutInflater.from(parent.context)
                val binding = ListItemAsteroidBinding.inflate(inflater, parent, false)
                return ViewHolder(binding)
            }
        }

    }

    class DiffCallback : DiffUtil.ItemCallback<Asteroid>() {
        override fun areItemsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
            TODO("Not yet implemented")
        }

        override fun areContentsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
            TODO("Not yet implemented")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }
}
