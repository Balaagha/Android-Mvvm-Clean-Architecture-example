package com.example.androidmvvmcleanarchitectureexample.ui.fragments.recipesdetail.ingredients

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidmvvmcleanarchitectureexample.R
import com.example.androidmvvmcleanarchitectureexample.adapters.IngredientsAdapter
import com.example.androidmvvmcleanarchitectureexample.util.Bundles.RECIPE_RESULT_KEY
import kotlinx.android.synthetic.main.fragment_ingredients.view.*
import com.example.androidmvvmcleanarchitectureexample.models.RecipesResult
import com.example.androidmvvmcleanarchitectureexample.util.helper.argument

class IngredientsFragment : Fragment() {

    private val mAdapter: IngredientsAdapter by lazy { IngredientsAdapter() }

    private val recipeResultBundle : RecipesResult? by argument()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_ingredients, container, false)

//        val args = arguments
//        val recipeResultBundle: RecipesResult? = args?.getParcelable(RECIPE_RESULT_KEY)


        setupRecyclerView(view)
        recipeResultBundle?.extendedIngredients?.let {
            mAdapter.setData(it)
        }
        return view
    }

    private fun setupRecyclerView(view: View) {
        view.ingredients_recyclerview.adapter = mAdapter
        view.ingredients_recyclerview.layoutManager = LinearLayoutManager(requireContext())
    }

}