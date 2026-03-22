package com.example.call.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.call.utils.CallState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CallViewModel : ViewModel() {

    val callState = MutableLiveData(CallState.IDLE)
    val phoneNumber = MutableLiveData("")
    val callTime = MutableLiveData(0)

    fun startCall(number: String) {
        phoneNumber.value = number
        callState.value = CallState.CALLING
    }

    fun incomingCall(number: String) {
        phoneNumber.value = number
        callState.value = CallState.RINGING
    }

    fun acceptCall() {
        callState.value = CallState.ACTIVE
        startTimer()
    }

    fun endCall() {
        callState.value = CallState.ENDED
        callTime.value = 0
    }

    private fun startTimer() {
        viewModelScope.launch {
            while (callState.value == CallState.ACTIVE) {
                delay(1000)
                callTime.postValue((callTime.value ?: 0) + 1)
            }
        }
    }
}
