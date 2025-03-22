package com.example.finalprojectmiddle

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity

class RegisterActivity : AppCompatActivity() {
    private val viewModel: RegisterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val etNama = findViewById<EditText>(R.id.etNama)
        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etNomorHp = findViewById<EditText>(R.id.etNomorHp)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val etConfirmPassword = findViewById<EditText>(R.id.etConfirmPassword)
        val btnRegister = findViewById<Button>(R.id.btnRegister)
        val tvGoToLogin = findViewById<TextView>(R.id.tvGoToLogin)

        // Navigasi ke LoginActivity
        tvGoToLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        btnRegister.setOnClickListener {
            val nama = etNama.text.toString()
            val email = etEmail.text.toString()
            val nomorHp = etNomorHp.text.toString()
            val password = etPassword.text.toString().trim() //mencegah spasi di dalam inputan user
            val confirmPassword = etConfirmPassword.text.toString()

            if (nama.isNotEmpty() && email.isNotEmpty() && nomorHp.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty()) {
                if (password == confirmPassword) {
                    viewModel.registerUser(nama, email, nomorHp, password, confirmPassword)
                } else {
                    Toast.makeText(this, "Password dan Konfirmasi Password tidak cocok!", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Harap isi semua data!", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.registerResponse.observe(this) { response ->
            response?.let {
                if (it.status == "success") {
                    Toast.makeText(this, "Registrasi berhasil!", Toast.LENGTH_SHORT).show()

                    // Setelah sukses daftar, langsung pindah ke LoginActivity
                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(this, "Registrasi gagal: ${it.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
