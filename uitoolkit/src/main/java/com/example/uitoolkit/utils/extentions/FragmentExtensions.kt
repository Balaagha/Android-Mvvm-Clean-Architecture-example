package com.example.uitoolkit.utils.extentions

import android.app.Activity
import androidx.activity.OnBackPressedCallback
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController

fun Fragment.hideKeyboard() = hideSoftInput(requireActivity())

fun Fragment.getColor(@ColorRes id: Int) = ContextCompat.getColor(requireContext(), id)

fun Fragment.getDrawable(@DrawableRes id: Int) = ContextCompat.getDrawable(requireContext(), id)!!

fun Fragment.getString(id: Int) = resources.getString(id)

fun <T> Fragment.getNavigationResultLiveData(key: String = "result") =
    findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<T>(key)

fun <T> Fragment.removeNavigationResultObserver(key: String = "result") =
    findNavController().currentBackStackEntry?.savedStateHandle?.remove<T>(key)

fun <T> Fragment.setNavigationResult(result: T, key: String = "result") {
    findNavController().previousBackStackEntry?.savedStateHandle?.set(key, result)
}

fun Fragment.onBackPressedCustomAction(action: () -> Unit) {
    requireActivity().onBackPressedDispatcher.addCallback(
        viewLifecycleOwner,
        object : OnBackPressedCallback(true) {
            override
            fun handleOnBackPressed() {
                action()
            }
        }
    )
}

fun Fragment.navigateSafe(directions: NavDirections, navOptions: NavOptions? = null) {
    findNavController().navigate(directions, navOptions)
}

fun Fragment.backToPreviousScreen() {
    findNavController().navigateUp()
}