package com.example.androidmvvmcleanarchitectureexample.ui.entryflow.view.login

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.fragment.findNavController
import com.example.androidmvvmcleanarchitectureexample.R
import com.example.androidmvvmcleanarchitectureexample.databinding.FragmentLoginBinding
import com.example.androidmvvmcleanarchitectureexample.ui.entryflow.utils.checkInputValidation
import com.example.androidmvvmcleanarchitectureexample.ui.entryflow.viewmodel.EntryViewModel
import com.example.common.listeners.TextChangedListener
import com.example.core.view.BaseMvvmFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseMvvmFragment<FragmentLoginBinding, EntryViewModel>(
    R.layout.fragment_login, EntryViewModel::class
) {
    override val viewModelFactoryOwner: () -> ViewModelStoreOwner = {
        findNavController().getViewModelStoreOwner(R.id.nav_graph_entry)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViewOnClickListeners()

        binding.apply {
            userMail.editText?.addTextChangedListener(object : TextChangedListener {
                override fun onTextChanged(inputLayoutText: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    userMail.error = null
                }
            })
            userPassword.editText?.addTextChangedListener(object : TextChangedListener {
                override fun onTextChanged(inputLayoutText: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    userPassword.error = null
                }
            })
        }

    }

    private fun setViewOnClickListeners() {
        binding.apply {
            forgotBtn.setOnClickListener {
                findNavController().navigate(R.id.action_loginFragment_to_forgotPasswordMehtodSelectorFragment)
            }
            signInBtn.setOnClickListener {
                if (checkInputValidation(userMail, 5) && checkInputValidation(userPassword, 8)) {
                    viewModel.onSignInBtnClicked()
//                    activity?.apply {
//                        finish()
//                        startActivity(Intent(requireContext(), MainActivity::class.java))
//                    }
                }
            }
            signUpBtn.setOnClickListener {
                findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
            }
        }
    }




}