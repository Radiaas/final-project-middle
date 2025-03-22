package com.example.finalprojectmiddle

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch

class WisataActivity : AppCompatActivity() {

    private val wisataViewModel: WisataViewModel by viewModels()
    private lateinit var wisataAdapter: WisataAdapter
    private lateinit var searchView: SearchView
    private lateinit var textNoData: TextView
    private lateinit var btnLogout: Button
    private lateinit var btnProfile: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wisata)

        initViews()
        setupRecyclerView()
        observeViewModel()
        handleSearch()
        handleLogout()

        btnProfile.setOnClickListener {
            showProfileDialog()
        }
    }

    private fun initViews() {
        searchView = findViewById(R.id.searchView)
        textNoData = findViewById(R.id.textNoData)
        btnLogout = findViewById(R.id.btnLogout)
        btnProfile = findViewById(R.id.btnProfile)
    }

    private fun setupRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        wisataAdapter = WisataAdapter()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = wisataAdapter
    }

    private fun observeViewModel() {
        val token = getToken()
        if (token.isNotEmpty()) {
            wisataViewModel.fetchWisata(token)
        } else {
            Log.e("WisataActivity", "Token tidak ditemukan! Pastikan login berhasil.")
        }

        wisataViewModel.wisataList.observe(this) { list ->
            if (list.isEmpty()) {
                textNoData.visibility = View.VISIBLE
            } else {
                textNoData.visibility = View.GONE
                wisataAdapter.submitList(list)
            }
        }

        wisataViewModel.errorMessage.observe(this) { message ->
            showToast(message)
        }
    }

    private fun handleSearch() {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                val token = getToken()
                if (query.isNullOrEmpty()) {
                    wisataViewModel.fetchWisata(token)
                } else {
                    wisataViewModel.searchWisata(token, query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrEmpty()) {
                    wisataViewModel.fetchWisata(getToken())
                }
                return false
            }
        })
    }

    private fun handleLogout() {
        btnLogout.setOnClickListener {
            logoutUser()
        }
    }

    private fun logoutUser() {
        val token = getToken()
        if (token.isEmpty()) {
            showToast("Token tidak ditemukan, silakan login kembali!")
            navigateToLogin()
            return
        }

        lifecycleScope.launch {
            try {
                val response = RetrofitClient.instance.logoutUser("logout", token)
                if (response.isSuccessful && response.body()?.status == "success") {
                    clearUserSession()
                    navigateToLogin()
                } else {
                    showToast("Gagal logout, coba lagi!")
                }
            } catch (e: Exception) {
                showToast("Error: ${e.message}")
                Log.e("LogoutError", e.message.toString())
            }
        }
    }

    private fun getToken(): String {
        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("TOKEN", "") ?: ""
        Log.d("WisataActivity", "Token yang ditemukan: $token") // Log token
        return token
    }

    private fun clearUserSession() {
        getSharedPreferences("MyPrefs", Context.MODE_PRIVATE).edit().clear().apply()
    }

    private fun navigateToLogin() {
        startActivity(Intent(this, LoginActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        })
        finish()
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun showProfileDialog() {
        val profileDialog = ProfileDialogFragment()
        profileDialog.show(supportFragmentManager, "ProfileDialog")
    }
}
