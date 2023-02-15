package com.example.simpletextcomposeapplication.jciapp2

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

val dataList: List<Data> = listOf(
    Data("Miguel", 18, "M"),
    Data("Alberto", 38, "M"),
    Data("Amaro", 21, "I"),
    Data("Manuel", 1, "M"),
    Data("Ricardo", 55, "M"),
    Data("Rosa", 45, "F"),
    Data("Rosa", 45, "F"),
    Data("Ricardo", 55, "M"),
    Data("Manuel", 1, "M"),
    Data("Sofia", 3, "F"),
    Data("Manuel", 78, "M"),
    Data("Manuel", 78, "M"),
    Data("Ricardo", 55, "I"),
    Data("Zidane",  17, "I"),
    Data("Diana", 15, "F"),
    Data("Alberto", 25, "M"),
    Data("Ricardo", 55, "M"),
    Data("Marlene", 23, "F"),
)

data class Data(val name: String, val age: Int, val gender: String, var occurrences: Int = -1)

interface DataViewModel {
    fun getData()
    fun getLiveData(): LiveData<List<Data>>
}

class DataViewModelImpl : ViewModel(), DataViewModel {

    private val listLiveData: MutableLiveData<List<Data>> = MutableLiveData<List<Data>>()

    override fun getLiveData() = listLiveData
    override fun getData() {
        viewModelScope.launch {

//            val dataMap = mutableMapOf<Data, Int>()
//            dataList
//                .map {
//                    if (dataMap.containsKey(it)) { dataMap[it] = dataMap[it]?.let { value -> value + 1 } ?: -1 }
//                    else { dataMap.put(it, 1) }
//                }
//            val sorted = dataMap
//                .map { Data(it.key.name, it.key.age, it.key.gender, it.value) }
//                .sortedWith(compareBy({ -it.occurrences }, { it.name }, { -it.age }))

            val sorted2 = dataList
                .distinct()
                .map { Data(it.name, it.age, it.gender, dataList.count { d -> d == it }) }
                .sortedWith(compareBy({ -it.occurrences }, { it.name }, { -it.age }))



            listLiveData.value = sorted2
        }
    }
}