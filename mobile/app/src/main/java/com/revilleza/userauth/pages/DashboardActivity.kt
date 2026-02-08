package com.revilleza.userauth.pages

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.revilleza.userauth.R
import com.revilleza.userauth.data.TokenManager

class DashboardActivity : AppCompatActivity() {

    private lateinit var tvTokenPreview: TextView
    private lateinit var btnLogout: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        tvTokenPreview = findViewById(R.id.tvTokenPreview)
        btnLogout = findViewById(R.id.btnLogout)

        val token = TokenManager.getToken(this)

        btnLogout.setOnClickListener {
            TokenManager.clearToken(this)

            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()

        val token = TokenManager.getToken(this)
        if(token.isNullOrBlank()) {
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }
}