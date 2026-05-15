package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class ToppingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_toppings)

        val previewCone = findViewById<ImageView>(R.id.preview_cone)
        val previewCup = findViewById<ImageView>(R.id.preview_cup)
        val previewScoop = findViewById<ImageView>(R.id.preview_scoop)
        val previewCherry = findViewById<ImageView>(R.id.preview_cherry)
        val previewSprinkles = findViewById<ImageView>(R.id.preview_sprinkles)

        val sharedPref = getSharedPreferences("ScoopPrefs", Context.MODE_PRIVATE)
        val container = sharedPref.getString("CONTAINER_TYPE", "cone")
        val flavor = sharedPref.getString("DEFAULT_FLAVOR", "vanilla")

        // Position & Size Logic
        val scoopParams = previewScoop.layoutParams as FrameLayout.LayoutParams
        val sprinkleParams = previewSprinkles.layoutParams as FrameLayout.LayoutParams
        val cherryParams = previewCherry.layoutParams as FrameLayout.LayoutParams

        // Force the cherry size
        cherryParams.width = dpToPx(85)
        cherryParams.height = dpToPx(85)

        // Margin for toppings
        cherryParams.topMargin = 0
        cherryParams.gravity = android.view.Gravity.CENTER_HORIZONTAL or android.view.Gravity.BOTTOM

        if (container == "cone") {
            previewCone.visibility = View.VISIBLE
            previewCup.visibility = View.GONE

            // Tall cone settings
            scoopParams.bottomMargin = dpToPx(100)
            sprinkleParams.bottomMargin = dpToPx(100)

            cherryParams.bottomMargin = dpToPx(220)
        } else {
            previewCone.visibility = View.GONE
            previewCup.visibility = View.VISIBLE

            // Shorter cup adjustment
            scoopParams.bottomMargin = dpToPx(60)
            sprinkleParams.bottomMargin = dpToPx(60)
            // cherry on cup margin
            cherryParams.bottomMargin = dpToPx(180)
        }

        // Apply the changes to the views
        previewScoop.layoutParams = scoopParams
        previewSprinkles.layoutParams = sprinkleParams
        previewCherry.layoutParams = cherryParams

        // Load Actual Flavor
        when (flavor) {
            "chocolate" -> previewScoop.setImageResource(R.drawable.chocolate)
            "strawberry" -> previewScoop.setImageResource(R.drawable.strawberry)
            "mint" -> previewScoop.setImageResource(R.drawable.mint)
            "mango" -> previewScoop.setImageResource(R.drawable.mango)
            else -> previewScoop.setImageResource(R.drawable.vanilla)
        }
        previewScoop.clearColorFilter()

        // Toppings Logic
        val checkCherry = findViewById<CheckBox>(R.id.check_cherry)
        val checkSprinkles = findViewById<CheckBox>(R.id.check_sprinkles)

        checkCherry.setOnCheckedChangeListener { _, isChecked ->
            previewCherry.visibility = if (isChecked) View.VISIBLE else View.GONE
        }

        checkSprinkles.setOnCheckedChangeListener { _, isChecked ->
            previewSprinkles.visibility = if (isChecked) View.VISIBLE else View.GONE
        }

        // Finish Button
        findViewById<Button>(R.id.btn_finish).setOnClickListener {
            val editor = sharedPref.edit()
            editor.putBoolean("HAS_CHERRY", checkCherry.isChecked)
            editor.putBoolean("HAS_SPRINKLES", checkSprinkles.isChecked)
            editor.apply()

            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    // Helper function to convert DP to Pixels
    private fun dpToPx(dp: Int): Int {
        val density = resources.displayMetrics.density
        return (dp * density).toInt()
    }
}