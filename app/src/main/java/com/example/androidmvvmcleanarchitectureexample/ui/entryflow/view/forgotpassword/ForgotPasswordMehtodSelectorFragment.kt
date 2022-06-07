package com.example.androidmvvmcleanarchitectureexample.ui.entryflow.view.forgotpassword

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.fragment.findNavController
import com.example.androidmvvmcleanarchitectureexample.R
import com.example.androidmvvmcleanarchitectureexample.databinding.FragmentForgotPasswordMehtodSelectorBinding
import com.example.androidmvvmcleanarchitectureexample.ui.entryflow.viewmodel.EntryViewModel
import com.example.core.view.BaseMvvmFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ForgotPasswordMehtodSelectorFragment : BaseMvvmFragment<FragmentForgotPasswordMehtodSelectorBinding, EntryViewModel>(
    R.layout.fragment_forgot_password_mehtod_selector, EntryViewModel::class
) {

    override val viewModelFactoryOwner: () -> ViewModelStoreOwner = {
        findNavController().getViewModelStoreOwner(R.id.nav_graph_entry)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            backBtn.setOnClickListener {
                findNavController().navigateUp()
            }
        }
    }

}