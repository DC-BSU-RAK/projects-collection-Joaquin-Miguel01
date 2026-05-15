package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity

class SettingsActivity : AppCompatActivity() {

    private var selectedFlavor = "vanilla"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        // Link Preview Images
        val previewCone = findViewById<ImageView>(R.id.preview_cone)
        val previewCup = findViewById<ImageView>(R.id.preview_cup)
        val previewScoop = findViewById<ImageView>(R.id.preview_scoop)

        // 2. Setup the Preview based
        val sharedPref = getSharedPreferences("ScoopPrefs", Context.MODE_PRIVATE)
        val savedContainer = sharedPref.getString("CONTAINER_TYPE", "cone")

        if (savedContainer == "cone") {
            previewCone.visibility = View.VISIBLE
            previewCup.visibility = View.GONE
        } else {
            previewCone.visibility = View.GONE
            previewCup.visibility = View.VISIBLE
        }

        // Flavor Selection Logic
        val radioChocolate = findViewById<RadioButton>(R.id.radio_chocolate)
        val radioStrawberry = findViewById<RadioButton>(R.id.radio_strawberry)
        val radioMint = findViewById<RadioButton>(R.id.radio_mint)
        val radioMango = findViewById<RadioButton>(R.id.radio_mango)
        val radioVanilla = findViewById<RadioButton>(R.id.radio_vanilla)

        radioChocolate.setOnClickListener {
            selectedFlavor = "chocolate"
            previewScoop.setImageResource(R.drawable.chocolate)
            previewScoop.clearColorFilter()
        }

        radioStrawberry.setOnClickListener {
            selectedFlavor = "strawberry"
            previewScoop.setImageResource(R.drawable.strawberry)
            previewScoop.clearColorFilter()
        }

        radioMint.setOnClickListener {
            selectedFlavor = "mint"
            previewScoop.setImageResource(R.drawable.mint)
            previewScoop.clearColorFilter()
        }

        radioMango.setOnClickListener {
            selectedFlavor = "mango"
            previewScoop.setImageResource(R.drawable.mango)
            previewScoop.clearColorFilter()
        }

        radioVanilla.setOnClickListener {
            selectedFlavor = "vanilla"
            previewScoop.setImageResource(R.drawable.vanilla)
            previewScoop.clearColorFilter()
        }

        // Next Button
        val nextButton = findViewById<Button>(R.id.btn_save)

        nextButton.setOnClickListener {
            // Save the flavor choice
            val editor = sharedPref.edit()
            editor.putString("DEFAULT_FLAVOR", selectedFlavor)
            editor.apply()

            // Open the Toppings screen
            val intent = Intent(this, ToppingsActivity::class.java)
            startActivity(intent)
        }
    }
}