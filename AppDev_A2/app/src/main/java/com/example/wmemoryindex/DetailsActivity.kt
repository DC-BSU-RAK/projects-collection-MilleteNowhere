package com.example.wmemoryindex

import android.media.MediaPlayer
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        val memoryName = intent.getStringExtra("memoryName")

        val memoryData = when (memoryName) {

            "Cyclone" -> listOf(
                "Soul Memory",
                "Represents the element of wind and serves as Philip’s primary Soul Memory.",
                "Grants control over airflow and enhances mobility and speed.",
                "Optimized for balanced combat and evasive maneuvers.",
                "First Appearance: Episode 1 – The W Search.",
                "Primary Form: CycloneJoker"
            )

            "Joker" -> listOf(
                "Body Memory",
                "Symbolizes the trump card and is Shotaro’s primary Body Memory.",
                "Enhances agility, reflexes, and close-quarters combat skill.",
                "Forms the base combat framework of Kamen Rider W.",
                "First Appearance: Episode 1.",
                "Primary Form: CycloneJoker"
            )

            "Heat" -> listOf(
                "Soul Memory",
                "Embodies flame and combustion energy.",
                "Produces intense fire-based attacks and explosive strikes.",
                "Designed for high-impact close-range combat.",
                "First Appearance: Episode 3.",
                "Primary Form: HeatJoker"
            )

            "Metal" -> listOf(
                "Body Memory",
                "Represents strength and defensive capability.",
                "Boosts durability and enables staff-based combat.",
                "Used for heavy defensive and impact tactics.",
                "First Appearance: Episode 3.",
                "Primary Form: CycloneMetal"
            )

            "Luna" -> listOf(
                "Soul Memory",
                "Represents elasticity and distortion.",
                "Allows limb extension and bending of attack trajectories.",
                "Enables adaptive and unpredictable combat patterns.",
                "First Appearance: Episode 5.",
                "Primary Form: LunaJoker"
            )

            "Trigger" -> listOf(
                "Body Memory",
                "Represents precision and ranged combat.",
                "Enhances gun-based attacks and projectile accuracy.",
                "Optimized for distance-based engagements.",
                "First Appearance: Episode 5.",
                "Primary Form: CycloneTrigger"
            )

            "Fang" -> listOf(
                "Special Body Memory",
                "A sentient dinosaur-type Memory embodying instinct.",
                "Temporarily grants aggressive feral combat capability.",
                "Used in high-risk or overwhelming enemy situations.",
                "First Appearance: Episode 16.",
                "Primary Form: FangJoker"
            )

            "Xtreme" -> listOf(
                "Ultimate Memory",
                "Represents perfect synchronization between partners.",
                "Unlocks CycloneJokerXtreme form.",
                "Symbolizes maximum compatibility and balance.",
                "First Appearance: Episode 35.",
                "Primary Form: CycloneJokerXtreme"
            )

            else -> listOf("", "", "", "", "", "")
        }

        findViewById<TextView>(R.id.nameText).text = memoryName
        findViewById<TextView>(R.id.typeText).text = memoryData[0]
        findViewById<TextView>(R.id.powerText).text = memoryData[1]
        findViewById<TextView>(R.id.usageText).text = memoryData[2]
        findViewById<TextView>(R.id.appearanceText).text = memoryData[3]
        findViewById<TextView>(R.id.notableFormText).text = memoryData[4]

        findViewById<ImageView>(R.id.memoryImage)
            .setImageResource(getImage(memoryName))

        findViewById<Button>(R.id.backButton)
            .setOnClickListener { finish() }

        val sharedPref = getSharedPreferences("AppPrefs", MODE_PRIVATE)
        val soundOn = sharedPref.getBoolean("soundOn", true)

        if (soundOn) {
            playSound(memoryName)
        }
    }

    private fun playSound(name: String?) {
        val soundRes = when (name) {
            "Cyclone" -> R.raw.cyclone
            "Heat" -> R.raw.heat
            "Luna" -> R.raw.luna
            "Joker" -> R.raw.joker
            "Metal" -> R.raw.metal
            "Trigger" -> R.raw.trigger
            "Fang" -> R.raw.fang
            "Xtreme" -> R.raw.xtreme
            else -> null
        }

        if (soundRes != null) {
            val mp = MediaPlayer.create(this, soundRes)
            mp.setOnCompletionListener { it.release() }
            mp.start()
        }
    }

    private fun getImage(name: String?) = when(name) {
        "Cyclone" -> R.drawable.cyclone
        "Heat" -> R.drawable.heat
        "Luna" -> R.drawable.luna
        "Joker" -> R.drawable.joker
        "Metal" -> R.drawable.metal
        "Trigger" -> R.drawable.trigger
        "Fang" -> R.drawable.fang
        "Xtreme" -> R.drawable.xtreme
        else -> 0
    }
}