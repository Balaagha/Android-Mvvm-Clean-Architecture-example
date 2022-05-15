package com.example.androidmvvmcleanarchitectureexample.ui.entryflow.view.onboarding

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.androidmvvmcleanarchitectureexample.R
import com.example.androidmvvmcleanarchitectureexample.databinding.FragmentOnBoardingBinding
import com.example.androidmvvmcleanarchitectureexample.ui.entryflow.view.onboarding.screens.OnBoardingFirstFragment
import com.example.androidmvvmcleanarchitectureexample.ui.entryflow.view.onboarding.screens.OnBoardingSecondFragment
import com.example.androidmvvmcleanarchitectureexample.ui.entryflow.view.onboarding.screens.OnBoardingThreeFragment
import com.example.androidmvvmcleanarchitectureexample.ui.entryflow.viewmodel.EntryViewModel
import com.example.core.view.BaseMvvmFragment
import kotlinx.android.synthetic.main.fragment_on_boarding.*


class OnBoardingFragment : BaseMvvmFragment<FragmentOnBoardingBinding, EntryViewModel>(
    R.layout.fragment_on_boarding, EntryViewModel::class
) {

    override val viewModelFactoryOwner: () -> ViewModelStoreOwner = {
        findNavController().getViewModelStoreOwner(R.id.nav_graph_entry)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("myTag","In OnBoardingFragment, data=> ${viewModel.data}")

        val fragmentList = arrayListOf<Fragment>(
            OnBoardingFirstFragment(),
            OnBoardingSecondFragment(),
            OnBoardingThreeFragment()
        )
        val adapter = OnBoardingViewPagerAdapter(
            fragmentList,
            requireActivity().supportFragmentManager,
            lifecycle
        )
        binding.onBoardingViewPager.adapter = adapter

        onBoardingViewPager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                when (position) {
                    0 -> {
                        mainBtn.text = "Next"
                    }
                    1 -> {
                        mainBtn.text = "Next"
                    }
                    2 -> {
                        mainBtn.text = "Finish"
                    }
                }
            }
        })

        mainBtn.setOnClickListener {
            when (onBoardingViewPager.currentItem) {
                0 -> {
                    onBoardingViewPager.currentItem = 1
                }
                1 -> {
                    onBoardingViewPager.currentItem = 2
                }
                2 -> {
                    findNavController().navigate(R.id.action_onBoardingFragment_to_loginFragment)
                }
            }
        }

    }

}