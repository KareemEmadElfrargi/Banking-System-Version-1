package com.example.bank_app.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class LoginViewModel : ViewModel() {
    private val authentication = FirebaseAuth.getInstance()
    private val _loginStatus = MutableLiveData<Boolean>()
    val loginStatus : LiveData<Boolean> get() = _loginStatus

    private val _loadingStatus = MutableLiveData<Boolean>()
    val loadingStatus : LiveData<Boolean> get() = _loadingStatus

    private val _errorStatus = MutableLiveData<String>()
    val errorStatus : LiveData<String> get() = _errorStatus

    fun login(email:String , password:String){
        if (email.isEmpty() || password.isEmpty()){
            _errorStatus.value = "Please fill all the fields"
            return
        }

        _loadingStatus.value = true

        loginFirebase(email, password)
    }

    private fun loginFirebase(email: String, password: String) {
        authentication.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                _loadingStatus.value = false
                if (task.isSuccessful) {
                    _loginStatus.value = true

                } else {
                    _errorStatus.value = task.exception?.message ?: "Login failed"
                }
            }
    }
}