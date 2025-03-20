package com.example.finalprojectmiddle

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class WisataViewModel : ViewModel() {
    private val _wisataList = MutableLiveData<List<WisataItem>>()
    val wisataList: LiveData<List<WisataItem>> get() = _wisataList

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    fun fetchWisata(token: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.instance.getWisata(token = token)
                if (response.isSuccessful && response.body() != null) {
                    _wisataList.postValue(response.body()?.data?.wisataList ?: emptyList())
                } else {
                    _errorMessage.postValue("Gagal mengambil data wisata.")
                }
            } catch (e: Exception) {
                _errorMessage.postValue("Error: ${e.message}")
            }
        }
    }

    fun searchWisata(token: String, keyword: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.instance.searchWisata(token = token, keyword = keyword)
                if (response.isSuccessful && response.body() != null) {
                    _wisataList.postValue(response.body()?.data?.wisataList ?: emptyList())
                } else {
                    _errorMessage.postValue("Gagal mencari wisata.")
                }
            } catch (e: Exception) {
                _errorMessage.postValue("Error: ${e.message}")
            }
        }
    }
}
