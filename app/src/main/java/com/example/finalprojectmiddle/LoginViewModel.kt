package com.example.finalprojectmiddle

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
                if (response.isSuccessful) {
                    _loginResponse.postValue(response.body())
                } else {
                    _loginResponse.postValue(LoginResponse(code = response.code(), status = "error", message = "Login gagal", data = null))
                }
            } catch (e: Exception) {
                _loginResponse.postValue(LoginResponse(code = 500, status = "error", message = e.message ?: "Unknown error", data = null))
            }
        }
    }
}
