package com.example.myapplication

import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // final preview images
        val finalCone = findViewById<ImageView>(R.id.preview_cone)
        val finalCup = findViewById<ImageView>(R.id.preview_cup)
        val finalScoop = findViewById<ImageView>(R.id.preview_scoop)
        val finalCherry = findViewById<ImageView>(R.id.preview_cherry)
        val finalSprinkles = findViewById<ImageView>(R.id.preview_sprinkles)

        // Load all choices from SharedPreferences
        val sharedPref = getSharedPreferences("ScoopPrefs", Context.MODE_PRIVATE)

        val container = sharedPref.getString("CONTAINER_TYPE", "cone")
        val flavor = sharedPref.getString("DEFAULT_FLAVOR", "vanilla")
        val hasCherry = sharedPref.getBoolean("HAS_CHERRY", false)
        val hasSprinkles = sharedPref.getBoolean("HAS_SPRINKLES", false)

        // Position
        val scoopParams = finalScoop.layoutParams as FrameLayout.LayoutParams
        val sprinkleParams = finalSprinkles.layoutParams as FrameLayout.LayoutParams
        val cherryParams = finalCherry.layoutParams as FrameLayout.LayoutParams

        // Sizes of scoops and toppings
        scoopParams.width = dpToPx(220)
        scoopParams.height = dpToPx(220)
        sprinkleParams.width = dpToPx(220)
        sprinkleParams.height = dpToPx(220)

        // Cherry Settings
        cherryParams.width = dpToPx(95)
        cherryParams.height = dpToPx(95)
        cherryParams.gravity = Gravity.CENTER_HORIZONTAL or Gravity.BOTTOM
        cherryParams.topMargin = 0 // Clear any old top margins

        // Container
        if (container == "cone") {
            finalCone.visibility = View.VISIBLE
            finalCup.visibility = View.GONE
            scoopParams.bottomMargin = dpToPx(10)
            sprinkleParams.bottomMargin = dpToPx(10)
            cherryParams.bottomMargin = dpToPx(230)
        } else {
            finalCone.visibility = View.GONE
            finalCup.visibility = View.VISIBLE
            scoopParams.bottomMargin = dpToPx(-10)
            sprinkleParams.bottomMargin = dpToPx(-10)
            cherryParams.bottomMargin = dpToPx(210)
        }

        // Finalize Layout Changes
        finalScoop.layoutParams = scoopParams
        finalSprinkles.layoutParams = sprinkleParams
        finalCherry.layoutParams = cherryParams

        // Flavor Selection
        when (flavor) {
            "chocolate" -> finalScoop.setImageResource(R.drawable.chocolate)
            "strawberry" -> finalScoop.setImageResource(R.drawable.strawberry)
            "mint" -> finalScoop.setImageResource(R.drawable.mint)
            "mango" -> finalScoop.setImageResource(R.drawable.mango)
            else -> finalScoop.setImageResource(R.drawable.vanilla)
        }
        finalScoop.clearColorFilter()

        // Apply Toppings Visibility
        finalCherry.visibility = if (hasCherry) View.VISIBLE else View.GONE
        finalSprinkles.visibility = if (hasSprinkles) View.VISIBLE else View.GONE

        // Reset Button
        val btnReset = findViewById<Button>(R.id.btn_reset)
        btnReset.setOnClickListener {
            val intent = android.content.Intent(this, ContainerActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    // Helper function to convert DP to Pixels
    private fun dpToPx(dp: Int): Int {
        val density = resources.displayMetrics.density
        return (dp * density).toInt()
    }
}