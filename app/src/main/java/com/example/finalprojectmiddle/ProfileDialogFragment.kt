package com.example.finalprojectmiddle

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        val view = LayoutInflater.from(context).inflate(R.layout.dialog_profile, null)
        dialog.setContentView(view)

        val textName = view.findViewById<TextView>(R.id.textName)
        val textEmail = view.findViewById<TextView>(R.id.textEmail)
        val textPhone = view.findViewById<TextView>(R.id.textPhone)
        val btnClose = view.findViewById<Button>(R.id.btnClose)

        btnClose.setOnClickListener { dismiss() }

        val token = getToken(requireContext())
        if (token.isNotEmpty()) {
            fetchProfile(token, textName, textEmail, textPhone)
        } else {
            showToast("Token tidak ditemukan, silakan login kembali!")
            dismiss()
        }

        return dialog
    }

    private fun fetchProfile(token: String, textName: TextView, textEmail: TextView, textPhone: TextView) {
        RetrofitClient.instance.getProfile(token = token).enqueue(object : Callback<ProfileResponse> {
            override fun onResponse(call: Call<ProfileResponse>, response: Response<ProfileResponse>) {
                if (response.isSuccessful && response.body()?.status == "success") {
                    val profile = response.body()?.data
                    textName.text = profile?.nama_lengkap ?: "Tidak Ada Data"
                    textEmail.text = profile?.email ?: "Tidak Ada Data"
                    textPhone.text = profile?.nomor_hp ?: "Tidak Ada Data"
                } else {
                    showToast("Gagal mendapatkan profil.")
                    dismiss()
                }
            }

            override fun onFailure(call: Call<ProfileResponse>, t: Throwable) {
                showToast("Kesalahan: ${t.message}")
                dismiss()
            }
        })
    }

    private fun getToken(context: Context): String {
        val sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        return sharedPreferences.getString("TOKEN", "") ?: ""
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}
