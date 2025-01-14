package com.example.feature.presidents.list

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
class PresidentListViewModel @Inject constructor(
    private val colombiaPresidentRepository: ColombiaPresidentRepository
) : ViewModel() {

    private val _viewStateFlow = MutableStateFlow(ViewState())
    val viewStateFlow = _viewStateFlow.asStateFlow()
    private val pageSize = 10
    private var allPresidents: List<ColombiaPresident> = emptyList()

    private val coroutineScope = CoroutineScope(Dispatchers.Default)

    fun processEvent(viewEvent: ViewEvent) {
        when (viewEvent) {
            is ViewEvent.LoadData -> {
                coroutineScope.launch {
                    _viewStateFlow.update { it.copy(loading = LoadingState.LoadingScreen) }
                    getPresidents(1, pageSize)
                    _viewStateFlow.update { it.copy(loading = LoadingState.Loaded) }
                }
            }

            is ViewEvent.LoadMoreData -> {
                coroutineScope.launch {
                    _viewStateFlow.update { it.copy(loading = LoadingState.LoadingPartially) }
                    getPresidents(viewEvent.page, pageSize)
                    _viewStateFlow.update { it.copy(loading = LoadingState.Loaded) }
                }
            }

            is ViewEvent.OnItemSelected -> {
                _viewStateFlow.update { it.copy(navigateEffect = ViewEffect.Navigate("PresidentDetailsScreen/${viewEvent.itemId}")) }
            }

            is ViewEvent.ConsumeEffect -> {
                _viewStateFlow.update { it.copy(navigateEffect = ViewEffect.Navigate("")) }
            }

            is ViewEvent.OnSearchPresident -> {
                _viewStateFlow.update { it.copy(searchDescription = viewEvent.searchDescription) }
                filteredPresidentList(viewEvent.searchDescription)
            }
        }
    }

    private fun filteredPresidentList(
        searchQuery: String
    ) {
        val filteredPresidents = if (searchQuery.isEmpty()) {
            allPresidents
        } else {
            allPresidents.filter { president ->
                president.name.contains(searchQuery, ignoreCase = true)
            }
        }
        _viewStateFlow.update { it.copy(presidents = filteredPresidents.distinctBy { it.id }) }
    }

    private suspend fun getPresidents(currentPage: Int, pageSize: Int) {
        when (val response = colombiaPresidentRepository.getPresidents(currentPage, pageSize)) {
            is NetworkResult.Success -> {
                allPresidents =
                    allPresidents + (response.data.data.map { presidentList ->
                        presidentList.toColombiaPresident()
                    })
                filteredPresidentList("")
            }

            is NetworkResult.ApiError -> TODO()
            is NetworkResult.ApiException -> TODO()
        }
    }

    data class ViewState(
        val presidents: List<ColombiaPresident> = emptyList(),
        val searchDescription: String = "",
        val loading: LoadingState = LoadingState.LoadingScreen,
        val searchParameters: List<String> = emptyList(),
        val navigateEffect: ViewEffect = ViewEffect.Navigate("")
    )

    enum class LoadingState {
        LoadingScreen,
        LoadingPartially,
        Loaded
    }

    sealed interface ViewEvent {
        data class LoadMoreData(val page: Int) : ViewEvent
        data object LoadData : ViewEvent
        data class OnItemSelected(val itemId: Int) : ViewEvent
        data class OnSearchPresident(val searchDescription: String) : ViewEvent
        data object ConsumeEffect : ViewEvent
    }

    sealed interface ViewEffect {
        data class Navigate(val route: String) : ViewEffect
    }

}