package com.revilleza.userauth.pages

import android.os.Bundle
import android.util.Patterns
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.checkbox.MaterialCheckBox
import com.google.android.material.internal.MaterialCheckable
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.revilleza.userauth.R

class RegisterActivity : AppCompatActivity() {

    private lateinit var tilFirstname: TextInputLayout
    private lateinit var tilMiddlename: TextInputLayout
    private lateinit var tilLastname: TextInputLayout
    private lateinit var tilEmail: TextInputLayout
    private lateinit var tilPassword: TextInputLayout
    private lateinit var tilConfirmPassword: TextInputLayout

    private lateinit var etFirstName: TextInputEditText
    private lateinit var etMiddleName: TextInputEditText
    private lateinit var etLastName: TextInputEditText
    private lateinit var etEmail: TextInputEditText
    private lateinit var etPassword: TextInputEditText
    private lateinit var etConfirmPassword: TextInputEditText

    private lateinit var cbTerms: MaterialCheckBox
    private lateinit var btnRegister: MaterialButton
    private lateinit var tvGoLogin: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        tilFirstname = findViewById(R.id.tilFirstname)
        tilMiddlename = findViewById(R.id.tilMiddlename)
        tilLastname = findViewById(R.id.tilLastname)
        tilEmail = findViewById(R.id.tilEmail)
        tilPassword = findViewById(R.id.tilPassword)
        tilConfirmPassword = findViewById(R.id.tilConfirmPassword)

        etFirstName = findViewById(R.id.etFirstName)
        etMiddleName = findViewById(R.id.etMiddleName)
        etLastName = findViewById(R.id.etLastName)
        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        etConfirmPassword = findViewById(R.id.etConfirmPassword)

        cbTerms = findViewById(R.id.cbTerms)
        btnRegister = findViewById(R.id.btnRegister)
        tvGoLogin = findViewById(R.id.tvGoLogin)

        btnRegister.isEnabled = false

        val watcher = SimpleTextWatcher {
            validateRegister(showErrors = false)
            btnRegister.isEnabled = isRegisterValid()
        }

        etFirstName.addTextChangedListener(watcher)
        etLastName.addTextChangedListener(watcher)
        etEmail.addTextChangedListener(watcher)
        etPassword.addTextChangedListener(watcher)

        cbTerms.setOnCheckedChangeListener { _, _ ->
            btnRegister.isEnabled = isRegisterValid()
        }

        btnRegister.setOnClickListener {
            if (!cbTerms.isChecked) {
                Toast.makeText(
                    this,
                    "You must accept to the terms and conditions.",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            if (validateRegister(showErrors = true)) {
                val first = etFirstName.text.toString().trim()
                val middle = etMiddleName.text.toString().trim().ifBlank { "" }
                val last = etLastName.text.toString().trim()
                val email = etEmail.text.toString().trim()
                val password = etPassword.text.toString()

                // TODO next step: call api
            }
        }

        tvGoLogin.setOnClickListener {
            finish()
        }
    }

    private fun isRegisterValid(): Boolean {
        val first = etFirstName.text?.toString()?.trim().orEmpty()
        val last = etLastName.text?.toString()?.trim().orEmpty()
        val email = etEmail.text?.toString()?.trim().orEmpty()
        val password = etPassword.text?.toString().orEmpty()
        val confirm = etConfirmPassword.text?.toString().orEmpty()

        val nameOk = first.isNotBlank() && last.isNotBlank()
        val emailOk = email.isNotBlank() && Patterns.EMAIL_ADDRESS.matcher(email).matches()
        val passOk = password.isNotBlank() && password.length >= 8
        val confirmOk = confirm.isNotBlank() && confirm == password
        val termsOk = cbTerms.isChecked

        return nameOk && emailOk && passOk && confirmOk && termsOk
    }

    private fun validateRegister(showErrors: Boolean): Boolean {
        var isValid = true

        val first = etFirstName.text?.toString()?.trim().orEmpty()
        val last = etLastName.text?.toString()?.trim().orEmpty()
        val email = etEmail.text?.toString()?.trim().orEmpty()
        val password = etPassword.text?.toString().orEmpty()

        if (first.isBlank()) {
            if (showErrors) tilFirstname.error = "First name is required"
            isValid = false;
        } else {
            tilFirstname.error = null
        }

        tilMiddlename.error = null

        if (last.isBlank()) {
            if (showErrors) tilLastname.error = "Last name is required"
            isValid = false
        } else {
            tilLastname.error = null
        }

        if (email.isBlank()) {
            if (showErrors) tilEmail.error = "Email is required"
            isValid = false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            if (showErrors) tilEmail.error = "Enter a valid email address"
            isValid = false
        } else {
            tilEmail.error = null
        }

        if (password.isBlank()) {
            if (showErrors) tilPassword.error = "Password is required"
            isValid = false
        } else if (password.length < 8) {
            if (showErrors) tilPassword.error = "Password must be at least 8 characters"
            isValid = false
        } else {
            tilPassword.error = null
        }

        if (!showErrors) {
            tilFirstname.error = null
            tilMiddlename.error = null
            tilLastname.error = null
            tilEmail.error = null
            tilPassword.error = null
            tilConfirmPassword.error = null
        }

        return isValid
    }
}