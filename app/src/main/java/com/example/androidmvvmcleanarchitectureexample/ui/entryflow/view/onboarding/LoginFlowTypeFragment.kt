package com.example.androidmvvmcleanarchitectureexample.ui.entryflow.view.onboarding

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.fragment.findNavController
import com.example.androidmvvmcleanarchitectureexample.R
import com.example.androidmvvmcleanarchitectureexample.databinding.FragmentLoginFlowTypeBinding
import com.example.androidmvvmcleanarchitectureexample.databinding.FragmentSplashBinding
import com.example.androidmvvmcleanarchitectureexample.ui.entryflow.viewmodel.EntryViewModel
import com.example.core.view.BaseMvvmFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFlowTypeFragment : BaseMvvmFragment<FragmentLoginFlowTypeBinding, EntryViewModel>(
    R.layout.fragment_login_flow_type, EntryViewModel::class
) {

    override val viewModelFactoryOwner: () -> ViewModelStoreOwner = {
        findNavController().getViewModelStoreOwner(R.id.nav_graph_entry)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpOnClickListener()
    }

    private fun setUpOnClickListener() {
        binding.apply {
            signWithId.setOnClickListener {
                findNavController().navigate(
                    R.id.action_loginFlowTypeFragment_to_loginFragment
                )
            }
        }
    }

}