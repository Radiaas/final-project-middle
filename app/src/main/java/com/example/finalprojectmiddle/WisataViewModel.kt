package com.example.finalprojectmiddle

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WisataViewModel : ViewModel() {
    private val _wisataList = MutableLiveData<List<Wisata>>()
    val wisataList: LiveData<List<Wisata>> get() = _wisataList

    fun fetchWisataData(token: String) {
        RetrofitClient.instance.getWisataList(token = token)
            .enqueue(object : Callback<ApiResponse> {
                override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                    if (response.isSuccessful) {
                        _wisataList.value = response.body()?.data?.wisatalist ?: emptyList()
                    }
                }

                override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                    _wisataList.value = emptyList()
                }
            })
    }
}
