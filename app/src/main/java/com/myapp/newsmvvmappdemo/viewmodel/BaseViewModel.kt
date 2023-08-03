package com.myapp.newsmvvmappdemo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

open class BaseViewModel : ViewModel() {
    private val _processing = MutableLiveData<Boolean?>(null)

    val processing: LiveData<Boolean?>
        get() = _processing

    private val _errorMessage = MutableLiveData<String?>()

    protected fun processAsync(block: suspend () -> Unit) {
        viewModelScope.launch {
            try {
                _processing.value = true
                block()
            } catch (error: Error) {
                _errorMessage.postValue(error.message)
            } finally {
                _processing.value = false
            }
        }
    }

}
