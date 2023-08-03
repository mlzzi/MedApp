package com.leafwise.medapp.presentation.ui.home

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import com.leafwise.medapp.R
import com.leafwise.medapp.framework.db.entity.MedicationEntity
import com.leafwise.medapp.util.AlarmUtil
import com.leafwise.medapp.util.Permissions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val alarmUtil: AlarmUtil,
    private val permissions: Permissions
): ViewModel() {

    private val _homeUiState = MutableStateFlow<HomeUiState>(HomeUiState.Empty)
    val homeUiState: StateFlow<HomeUiState> = _homeUiState.asStateFlow()

    //Should verify if the user has permission to schedule an alarm and if not, show a message
    fun verifyPermissions()  {
        if (permissions.hasSetAlarmPermission()) {
            //Should RESET UI state to... TODO FIX empty or success
            _homeUiState.value = HomeUiState.Empty

        } else {
            // Show a toast message indicating that the user doesn't have the required permission
            _homeUiState.value = HomeUiState.Error(
                message = R.string.permission_error_required
            )

        }
    }
    fun addMedication()  {

    }

    fun saveMedication(){

    }

    sealed class HomeUiState {
        object Empty : HomeUiState()
        object Loading : HomeUiState()
        class Success(val data: List<MedicationEntity>) : HomeUiState()
        class Error(@StringRes val message: Int) : HomeUiState()
    }
}