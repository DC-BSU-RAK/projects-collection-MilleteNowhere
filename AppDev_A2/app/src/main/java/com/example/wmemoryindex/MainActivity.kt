package com.example.wmemoryindex

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private val memoryNames = arrayOf(
        "Cyclone",
        "Heat",
        "Luna",
        "Joker",
        "Metal",
        "Trigger",
        "Fang",
        "Xtreme"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val listView = findViewById<ListView>(R.id.memoryListView)
        val adapter = MemoryAdapter(this, memoryNames)
        listView.adapter = adapter

        listView.setOnItemClickListener { _, _, position, _ ->
            val intent = Intent(this, DetailsActivity::class.java)
            intent.putExtra("memoryName", memoryNames[position])
            startActivity(intent)
        }

        findViewById<Button>(R.id.settingsButton).setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }

        findViewById<Button>(R.id.infoButton).setOnClickListener {
            showInfoDialog()
        }
    }

    private fun showInfoDialog() {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_info)

        dialog.findViewById<Button>(R.id.closeButton)
            .setOnClickListener { dialog.dismiss() }

        dialog.show()
    }
}