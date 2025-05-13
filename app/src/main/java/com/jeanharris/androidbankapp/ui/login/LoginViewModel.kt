package com.jeanharris.androidbankapp.ui.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jeanharris.androidbankapp.BuildConfig
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
                dbUrl = BuildConfig.DB_URL,
                dbUser = BuildConfig.DB_USER,
                dbPassword = BuildConfig.DB_PASSWORD
            )
            if (conn != null) {
                println("✅ Connected to DB")
                conn.close()
            } else {
                println("❌ Failed to connect to DB")
            }
        }
    }
}