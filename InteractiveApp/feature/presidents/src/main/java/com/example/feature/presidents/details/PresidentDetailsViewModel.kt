package com.example.feature.presidents.details

import androidx.lifecycle.ViewModel
import com.example.core.models.presidents.ColombiaPresident
import com.example.data.remote.mappers.toColombiaPresident
import com.example.data.remote.repository.ColombiaPresidentRepository
import com.example.data.remote.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PresidentDetailsViewModel @Inject constructor(
    private val colombiaPresidentRepository: ColombiaPresidentRepository
) : ViewModel() {

    private val _viewStateFlow = MutableStateFlow(ViewState())
    val viewStateFlow = _viewStateFlow.asStateFlow()

    private val coroutineScope = CoroutineScope(Dispatchers.Default)

    fun processEvent(viewEvent: ViewEvent) {
        when (viewEvent) {
            is ViewEvent.OnPresidentById -> {
                coroutineScope.launch {
                    getPresidentsByID(viewEvent.presidentId)
                    _viewStateFlow.update { it.copy(loading = false) }
                }
            }
        }
    }


    private suspend fun getPresidentsByID(presidentId: Int) {
        when (val response = colombiaPresidentRepository.getPresidentById(presidentId)) {
            is NetworkResult.Success -> {
                _viewStateFlow.update {
                    it.copy(president = response.data.toColombiaPresident())
                }
            }
            is NetworkResult.ApiError -> TODO()
            is NetworkResult.ApiException -> TODO()
        }
    }

    data class ViewState(
        val president: ColombiaPresident = ColombiaPresident(),
        val presidentId : Int = 0,
        val loading : Boolean = true
    )
    sealed interface ViewEvent {
        data class OnPresidentById(val presidentId : Int) : ViewEvent
    }
}