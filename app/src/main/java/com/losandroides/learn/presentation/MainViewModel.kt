package com.losandroides.learn.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.losandroides.learn.di.IO
import com.losandroides.learn.domain.ItemsUseCase
import com.losandroides.learn.domain.model.Item
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val itemsUseCase: ItemsUseCase,
    @IO private val dispatcher: CoroutineDispatcher,
) : ViewModel() {

    init {
        getItems()
    }

    private val _viewState = MutableStateFlow<ViewState>(ViewState.Loading)
    val viewState: StateFlow<ViewState> = _viewState

    private fun getItems() {
        viewModelScope.launch(dispatcher) {
            itemsUseCase()
                .fold(
                    {
                        _viewState.value = ViewState.Error
                    },
                    { items ->
                        _viewState.value = ViewState.Content(items)
                    })
        }
    }

    sealed class ViewState {
        object Error : ViewState()
        object Loading : ViewState()
        data class Content(val items: List<Item> = emptyList()) : ViewState()
    }
}
