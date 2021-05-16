package com.udacity.asteroidradar.main

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.udacity.asteroidradar.AsteroidRepository
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private val viewModel: MainViewModel by lazy {
        val application = requireNotNull(this.activity).application
        val dataSource = AsteroidDatabase.getInstance(application).dao
        val repo = AsteroidRepository(dataSource)
        val factory = MainViewModelFactory(repo)
        ViewModelProvider(this, factory).get(MainViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = FragmentMainBinding.inflate(inflater)
        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        setHasOptionsMenu(true)

        val asteroids_view = AsteroidAdapter()
        binding.asteroidRecycler.adapter = asteroids_view

        // observation here is such that when viewModel.asteroids gets updated, the recyclerView responds with updated object
        // In this app, the viewModel.asteroids are automatically downloaded daily.
        viewModel.repo.asteroids.observe(viewLifecycleOwner, {
            it?.let{
                asteroids_view.submitList(it)
            }
        })

        viewModel.pictureOfDay.observe(viewLifecycleOwner, {})

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return true
    }
}
