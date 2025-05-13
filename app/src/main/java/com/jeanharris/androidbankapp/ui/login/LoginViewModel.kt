package com.jeanharris.androidbankapp.ui.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jeanharris.androidbankapp.data.Env
import com.jeanharris.androidbankapp.DbRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    var state by mutableStateOf(LoginState())
        private set

    fun onUsernameChanged(newUsername: String) {
        state = state.copy(username = newUsername)
    }

    fun onPasswordChanged(newPassword: String) {
        state = state.copy(password = newPassword)
    }

    fun testDbConnection() {
        viewModelScope.launch(Dispatchers.IO) {
            val conn = DbRepository.connectToDatabase(
                dbUrl = Env.dbUrl,
                dbUser = Env.dbUser,
                dbPassword = Env.dbPassword
            )
            if (conn != null) {
                println("✅ Connected to DB")
                conn.close()
            } else {
                println("❌ Failed to connect to DB")
            }
        }
    }

    fun attemptLogin() {
        viewModelScope.launch(Dispatchers.IO) {
            val success = DbRepository.validateUserLogin(
                state.username,
                state.password
            )
            println(if (success) "✅ Login successful" else "❌ Login failed")
        }
    }
}