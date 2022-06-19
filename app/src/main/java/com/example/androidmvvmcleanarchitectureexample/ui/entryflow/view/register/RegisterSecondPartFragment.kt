package com.example.androidmvvmcleanarchitectureexample.ui.entryflow.view.register

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.fragment.findNavController
import com.example.androidmvvmcleanarchitectureexample.R
import com.example.androidmvvmcleanarchitectureexample.databinding.FragmentRegisterSecondPartBinding
import com.example.androidmvvmcleanarchitectureexample.ui.entryflow.viewmodel.EntryViewModel
import com.example.common.listeners.TextChangedListener
import com.example.common.utils.extentions.observe
import com.example.core.view.BaseMvvmFragment
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterSecondPartFragment : BaseMvvmFragment<FragmentRegisterSecondPartBinding, EntryViewModel>(
    R.layout.fragment_register_second_part, EntryViewModel::class
) {

    override val viewModelFactoryOwner: () -> ViewModelStoreOwner = {
        findNavController().getViewModelStoreOwner(R.id.nav_graph_entry)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewSubscriber()
    }

    private fun initViewSubscriber() {
        observe(viewModel.navigationRouteId) {
            if (it?.first == this.javaClass) {
                findNavController().popBackStack(R.id.loginFragment,false)
            }
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViewOnClickListeners()
        binding.apply {
            setTextChangeListener(tvCountryName, 5)
            setTextChangeListener(tvStreetName, 5)
            setTextChangeListener(tvStreetNumber, 5)
            setTextChangeListener(tvBusinessAddress, 5)
            setTextChangeListener(tvBusinessMailAddress, 5)
            setTextChangeListener(tvBusinessName, 5)
            setTextChangeListener(tvBusinessVatNumber, 5)
            setTextChangeListener(tvBusinessRegistrationNumber, 5)
        }
        binding.apply {
            viewmodel = viewModel
        }
    }

    private fun setTextChangeListener(
        inputLayout: TextInputLayout?,
        minLength: Int,
        passwordLayout: TextInputLayout? = null,
        isCheckPasswordMatch: Boolean = false,
        isBirthDayInput: Boolean = false
    ) {
        binding.apply {
            inputLayout?.editText?.addTextChangedListener(object : TextChangedListener {
                override fun onTextChanged(
                    inputLayoutText: CharSequence?,
                    p1: Int,
                    p2: Int,
                    p3: Int
                ) {
                    if (!isCheckPasswordMatch) {
                        inputLayout.error = null
                    } else {
                        inputLayoutText?.let { inputLayoutTextValue ->
                            val isValid = inputLayoutTextValue.length > minLength
                            if (isValid && passwordLayout != null) {
                                val isMatchPassword =
                                    inputLayoutTextValue.toString() == passwordLayout.editText?.text.toString()
                                if (isMatchPassword) {
                                    inputLayout.error = null
                                }
                            }
                        }
                    }
                }
            })
        }
    }

    private fun setViewOnClickListeners() {
        binding.apply {
            signUpBtn.setOnClickListener {
                if (
                    checkValidation(tvCountryName, 5) &&
                    checkValidation(tvStreetName, 5) &&
                    checkValidation(tvStreetNumber, 5)&&
                    checkValidation(tvBusinessAddress, 5)&&
                    checkValidation(tvBusinessMailAddress, 5)&&
                    checkValidation(tvBusinessName, 5)&&
                    checkValidation(tvBusinessVatNumber, 5)&&
                    checkValidation(tvBusinessRegistrationNumber, 5)
                ) {
                    viewModel.onSignUpBtnClicked()
                }
            }
        }
    }

    private fun checkValidation(
        inputLayout: TextInputLayout,
        minLength: Int,
        passwordLayout: TextInputLayout? = null,
    ): Boolean {
        inputLayout.editText?.text?.let { inputLayoutTextValue ->
            val isValid = inputLayoutTextValue.length > minLength
            if (isValid && passwordLayout != null) {
                val isMatchPassword =
                    inputLayoutTextValue.toString() == passwordLayout.editText?.text.toString()
                inputLayout.error = if (isMatchPassword) null else "password not match!"
                return isMatchPassword
            }
            inputLayout.error = if (isValid) null else "required input"
            return inputLayoutTextValue.length > minLength
        } ?: run {
            return false
        }
    }


}