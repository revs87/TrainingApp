package com.example.simpletextcomposeapplication.gendarizeapp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.simpletextcomposeapplication.gendarizeapp.base.BaseViewModel
import com.example.simpletextcomposeapplication.gendarizeapp.repository.api.GenderProfileByCountryResponse
import com.example.simpletextcomposeapplication.gendarizeapp.repository.api.GenderProfileResponse
import com.example.simpletextcomposeapplication.gendarizeapp.repository.api.GenderizeService
import com.example.simpletextcomposeapplication.gendarizeapp.repository.api.UiState
import com.example.simpletextcomposeapplication.gendarizeapp.repository.domain.GenderProfile
import kotlinx.coroutines.launch
import timber.log.Timber


class GenderizeViewModel(
    private val api: GenderizeService = GenderizeService()
) : BaseViewModel<UiState>() {

    /* Actual data */
    private val maleContentList = mutableListOf<String>().also { it.add("Male results:") }
    private val femaleContentList = mutableListOf<String>().also { it.add("Female results:") }
    private fun addToList(profile: GenderProfile, list: MutableList<String>) = list.add("Name:${profile.name}")

    /* Compose UI data linkage */
    val maleListLiveData = MutableLiveData<List<String>>()
    val femaleListLiveData = MutableLiveData<List<String>>()
    fun updateList(profile: GenderProfile) {
        setName("")
        when (profile.gender) {
            "male" -> {
                addToList(profile, maleContentList)
                maleListLiveData.value = maleContentList.toList()
            }
            "female" -> {
                addToList(profile, femaleContentList)
                femaleListLiveData.value = femaleContentList.toList()
            }
        }
    }
    val nameLiveData = MutableLiveData<String>()
    fun setName(name: String) { nameLiveData.value = name }

    /* API data */
    fun getGender(name: String) {
        viewModelScope.launch {
            uiState.value = UiState.Loading
            try {
                val gender = api.getGender(name)
                uiState.value = UiState.Success(gender.toDomain())
            } catch (e: Exception) {
                Timber.e(e)
                uiState.value = UiState.Error("Network request failed!")
            }
        }
    }
    fun getGenderByCountry(name: String, countryId: String = "PT") {
        viewModelScope.launch {
            uiState.value = UiState.Loading
            try {
                val gender = api.getGenderByCountry(name, countryId)
                uiState.value = UiState.Success(gender.toDomain())
            } catch (e: Exception) {
                Timber.e(e)
                uiState.value = UiState.Error("Network request failed!")
            }
        }
    }
}

private fun GenderProfileResponse.toDomain(): GenderProfile = GenderProfile(this.name, this.gender)
private fun GenderProfileByCountryResponse.toDomain(): GenderProfile = GenderProfile(this.name, this.gender)