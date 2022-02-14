package com.example.androidmvvmcleanarchitectureexample.adapters

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.adapter.FragmentStateAdapter

class PagerAdapter(
    private val root: AppCompatActivity,
    private val resultBundle: Bundle,
    private val fragments: ArrayList<Fragment>,
): FragmentStateAdapter(root) {

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        fragments[position].arguments = resultBundle
        return fragments[position]
    }



}