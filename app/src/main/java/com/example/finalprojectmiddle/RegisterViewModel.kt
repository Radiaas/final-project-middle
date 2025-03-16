package com.example.finalprojectmiddle

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterViewModel : ViewModel() {
    private val _registerResponse = MutableLiveData<RegisterResponse?>()
    val registerResponse: LiveData<RegisterResponse?> = _registerResponse

    fun registerUser(nama: String, email: String, nomorHp: String, password: String, confirmPassword: String) {
        val request = RegisterRequest(
            nama_lengkap = nama,
            email = email,
            nomor_hp = nomorHp,
            password = password,
            confirm_password = confirmPassword
        )

        RetrofitClient.instance.registerUser(request).enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(call: Call<RegisterResponse>, response: Response<RegisterResponse>) {
                if (response.isSuccessful) {
                    _registerResponse.value = response.body()
                    Log.d("Register", "Success: ${response.body()}")
                } else {
                    Log.e("Register", "Error: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                Log.e("Register", "Failure: ${t.message}")
            }
        })
    }
}
