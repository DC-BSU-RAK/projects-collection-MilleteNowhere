package com.example.wmemoryindex

import android.os.Bundle
import android.widget.Button
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val sharedPref = getSharedPreferences("AppPrefs", MODE_PRIVATE)

        val themeSwitch = findViewById<Switch>(R.id.themeSwitch)
        val soundSwitch = findViewById<Switch>(R.id.soundSwitch)

        themeSwitch.isChecked = sharedPref.getBoolean("darkMode", true)
        soundSwitch.isChecked = sharedPref.getBoolean("soundOn", true)

        themeSwitch.setOnCheckedChangeListener { _, isChecked ->
            sharedPref.edit().putBoolean("darkMode", isChecked).apply()

            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_YES
                )
            } else {
                AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_NO
                )
            }
        }

        soundSwitch.setOnCheckedChangeListener { _, isChecked ->
            sharedPref.edit().putBoolean("soundOn", isChecked).apply()
        }

        findViewById<Button>(R.id.backButton)
            .setOnClickListener { finish() }
    }
}