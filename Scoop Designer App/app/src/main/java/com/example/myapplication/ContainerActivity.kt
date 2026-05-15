package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ContainerActivity : AppCompatActivity() {

    // Default selection
    private var selectedContainer = "cone"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_container)

        // Link the Preview Images
        val previewCone = findViewById<ImageView>(R.id.preview_cone)
        val previewCup = findViewById<ImageView>(R.id.preview_cup)

        // Link the Selection Buttons
        val btnCone = findViewById<ImageButton>(R.id.btn_cone)
        val btnCup = findViewById<ImageButton>(R.id.btn_cup)
        val btnNext = findViewById<Button>(R.id.btn_next_container)

        btnCone.alpha = 1.0f
        btnCup.alpha = 0.5f

        // Logic for clicking the Cone button
        btnCone.setOnClickListener {
            selectedContainer = "cone"
            previewCone.visibility = View.VISIBLE
            previewCup.visibility = View.GONE

            btnCone.alpha = 1.0f
            btnCup.alpha = 0.5f
        }

        // Logic for clicking the Cup button
        btnCup.setOnClickListener {
            selectedContainer = "cup"
            previewCup.visibility = View.VISIBLE
            previewCone.visibility = View.GONE

            btnCup.alpha = 1.0f
            btnCone.alpha = 0.5f
        }

        // Logic for the NEXT button
        btnNext.setOnClickListener {
            // Save choice to SharedPreferences
            val sharedPref = getSharedPreferences("ScoopPrefs", Context.MODE_PRIVATE)
            sharedPref.edit().putString("CONTAINER_TYPE", selectedContainer).apply()

            // Move to Flavor selection (SettingsActivity)
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }

        val mainView = findViewById<View>(R.id.main)
        if (mainView != null) {
            ViewCompat.setOnApplyWindowInsetsListener(mainView) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
                insets
            }
        }
    }
}