package com.example.androidmvvmcleanarchitectureexample.ui.fragments.recipes

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidmvvmcleanarchitectureexample.R
import com.example.androidmvvmcleanarchitectureexample.adapters.RecipesAdapter
import com.example.androidmvvmcleanarchitectureexample.databinding.FragmentRecipesBinding
import com.example.androidmvvmcleanarchitectureexample.viewmodels.MainViewModel
import com.example.androidmvvmcleanarchitectureexample.viewmodels.RecipesViewModel
import com.example.common.utils.extentions.observeOnce
import com.example.core.view.BaseMvvmFragment
import com.example.data.base.commonimpl.NetworkStatusListenerHelper
import com.example.data.base.models.DataWrapper
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class RecipesFragment : BaseMvvmFragment<FragmentRecipesBinding, MainViewModel>(
    R.layout.fragment_recipes,MainViewModel::class
), SearchView.OnQueryTextListener {

    private val args by navArgs<RecipesFragmentArgs>()

    private lateinit var recipesViewModel: RecipesViewModel
    private val mAdapter by lazy { RecipesAdapter() }

    @Inject
    lateinit var networkStatusListenerHelper: NetworkStatusListenerHelper


    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recipesViewModel = ViewModelProvider(requireActivity())[RecipesViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        initObservers()
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Inflate the layout for this fragment // => binding.lifecycleOwner = this
        binding.viewModel = viewModel

        setHasOptionsMenu(true)

        setupRecyclerView()
        initNetworkListener()
        binding.recipesFab.setOnClickListener {
            if (recipesViewModel.networkStatus) {
                val bundle = Bundle()
                bundle.putAll(arguments)
                findNavController().navigate(R.id.recipesBottomSheet, bundle)
            } else {
                recipesViewModel.showNetworkStatus()
            }
        }
    }

    private fun initObservers() {
        recipesViewModel.readBackOnline.observe(viewLifecycleOwner, {
            recipesViewModel.backOnline = it
        })

        viewModel.searchedRecipesResponse.observe(viewLifecycleOwner, { response ->
            when (response) {
                is DataWrapper.Success -> {
                    hideShimmerEffect()
                    response.value.let { mAdapter.setData(it) }
                }
                is DataWrapper.Failure -> {
                    hideShimmerEffect()
                    loadDataFromCache()
                    Toast.makeText(
                        requireContext(),
                        response.message.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is DataWrapper.Loading -> {
                    showShimmerEffect()
                }
            }
        })

        viewModel.recipesResponse.observe(viewLifecycleOwner, { response ->
            when (response) {
                is DataWrapper.Success -> {
                    hideShimmerEffect()
                    response.value.let { mAdapter.setData(it) }
                }
                is DataWrapper.Failure -> {
                    hideShimmerEffect()
                    loadDataFromCache()
                    Toast.makeText(
                        requireContext(),
                        response.message.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is DataWrapper.Loading -> {
                    showShimmerEffect()
                }
            }
        })
    }


    private fun initNetworkListener() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                networkStatusListenerHelper.checkNetworkAvailability()
                    .collect { status ->
                        Log.d("myTag", "network status in Receipt $status")
                        recipesViewModel.networkStatus = status
                        recipesViewModel.showNetworkStatus()
                        readDatabase()
                    }
            }
        }
    }

    private fun setupRecyclerView() {
        binding.recyclerview.adapter = mAdapter
        binding.recyclerview.layoutManager = LinearLayoutManager(requireContext())
        showShimmerEffect()
    }

    private fun readDatabase() {
        lifecycleScope.launch {
            viewModel.readRecipes.observeOnce(viewLifecycleOwner, { database ->
                if (database.isNotEmpty() && !args.backFromBottomSheet) {
                    Log.d("RecipesFragment", "readDatabase called!")
                    mAdapter.setData(database[0].foodRecipe)
                    hideShimmerEffect()
                } else {
                    requestApiData()
                }
            })
        }
    }

    private fun requestApiData() {
        viewModel.getRecipes(recipesViewModel.applyQueries())
    }

    private fun loadDataFromCache() {
        lifecycleScope.launch {
            viewModel.readRecipes.observe(viewLifecycleOwner, { database ->
                if (database.isNotEmpty()) {
                    mAdapter.setData(database[0].foodRecipe)
                }
            })
        }
    }

    // Region-start ( Search menu item view init )
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.recipes_menu, menu)

        val search = menu.findItem(R.id.menu_search)
        val searchView = search.actionView as? SearchView
        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(this)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null) {
            searchApiData(query)
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return true
    }

    private fun searchApiData(searchQuery: String) {
        showShimmerEffect()
        viewModel.searchRecipes(recipesViewModel.applySearchQuery(searchQuery))
    }

    // Region-end ( Search menu item )

    private fun showShimmerEffect() {
        binding.recyclerview.showShimmer()
    }

    private fun hideShimmerEffect() {
        binding.recyclerview.hideShimmer()
    }

}