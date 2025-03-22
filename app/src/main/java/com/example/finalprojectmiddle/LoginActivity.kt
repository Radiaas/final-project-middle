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

        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

        // Cek apakah user sudah login
        val token = sharedPreferences.getString("TOKEN", null)
        if (!token.isNullOrEmpty()) {
            startActivity(Intent(this, WisataActivity::class.java))
            finish()
            return
        }

        setContentView(R.layout.activity_login)

        val emailInput = findViewById<EditText>(R.id.etEmail)
        val passwordInput = findViewById<EditText>(R.id.etPassword)
        val loginButton = findViewById<Button>(R.id.btnLogin)
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)

        val tvGoToRegister = findViewById<TextView>(R.id.tvGoToRegister)

        tvGoToRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

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

            if (response.status == "success" && response.data != null) {
                val token = response.data.token
                val editor = sharedPreferences.edit()
                editor.putString("TOKEN", token)
                editor.putInt("USER_ID", response.data.id)
                editor.putString("NAMA_LENGKAP", response.data.nama_lengkap)
                editor.putString("EMAIL", response.data.email)
                editor.putString("NOMOR_HP", response.data.nomor_hp)
                editor.apply()

                startActivity(Intent(this, WisataActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, response.message, Toast.LENGTH_SHORT).show()
            }
        })
    }
}
