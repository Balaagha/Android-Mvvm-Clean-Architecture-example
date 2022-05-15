package com.example.androidmvvmcleanarchitectureexample.ui.entryflow.view.login

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.androidmvvmcleanarchitectureexample.R
import com.example.androidmvvmcleanarchitectureexample.ui.MainActivity
import com.example.common.listeners.TextChangedListener
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViewOnClickListeners()

        userMail?.editText?.addTextChangedListener(object : TextChangedListener {
            override fun onTextChanged(inputLayoutText: CharSequence?, p1: Int, p2: Int, p3: Int) {
                userMail.error = null
            }
        })
        userPassword?.editText?.addTextChangedListener(object : TextChangedListener {
            override fun onTextChanged(inputLayoutText: CharSequence?, p1: Int, p2: Int, p3: Int) {
                userPassword.error = null
            }
        })
    }

    private fun setViewOnClickListeners() {
        forgotBtn.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_forgotPasswordMehtodSelectorFragment)
        }
        signInBtn.setOnClickListener {
            if (
                checkValidation(userMail, 5) && checkValidation(userPassword, 8)
            ) {
                activity?.apply {
                    finish()
                    startActivity(Intent(requireContext(), MainActivity::class.java))
                }
            }
        }
        signUpBtn.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }

    private fun checkValidation(inputLayout: TextInputLayout, minLength: Int): Boolean {
        inputLayout.editText?.text?.let {
            val isValid = it.length > minLength
            inputLayout.error = if (isValid) null else "wrong input"
            return it.length > minLength
        } ?: run {
            return false
        }
    }


}