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
                Log.d("WisataViewModel", "Response: ${response.raw()}")  // Log response
                if (response.isSuccessful && response.body() != null) {
                    Log.d("WisataViewModel", "Data Wisata: ${response.body()}")
                    _wisataList.postValue(response.body()?.data?.wisataList ?: emptyList())

                    if (response.isSuccessful && response.body() != null) {
                        val responseBody = response.body()
                        Log.d("WisataViewModel", "Full Response Body: $responseBody")
                        Log.d("WisataViewModel", "Wisata List: ${responseBody?.data?.wisataList}")

                        _wisataList.postValue(responseBody?.data?.wisataList ?: emptyList())
                    } else {
                        _errorMessage.postValue("Gagal mengambil data wisata: ${response.message()}")
                    }

                } else {
                    _errorMessage.postValue("Gagal mengambil data wisata.")
                }
            } catch (e: Exception) {
                _errorMessage.postValue("Error: ${e.message}")
                Log.e("WisataViewModel", "Error: ${e.message}")
            }
        }
    }
}
