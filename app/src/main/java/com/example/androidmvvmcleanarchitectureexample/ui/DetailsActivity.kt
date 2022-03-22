package com.example.androidmvvmcleanarchitectureexample.ui

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.ORIENTATION_HORIZONTAL
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.example.androidmvvmcleanarchitectureexample.R
import com.example.androidmvvmcleanarchitectureexample.adapters.PagerAdapter
import com.example.androidmvvmcleanarchitectureexample.ui.fragments.recipesdetail.ingredients.IngredientsFragment
import com.example.androidmvvmcleanarchitectureexample.ui.fragments.recipesdetail.instructions.InstructionsFragment
import com.example.androidmvvmcleanarchitectureexample.ui.fragments.recipesdetail.overview.OverviewFragment
import com.example.androidmvvmcleanarchitectureexample.util.Bundles.RECIPE_RESULT_KEY
import com.example.common.utils.extentions.applyScaleTransformer
import com.example.common.utils.extentions.put
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_details.*


class DetailsActivity : AppCompatActivity() {

    private val args by navArgs<DetailsActivityArgs>()

    private lateinit var callback: ViewPager2.OnPageChangeCallback

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        initToolbar()
        initViewPagerAdapter()
        initTabLayout()

    }

    private fun initToolbar() {
        setSupportActionBar(toolbar)
        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.white))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initViewPagerAdapter() {
        callback = object : OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                Log.d("myTag", "onPageSelected@Selected_Page | position => $position")
            }
        }

        val fragments = ArrayList<Fragment>()
        fragments.add(OverviewFragment())
        fragments.add(IngredientsFragment())
        fragments.add(InstructionsFragment())

        val resultBundle = Bundle()
//        resultBundle.putParcelable(RECIPE_RESULT_KEY, args.result)
        resultBundle.put(RECIPE_RESULT_KEY, args.result)
        val adapter = PagerAdapter(this, resultBundle, fragments)

        viewPager.apply {
            this.adapter = adapter

            orientation = ORIENTATION_HORIZONTAL
            isUserInputEnabled = true

            addItemDecoration(DividerItemDecoration(applicationContext, ORIENTATION_HORIZONTAL))

            applyScaleTransformer()
            registerOnPageChangeCallback(callback)

        }

        viewPager.setCurrentItem(0, false)

    }

    private fun initTabLayout() {
        val tabTitles = ArrayList<String>()
        tabTitles.add("Overview")
        tabTitles.add("Ingredients")
        tabTitles.add("Instructions")

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = tabTitles[position]
        }.attach()
    }

    override fun onDestroy() {
        super.onDestroy()
        if(::callback.isInitialized){
            viewPager.unregisterOnPageChangeCallback(callback)
        }
    }


}