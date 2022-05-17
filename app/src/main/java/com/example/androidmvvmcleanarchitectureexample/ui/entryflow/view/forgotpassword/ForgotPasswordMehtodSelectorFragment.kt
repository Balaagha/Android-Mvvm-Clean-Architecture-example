package com.example.androidmvvmcleanarchitectureexample.ui.entryflow.view.forgotpassword

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.fragment.findNavController
import com.example.androidmvvmcleanarchitectureexample.R
import com.example.androidmvvmcleanarchitectureexample.databinding.FragmentRegisterBinding
import com.example.androidmvvmcleanarchitectureexample.ui.entryflow.viewmodel.EntryViewModel
import com.example.core.view.BaseMvvmFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_forgot_password_mehtod_selector.*


@AndroidEntryPoint
class ForgotPasswordMehtodSelectorFragment : BaseMvvmFragment<FragmentRegisterBinding, EntryViewModel>(
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