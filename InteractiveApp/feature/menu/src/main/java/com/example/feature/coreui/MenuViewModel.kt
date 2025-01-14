package com.example.feature.coreui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MenuViewModel : ViewModel() {

    private val _viewStateFlow = MutableStateFlow(ViewState())
    val viewStateFlow = _viewStateFlow.asStateFlow()

    fun processEvent(viewEvent: ViewEvent) {
        when (viewEvent) {
            ViewEvent.OnGoToCapitalizer -> {
                _viewStateFlow.update { it.copy(navigateEffect = ViewEffect.Navigate("CapitalizerGenerator")) }
            }
            ViewEvent.OnGoToPassword -> {
                _viewStateFlow.update { it.copy(navigateEffect = ViewEffect.Navigate("RandomPasswordGenerator")) }
            }
            ViewEvent.OnGoToPresidentsList -> {
                _viewStateFlow.update { it.copy(navigateEffect = ViewEffect.Navigate("PresidentList")) }
            }

            ViewEvent.ConsumeEffect -> {
                _viewStateFlow.update { it.copy(navigateEffect = ViewEffect.Navigate("")) }
            }
        }
    }

    data class ViewState(
        val navigateEffect: ViewEffect = ViewEffect.Navigate("")
    )

    sealed interface ViewEvent {
        data object OnGoToCapitalizer : ViewEvent
        data object OnGoToPassword : ViewEvent
        data object OnGoToPresidentsList : ViewEvent
        data object ConsumeEffect : ViewEvent
    }

    sealed interface ViewEffect {
        data class Navigate(val route: String) : ViewEffect
    }
}
