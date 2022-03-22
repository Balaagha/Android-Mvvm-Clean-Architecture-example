package com.example.core.view

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment

abstract class BaseFragment<ViewDataBindingType : ViewDataBinding>(@LayoutRes val layoutId: Int) : DialogFragment() {

    private var _binding: ViewDataBindingType? = null
    protected val binding: ViewDataBindingType get() = _binding as ViewDataBindingType

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        if (_binding == null)
            throw IllegalArgumentException("Binding cannot be null")
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    /**
     * it is for hiding keyboard
     */
    fun hideKeyboard() {
        try {
            val inputMethodManager =
                context?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view?.windowToken, 0)
        } catch (e: Exception) {
//            Timber.e(e, "exception occurred at hide keyboard")
        }
    }
}