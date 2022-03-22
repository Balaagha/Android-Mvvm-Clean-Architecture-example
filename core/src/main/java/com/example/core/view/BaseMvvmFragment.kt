package com.example.core.view

import android.os.Bundle
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.createViewModelLazy
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.example.common.utils.extentions.observe
import com.example.core.event.BaseUiEvent
import com.example.core.viewmodel.BaseViewModel
import kotlin.reflect.KClass

abstract class BaseMvvmFragment<VB : ViewDataBinding>(
    @LayoutRes layoutId: Int,
    viewModelClass: KClass<BaseViewModel>,
) : BaseFragment<VB>(layoutId) {

    open val viewModelFactoryOwner: (() -> ViewModelStoreOwner) = { this }

    open val factoryProducer: ViewModelProvider.Factory by lazy { defaultViewModelProviderFactory }

    open val viewModel: BaseViewModel by createViewModelLazy(
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
        observe(viewModel.event,::handleEvent)
    }

    protected open fun handleEvent(uiActionEvent: BaseUiEvent?) {
        when(uiActionEvent){
            is BaseUiEvent.Alert -> {
                showAlertViaBaseUiEvent(uiActionEvent)
            }
            is BaseUiEvent.SnackBar -> {
                showSnackBarViaBaseUiEvent(uiActionEvent)
            }
            is BaseUiEvent.Toast -> {
                showToastViaBaseUiEvent(uiActionEvent)
            }
            is BaseUiEvent.LoadingIndicator -> {
                showLoadingIndicatorViaBaseUiEvent(uiActionEvent)
            }
            else -> {
//                Timber.e("Unknown event handle $uiActionEvent ")
            }
        }
    }

    protected open fun showLoadingIndicatorViaBaseUiEvent(uiActionEvent: BaseUiEvent.LoadingIndicator) {
        Toast.makeText(requireContext(), "BaseUiEvent.LoadingIndicator handle with state ${ uiActionEvent.isLoadingEnable}",Toast.LENGTH_SHORT).show()
    }

    protected open fun showAlertViaBaseUiEvent(uiActionEvent: BaseUiEvent.Alert) {
        Toast.makeText(requireContext(), "BaseUiEvent.Alert handle with title ${ uiActionEvent.title.toString() }",Toast.LENGTH_SHORT).show()
    }

    protected open fun showToastViaBaseUiEvent(uiActionEvent: BaseUiEvent.Toast) {
        Toast.makeText(requireContext(), "BaseUiEvent.Toast handle with title ${ uiActionEvent.title.toString() }",Toast.LENGTH_SHORT).show()
    }

    protected open fun showSnackBarViaBaseUiEvent(uiActionEvent: BaseUiEvent.SnackBar) {
        Toast.makeText(requireContext(), "BaseUiEvent.SnackBar handle with title ${ uiActionEvent.title.toString() }",Toast.LENGTH_SHORT).show()
    }




}