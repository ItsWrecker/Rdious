package com.wrecker.rdious.presantation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wrecker.rdious.domain.entities.EventStatus
import com.wrecker.rdious.domain.entities.FacilityData
import com.wrecker.rdious.domain.repositories.FacilityRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FacilityViewModel @Inject constructor(
    private val facilityRepository: FacilityRepository
) : ViewModel() {


    private val _state = MutableStateFlow(MainState())
    val state: StateFlow<MainState> = _state


    companion object {
        private val TAG = FacilityViewModel::class.java.simpleName
    }

    fun getData() = viewModelScope.launch {
        facilityRepository.getFacility().collectLatest {
            when (it) {
                is EventStatus.Error -> Log.e(TAG, it.message.toString())
                is EventStatus.Loading -> Log.e(TAG, it.message)
                is EventStatus.Success -> {
                    _state.value = _state.value.copy(
                        facilities = it.data.facilities
                    )
                    facilityRepository.getExclusion().collectLatest { exclusion ->
                        when(exclusion){
                            is EventStatus.Error -> Log.e(TAG, exclusion.message.toString())
                            is EventStatus.Loading ->  Log.e(TAG, exclusion.message)
                            is EventStatus.Success -> {
                                _state.value = _state.value.copy(
                                    exclusion = exclusion.data
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}