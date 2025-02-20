package com.example.bank_app.ui.login

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.bank_app.R
import com.example.bank_app.databinding.ActivityLoginBinding
import com.google.android.material.snackbar.Snackbar

class LoginActivity : AppCompatActivity() {
    private lateinit var _binding: ActivityLoginBinding
    private val loginViewModel : LoginViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(_binding.root)

        _binding.btnLogin.setOnClickListener {
            val email = _binding.edEmail.text.toString().trim()
            val password = _binding.etPassword.text.toString().trim()
            loginViewModel.login(email,password)
        }
        loginViewModel.loginStatus.observe(this){ isLoading ->
            _binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
        loginViewModel.loginStatus.observe(this){ isSuccessLogin ->
            if (isSuccessLogin){
                Snackbar.make(_binding.root,"Login Successful!",Snackbar.LENGTH_SHORT).show()
            }
        }
        loginViewModel.errorStatus.observe(this){ error ->
            Snackbar.make(_binding.root,error,Snackbar.LENGTH_SHORT).show()
        }

    }
}