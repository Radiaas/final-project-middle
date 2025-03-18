package com.example.finalprojectmiddle

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer

class LoginActivity : AppCompatActivity() {
    private val loginViewModel: LoginViewModel by viewModels()
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

        val emailInput = findViewById<EditText>(R.id.etEmail)
        val passwordInput = findViewById<EditText>(R.id.etPassword)
        val loginButton = findViewById<Button>(R.id.btnLogin)
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)

        loginButton.setOnClickListener {
            val email = emailInput.text.toString().trim()
            val password = passwordInput.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Email dan Password harus diisi!", Toast.LENGTH_SHORT).show()
            } else {
                progressBar.visibility = ProgressBar.VISIBLE
                loginViewModel.login(email, password)
            }
        }

        loginViewModel.loginResponse.observe(this, Observer { response ->
            progressBar.visibility = ProgressBar.GONE

            Log.d("LoginActivity", "Response login: $response") // Debug seluruh response

            if (response.status == "success" && response.data != null) {
                val token = response.data.token
                Log.d("LoginActivity", "Token diterima: $token") // Debug token yang diterima

                // Simpan token dan data user ke SharedPreferences
                val editor = sharedPreferences.edit()
                editor.putString("TOKEN", token)
                editor.putInt("USER_ID", response.data.id)
                editor.putString("NAMA_LENGKAP", response.data.nama_lengkap)
                editor.putString("EMAIL", response.data.email)
                editor.putString("NOMOR_HP", response.data.nomor_hp)
                editor.apply()

                // Debug: Cek apakah data benar-benar tersimpan
                val savedToken = sharedPreferences.getString("TOKEN", "Tidak ada token")
                Log.d("LoginActivity", "Token tersimpan di SharedPreferences: $savedToken")

                // Pindah ke WisataActivity
                val intent = Intent(this, WisataActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Log.e("LoginActivity", "Login gagal: ${response.message}") // Debug jika login gagal
                Toast.makeText(this, response.message, Toast.LENGTH_SHORT).show()
            }
        })

    }
}
