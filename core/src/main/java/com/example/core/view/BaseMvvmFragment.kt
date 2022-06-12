package com.example.core.view

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.createViewModelLazy
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.example.common.utils.extentions.observe
import com.example.core.R
import com.example.core.event.BaseUiEvent
import com.example.core.viewmodel.BaseViewModel
import com.example.uitoolkit.popup.GenericPopUpHelper
import com.example.uitoolkit.utils.extentions.getMyString
import kotlin.reflect.KClass

abstract class BaseMvvmFragment<VB : ViewDataBinding, VM : BaseViewModel>(
    @LayoutRes layoutId: Int,
    viewModelClass: KClass<VM>,
) : BaseFragment<VB>(layoutId) {

    open val viewModelFactoryOwner: (() -> ViewModelStoreOwner) = { this }

    open val factoryProducer: ViewModelProvider.Factory by lazy { defaultViewModelProviderFactory }

    open val viewModel: VM by createViewModelLazy(
        viewModelClass,
        { viewModelFactoryOwner.invoke().viewModelStore },
        { factoryProducer }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeObserver()
    }

    private fun initializeObserver() {
        // Listen to events
        observe(viewModel.event, ::handleGenericsUiActionEvents)
        observe(viewModel.loadingEvent, ::handleLoadingIndicatorEvent)
    }

    protected open fun handleGenericsUiActionEvents(uiActionEvent: BaseUiEvent?) {
        Log.d("myTag"," call handleEvent => $uiActionEvent")
        when (uiActionEvent) {
            is BaseUiEvent.Alert -> {
                showAlertViaBaseUiEvent(uiActionEvent)
            }
            is BaseUiEvent.SnackBar -> {
                showSnackBarViaBaseUiEvent(uiActionEvent)
            }
            is BaseUiEvent.Toast -> {
                showToastViaBaseUiEvent(uiActionEvent)
            }
            else -> {
//                Timber.e("Unknown event handle $uiActionEvent ")
            }
        }
    }

    protected open fun showAlertViaBaseUiEvent(uiActionEvent: BaseUiEvent.Alert) {
        GenericPopUpHelper.Builder(childFragmentManager)
            .setStyle(GenericPopUpHelper.Style.STYLE_2_VERTICAL_BUTTONS)
            .setImage(R.drawable.ic_error_icon)
            .setImageLayoutParams(
                imageWith = 100,
                imageHeight = 100
            )
            .setTitle(uiActionEvent.title ?: getMyString(uiActionEvent.titleRes))
            .setTitleColor(ContextCompat.getColor(requireContext(), R.color.black))
            .setContent(uiActionEvent.message ?: getMyString(uiActionEvent.messageRes))
            .setContentColor(ContextCompat.getColor(requireContext(), R.color.black))
            .setPositiveButtonBackground(R.drawable.btn_approve)
            .setPositiveButtonTextAppearance(R.style.MBoldWhite)
            .setPositiveButton("Okay") {
                it.dismiss()
            }
            .setNegativeButtonBackground(R.drawable.btn_cancel)
            .setNegativeButtonTextAppearance(R.style.MBoldBlack)
            .setNegativeButton("Cancel", null)
            .create()
    }

    protected open fun showToastViaBaseUiEvent(uiActionEvent: BaseUiEvent.Toast) {
        Toast.makeText(
            requireContext(),
            "BaseUiEvent.Toast handle with title ${uiActionEvent.title.toString()}",
            Toast.LENGTH_SHORT
        ).show()
    }

    protected open fun showSnackBarViaBaseUiEvent(uiActionEvent: BaseUiEvent.SnackBar) {
        Toast.makeText(
            requireContext(),
            "BaseUiEvent.SnackBar handle with title ${uiActionEvent.title.toString()}",
            Toast.LENGTH_SHORT
        ).show()
    }


    protected open fun handleLoadingIndicatorEvent(isShow: Boolean?) {
        showLoadingIndicatorViaBaseUiEvent(isShow)
    }

    protected open fun showLoadingIndicatorViaBaseUiEvent(isShow: Boolean?) {
        if (isShow == true) {
            (activity as BaseActivity).showLoadingDialog()
        } else {
            (activity as BaseActivity).hideLoadingDialog()
        }
    }


}