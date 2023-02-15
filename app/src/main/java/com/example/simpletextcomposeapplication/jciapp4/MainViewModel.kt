package com.example.simpletextcomposeapplication.jciapp4

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simpletextcomposeapplication.jciapp4.api.MeawService
import kotlinx.coroutines.launch

data class MeawData(val data: String)

interface IMainViewModel {
    fun getLiveData(): LiveData<List<MeawData>>
    fun getData(numberOfLines: Int)
}

class MainViewModel(
    val api: MeawService = MeawService()
) : ViewModel(), IMainViewModel {

    private val liveData: MutableLiveData<List<MeawData>> = MutableLiveData()

    override fun getLiveData(): LiveData<List<MeawData>> = liveData

    override fun getData(numberOfLines: Int) {
        viewModelScope.launch {
            liveData.value = api.getData(numberOfLines)
        }
    }
}