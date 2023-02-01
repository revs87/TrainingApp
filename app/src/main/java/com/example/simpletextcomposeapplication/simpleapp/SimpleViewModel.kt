package com.example.simpletextcomposeapplication.simpleapp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MainViewModel : ViewModel() {
    val messageLiveData = MutableLiveData("")

    fun setMessage(newText: String) {
        messageLiveData.value = newText
    }
}

class MyViewModelFactory() : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel() as T
    }
}