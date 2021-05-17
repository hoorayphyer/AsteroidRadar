package com.udacity.asteroidradar.main

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
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

        // Logic of click listener setup
        // 1. in list_item_asteroid.xml, there is a variable called clickListener whose onClick call is bound to the view's onClick attribute
        // 2. it is viewHolder's responsibility to bind that variable with an instance. It might be tempting to just instantiate such an instance inside the viewholder's calss, but it's not recommeneded because view holder should not maintain the logic of onClick behavior.
        // 3. for the click listener, we want navigation (which needs the current fragment to retrieve the navController), and customizing the fields with actual data (which is held in view model)
        // 4. in viewHolder, when clickListener is bound, the corresponding asteroid object is available.
        // 5. we use safe-args to pass values during navigation. This value can be set in clickListener

        val asteroidsView = AsteroidAdapter(
            AsteroidItemClickListener{
                val navAction = MainFragmentDirections.actionShowDetail(it) // this is using safe-args, see https://developer.android.com/guide/navigation/navigation-pass-data
                this.findNavController().navigate(navAction)
            }
        )
        binding.asteroidRecycler.adapter = asteroidsView

        // observation here is such that when viewModel.asteroids gets updated, the recyclerView responds with updated object
        // In this app, the viewModel.asteroids are automatically downloaded daily.
        viewModel.repo.asteroids.observe(viewLifecycleOwner, {
            it?.let{
                asteroidsView.submitList(it)
            }
        })

        viewModel.pictureOfDay.observe(viewLifecycleOwner, {
            it?.let{
                binding.titleOfImageOfTheDay.text = it.title
            }
        })

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
