package com.leafwise.medapp.presentation.ui.home

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leafwise.medapp.R
import com.leafwise.medapp.domain.model.AlarmInfo.Companion.TEST_ALARM
import com.leafwise.medapp.domain.model.meds.Medication
import com.leafwise.medapp.domain.usecase.GetMedicationsUseCase
import com.leafwise.medapp.util.AlarmUtil
import com.leafwise.medapp.util.Permissions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@Suppress("UnusedPrivateProperty")
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val alarmUtil: AlarmUtil,
    private val permissions: Permissions,
    private val getMedicationsUseCase: GetMedicationsUseCase,
): ViewModel() {

    private val _homeUiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val homeUiState: StateFlow<HomeUiState> = _homeUiState.asStateFlow()

    init {
        loadAlarms()
    }

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

    private fun loadAlarms() =
        viewModelScope.launch {
            _homeUiState.run {
                update { HomeUiState.Loading }
                getMedicationsUseCase.invoke()
                    .collect{ items ->
                        update {
                            if(items.isEmpty()) HomeUiState.Empty
                            else HomeUiState.Success(items)
                        }
                    }
            }

        }

    fun scheduleAlarm(){
        //alarmUtil.scheduleExactAlarm(TEST_ALARM)
        //_homeUiState.value = HomeUiState.Success(listOf())
    }

    sealed class HomeUiState {
        object Empty : HomeUiState()
        object Loading : HomeUiState()
        class Success(val data: List<Medication>) : HomeUiState()
        class Error(@StringRes val message: Int) : HomeUiState()
    }
}