package com.example.core.view

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import com.example.core.helper.viewDataBinding

abstract class BaseFragment<ViewDataBindingType : ViewDataBinding>(@LayoutRes val layoutId: Int) : DialogFragment() {

    /**
     * View data binding
     */
    open val binding: ViewDataBindingType by viewDataBinding(::onInternalPreDestroyView)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layoutId, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
    }

    /**
     * This method is for hiding keyboard
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

    internal open fun onInternalPreDestroyView() {
        // Expose method for inheritance
        onPreDestroyView()
    }

    /**
     * This method is called before destroying data binding
     */
    open fun onPreDestroyView() {
        // NoImpl
    }



}