package com.leafwise.medapp.presentation.ui.home

import androidx.lifecycle.ViewModel
import com.leafwise.medapp.domain.model.Medication
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {

    private val _homeUiState = MutableStateFlow<HomeUiState>(HomeUiState.Empty)
    val homeUiState: StateFlow<HomeUiState> = _homeUiState.asStateFlow()

    sealed class HomeUiState {
        object Empty : HomeUiState()
        object Loading : HomeUiState()
        class Success(val data: List<Medication>) : HomeUiState()
        class Error(val message: String) : HomeUiState()
    }

}