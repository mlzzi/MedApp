package com.leafwise.medapp.presentation.ui.home

import androidx.lifecycle.ViewModel
import com.leafwise.medapp.framework.db.entity.MedicationEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.Calendar

class HomeViewModel : ViewModel() {

    private val _homeUiState = MutableStateFlow<HomeUiState>(HomeUiState.Empty)
    val homeUiState: StateFlow<HomeUiState> = _homeUiState.asStateFlow()

    fun addMedication() {
        //_homeUiState.value = HomeUiState.Adding
    }

    sealed class HomeUiState {
        object Empty : HomeUiState()
        object Loading : HomeUiState()
        class Success(val data: List<MedicationEntity>) : HomeUiState()
        class Error(val message: String) : HomeUiState()
    }
}