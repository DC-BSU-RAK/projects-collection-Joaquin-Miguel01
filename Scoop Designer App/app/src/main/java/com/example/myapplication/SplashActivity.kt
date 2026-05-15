package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContentView(com.example.myapplication.R.layout.activity_splash)

        val mainView = findViewById<android.view.View>(com.example.myapplication.R.id.main)

        if (mainView != null) {
            ViewCompat.setOnApplyWindowInsetsListener(mainView) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
                insets
            }
        }

        Handler(Looper.getMainLooper()).postDelayed({
            // Ensure ContainerActivity exists in your project!
            val intent = Intent(this, ContainerActivity::class.java)
            startActivity(intent)
            finish()
        }, 2500)
    }
}