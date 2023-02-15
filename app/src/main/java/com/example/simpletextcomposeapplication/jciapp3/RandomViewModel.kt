package com.example.simpletextcomposeapplication.jciapp3

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kotlin.random.Random


data class Data2(val name: String, val number: Int)

interface Data2ViewModel {
    fun getData()
    fun getLiveData(): LiveData<List<Data2>>
}

class Data2ViewModelImpl : ViewModel(), Data2ViewModel {

    private val data2LiveData: MutableLiveData<List<Data2>> = MutableLiveData()

    override fun getLiveData(): LiveData<List<Data2>> = data2LiveData

    override fun getData() {
        viewModelScope.launch {
            data2LiveData.value = (1..100).map { Data2(it.toString(), Random.nextInt(10000, 99999)) }
        }
    }
}