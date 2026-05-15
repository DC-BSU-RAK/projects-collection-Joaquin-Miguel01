package com.example.myapplication

import android.os.Bundle
import android.view.View
import android.view.animation.BounceInterpolator
import android.view.animation.OvershootInterpolator
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // SETUP DISPLAY LAYERS
        val displayBase = findViewById<ImageView>(R.id.display_base)
        val displayFlavor = findViewById<ImageView>(R.id.display_flavor)
        val displayCherry = findViewById<ImageView>(R.id.display_cherry)
        val displaySprinkles = findViewById<ImageView>(R.id.display_sprinkles)
        val displayFudge = findViewById<ImageView>(R.id.display_fudge_drizzle)

        // SETUP BUTTONS
        val btnCone = findViewById<ImageButton>(R.id.btn_cone)
        val btnCup = findViewById<ImageButton>(R.id.btn_cup)
        val btnChocolate = findViewById<ImageButton>(R.id.btn_chocolate)
        val btnMango = findViewById<ImageButton>(R.id.btn_mango)
        val btnStrawberry = findViewById<ImageButton>(R.id.btn_strawberry)
        val btnFudge = findViewById<ImageButton>(R.id.btn_fudge_drizzle)
        val btnCherry = findViewById<ImageButton>(R.id.btn_cherry)
        val btnSprinkles = findViewById<ImageButton>(R.id.btn_sprinkles)

        val infoCard = findViewById<CardView>(R.id.info_card)
        val btnCloseInfo = findViewById<Button>(R.id.btn_close_info)

        // Slide the card up when app starts
        infoCard.animate()
            .translationY(0f)
            .setDuration(900)
            .setStartDelay(500)
            .setInterpolator(OvershootInterpolator())
            .start()

        // Slide the card away when clicked
        btnCloseInfo.setOnClickListener {
            infoCard.animate()
                .translationY(2000f)
                .setDuration(600)
                .start()
        }

        // INITIAL STATE (The Lock)
        val flavorButtons = listOf(btnChocolate, btnMango, btnStrawberry)
        val toppingButtons = listOf(btnFudge, btnCherry, btnSprinkles)

        flavorButtons.forEach { it.isEnabled = false; it.alpha = 0.5f }
        toppingButtons.forEach { it.isEnabled = false; it.alpha = 0.5f }

        // STEP-BY-STEP LOGIC

        fun animateContainer(drawableId: Int) {
            displayBase.setImageResource(drawableId)
            displayBase.visibility = View.VISIBLE
            displayBase.scaleX = 0.7f
            displayBase.scaleY = 0.7f
            displayBase.animate().scaleX(1f).scaleY(1f).setDuration(300).setInterpolator(OvershootInterpolator()).start()
            flavorButtons.forEach { it.isEnabled = true; it.alpha = 1.0f }
        }

        btnCone.setOnClickListener { animateContainer(R.drawable.cone) }
        btnCup.setOnClickListener { animateContainer(R.drawable.cup) }

        fun animateFlavor(drawableId: Int) {
            displayFlavor.setImageResource(drawableId)
            displayFlavor.visibility = View.VISIBLE
            displayFlavor.alpha = 0f
            displayFlavor.animate().alpha(1f).setDuration(400).setInterpolator(OvershootInterpolator()).start()
            toppingButtons.forEach { it.isEnabled = true; it.alpha = 1.0f }
        }

        btnChocolate.setOnClickListener { animateFlavor(R.drawable.chocolate) }
        btnMango.setOnClickListener { animateFlavor(R.drawable.mango) }
        btnStrawberry.setOnClickListener { animateFlavor(R.drawable.strawberry) }

        // MULTI-TOPPING LOGIC

        btnFudge.setOnClickListener {
            if (displayFudge.visibility == View.VISIBLE) {
                displayFudge.visibility = View.GONE
            } else {
                displayFudge.setImageResource(R.drawable.fudge_drizzle)
                displayFudge.visibility = View.VISIBLE
                displayFudge.translationY = -30f
                displayFudge.animate().translationY(0f).setDuration(500).start()
            }
        }

        btnCherry.setOnClickListener {
            if (displayCherry.visibility == View.VISIBLE) {
                displayCherry.visibility = View.GONE
            } else {
                displayCherry.setImageResource(R.drawable.cherry)
                displayCherry.visibility = View.VISIBLE
                displayCherry.translationY = -500f
                displayCherry.animate()
                    .translationY(0f)
                    .setDuration(700)
                    .setInterpolator(BounceInterpolator())
                    .start()
            }
        }

        btnSprinkles.setOnClickListener {
            if (displaySprinkles.visibility == View.VISIBLE) {
                displaySprinkles.visibility = View.GONE
            } else {
                displaySprinkles.setImageResource(R.drawable.sprinkles)
                displaySprinkles.visibility = View.VISIBLE
                displaySprinkles.scaleX = 0f
                displaySprinkles.scaleY = 0f
                displaySprinkles.animate().scaleX(1f).scaleY(1f).setDuration(400).start()
            }
        }

        // EAT / RESET LOGIC
        val btnEat = findViewById<Button>(R.id.btn_eat)

        btnEat.setOnClickListener {
            val allLayers = listOf(displayBase, displayFlavor, displayCherry, displaySprinkles, displayFudge)

            for (layer in allLayers) {
                layer.animate()
                    .alpha(0f)
                    .translationY(1000f)
                    .setDuration(800)
                    .withEndAction {
                        layer.visibility = View.GONE
                        layer.translationY = 0f
                        layer.alpha = 1f
                    }
                    .start()
            }

            flavorButtons.forEach { it.isEnabled = false; it.alpha = 0.5f }
            toppingButtons.forEach { it.isEnabled = false; it.alpha = 0.5f }
        }
    }
}