package com.revilleza.userauth.pages

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.revilleza.userauth.R
import com.revilleza.userauth.data.TokenManager
import com.revilleza.userauth.network.ApiClient
import com.revilleza.userauth.network.dto.LoginRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.w3c.dom.Text

class LoginActivity : AppCompatActivity() {

    private lateinit var tilEmail: TextInputLayout
    private lateinit var tilPassword: TextInputLayout
    private lateinit var etEmail: TextInputEditText
    private lateinit var etPassword: TextInputEditText
    private lateinit var btnLogin: MaterialButton
    private lateinit var tvGoRegister: TextView

    private val uiScope = CoroutineScope(Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        tilEmail = findViewById(R.id.tilEmail)
        tilPassword = findViewById(R.id.tilPassword)
        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        btnLogin = findViewById(R.id.btnLogin)
        tvGoRegister = findViewById(R.id.tvGoRegister)

        etEmail.addTextChangedListener(SimpleTextWatcher { tilEmail.error = null })
        etPassword.addTextChangedListener(SimpleTextWatcher { tilPassword.error = null })

        btnLogin.setOnClickListener {
            if(validateLogin(showErrors = true)) {
                val email = etEmail.text.toString().trim()
                val password = etPassword.text.toString()

                btnLogin.isEnabled = false

                uiScope.launch {
                    try{
                        val api = ApiClient.createAuthApi(applicationContext)
                        val res = withContext(Dispatchers.IO) {
                            api.login(LoginRequest(email, password))
                        }

                        val token = res.resolvedToken()
                        if(token.isNullOrBlank()) {
                            Toast.makeText(this@LoginActivity, "Login failed: no token returned", Toast.LENGTH_LONG).show()
                            return@launch
                        }

                        TokenManager.saveToken(this@LoginActivity, token)
                        goToDashboardClearStack()
                    } catch(e: Exception) {
                        Toast.makeText(this@LoginActivity, "Login failed: ${e.message}", Toast.LENGTH_LONG).show()
                    } finally {
                        btnLogin.isEnabled = isLoginValid()
                    }
                }
            }
        }

        tvGoRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    override fun onStart() {
        super.onStart()

        val token = TokenManager.getToken(this)
        if(!token.isNullOrBlank()) {
            goToDashboardClearStack()
        }
    }

    private fun goToDashboardClearStack() {
        val intent = Intent(this, DashboardActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    private fun isLoginValid(): Boolean {
        val email = etEmail.text?.toString()?.trim().orEmpty()
        val password = etPassword.text?.toString().orEmpty()

        val emailOk = email.isNotBlank()
        val passOk = password.isNotBlank()

        return emailOk && passOk
    }

    private fun validateLogin(showErrors: Boolean): Boolean {
        var isValid = true

        val email = etEmail.text?.toString()?.trim().orEmpty()
        val password = etPassword.text?.toString().orEmpty()

        if (email.isBlank()) {
            if(showErrors) tilEmail.error = "Email is required"
            isValid = false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            if(showErrors) tilEmail.error = "Enter a valid email address"
            isValid = false
        } else {
            tilEmail.error = null
        }

        if (password.isBlank()) {
            if(showErrors) tilPassword.error = "Password is required"
            isValid = false
        } else if (password.length < 8) {
            if(showErrors) tilPassword.error = "Password must be at least 8 characters"
            isValid = false
        } else {
            tilPassword.error = null
        }

        return isValid
    }
}