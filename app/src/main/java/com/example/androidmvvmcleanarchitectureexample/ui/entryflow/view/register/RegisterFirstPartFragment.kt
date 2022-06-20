package com.example.androidmvvmcleanarchitectureexample.ui.entryflow.view.register

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.fragment.findNavController
import com.example.androidmvvmcleanarchitectureexample.R
import com.example.androidmvvmcleanarchitectureexample.databinding.FragmentRegisterFirstPartBinding
import com.example.androidmvvmcleanarchitectureexample.ui.entryflow.viewmodel.EntryViewModel
import com.example.common.listeners.TextChangedListener
import com.example.common.utils.extentions.isNumber
import com.example.core.view.BaseMvvmFragment
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_register_first_part.*

@AndroidEntryPoint
class RegisterFirstPartFragment :
    BaseMvvmFragment<FragmentRegisterFirstPartBinding, EntryViewModel>(
        R.layout.fragment_register_first_part, EntryViewModel::class
    ) {

    override val viewModelFactoryOwner: () -> ViewModelStoreOwner = {
        findNavController().getViewModelStoreOwner(R.id.nav_graph_entry)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViewOnClickListeners()
        setTextChangeListener(tvUserFirstName, 4)
        setTextChangeListener(tvUserMiddleName, 0)
        setTextChangeListener(tvUserLastName, 5)
        setTextChangeListener(tvUserMail, 5)
        setTextChangeListener(tvUserPhone, 5)
        setTextChangeListener(tvUserName, 5)
        setTextChangeListener(tvUserFirstName, 5)
        setTextChangeListener(tvUserPassword, 7, tvUserRePassword)
        setTextChangeListener(tvUserRePassword, 7, tvUserPassword, true)
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
            var oldValue = ""
            inputLayout?.editText?.addTextChangedListener(object : TextChangedListener {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                    s?.let {
                        oldValue = it.toString()
                    }
                }
                override fun onTextChanged(
                    inputLayoutText: CharSequence?,
                    p1: Int,
                    p2: Int,
                    p3: Int
                ) {

                    fun setEditTextValueWithoutNotifyListener(value: String, cursorPosition: Int = value.length) {
                        inputLayout.editText?.removeTextChangedListener(this)
                        val cursorPositionValue = when{
                            cursorPosition < 0 -> 0
                            cursorPosition > value.length -> value.length
                            else -> cursorPosition
                        }
                        oldValue = try {
                            inputLayout.editText?.setText(value)
                            inputLayout.editText?.setSelection(cursorPositionValue)
                            value
                        } catch (e: Exception){
                            inputLayout.editText?.setText("")
                            inputLayout.editText?.setSelection(0)
                            "EMPTY"
                        }
                        inputLayout.editText?.addTextChangedListener(this)
                    }

                    if (isBirthDayInput){
                        inputLayoutText?.let { inputLayoutTextValue ->
//                            inputLayoutTextValue.toString().isNumber()  {}
                           // birthday input logic
                        }
                    } else {
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
                }
            })
        }
    }

    private fun setViewOnClickListeners() {
        binding.apply {
            signUpBtn.setOnClickListener {
                if (
                    checkValidation(tvUserFirstName, 5) &&
                    checkValidation(tvUserLastName, 5) &&
                    checkValidation(tvUserMail, 5) &&
                    checkValidation(tvUserPhone, 5) &&
                    checkValidation(tvUserName, 5) &&
                    checkValidation(tvUserFirstName, 5) &&
                    checkValidation(tvUserPassword, 7) &&
                    checkValidation(tvUserRePassword, 7, tvUserPassword)
                ) {
                    findNavController().navigate(R.id.action_registerFirstPartFragment_to_registerSecondPartFragment)
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
            inputLayout.error = if (isValid) null else "wrong input"
            return inputLayoutTextValue.length > minLength

        } ?: run {
            return false
        }
    }


}