package com.example.finalprojectmiddle

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    private val _loginResponse = MutableLiveData<LoginResponse>()
    val loginResponse: LiveData<LoginResponse> get() = _loginResponse

    fun login(email: String, password: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.instance.loginUser(LoginRequest(email = email, password = password))

                Log.d("LoginViewModel", "Response dari API: ${response.raw()}") // Debug response mentah
                Log.d("LoginViewModel", "Response body: ${response.body()}") // Debug body dari response

                if (response.isSuccessful) {
                    val body = response.body()
                    Log.d("LoginViewModel", "Login sukses, token: ${body?.data?.token}") // Debug token dari API

                    _loginResponse.postValue(body)
                } else {
                    Log.e("LoginViewModel", "Login gagal, kode: ${response.code()}")
                    _loginResponse.postValue(LoginResponse(code = response.code(), status = "error", message = "Login gagal", data = null))
                }
            } catch (e: Exception) {
                Log.e("LoginViewModel", "Exception saat login: ${e.message}")
                _loginResponse.postValue(LoginResponse(code = 500, status = "error", message = e.message ?: "Unknown error", data = null))
            }
        }
    }
}
