package com.example.alchemyapp

import android.app.Dialog
import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    // ============================
    // STATE VARIABLES
    // ============================

    private var slot1: String = ""
    private var slot2: String = ""
    private var fangActive: Boolean = false

    // ============================
    // STARTUP
    // ============================

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Long press Cyclone → Fang
        val cycloneBtn = findViewById<ImageView>(R.id.btnCyclone)

        cycloneBtn.setOnLongClickListener {

            fangActive = true
            cycloneBtn.setImageResource(R.drawable.fang)
            cycloneBtn.tag = "Fang"

            playSound(R.raw.fang)

            slot1 = "Fang"

            val resultText = findViewById<TextView>(R.id.tvResult)

            when {
                slot2 != "" -> resultText.text = "Fang + $slot2"
                else        -> resultText.text = "Memory 1: Fang"
            }

            true
        }
    }

    // ============================
    // AUDIO
    // ============================

    private fun playSound(soundRes: Int) {
        val mp = MediaPlayer.create(this, soundRes)
        mp.setOnCompletionListener { it.release() }
        mp.start()
    }

    // ============================
    // MEMORY SELECTION
    // Soul → Slot 1 | Body → Slot 2
    // ============================

    fun onElementClick(view: View) {

        val selectedMemory = view.tag.toString()

        // Play memory sound
        when (selectedMemory) {
            "Cyclone" -> playSound(R.raw.cyclone)
            "Heat"    -> playSound(R.raw.heat)
            "Luna"    -> playSound(R.raw.luna)
            "Fang"    -> playSound(R.raw.fang)
            "Joker"   -> playSound(R.raw.joker)
            "Metal"   -> playSound(R.raw.metal)
            "Trigger" -> playSound(R.raw.trigger)
        }

        // Soul → Slot 1
        if (selectedMemory == "Cyclone" ||
            selectedMemory == "Heat"    ||
            selectedMemory == "Luna"    ||
            selectedMemory == "Fang"
        ) {
            slot1 = selectedMemory
        }

        // Body → Slot 2
        if (selectedMemory == "Joker"   ||
            selectedMemory == "Metal"   ||
            selectedMemory == "Trigger"
        ) {
            slot2 = selectedMemory
        }

        // Update display ONLY
        val resultText = findViewById<TextView>(R.id.tvResult)

        when {
            slot1 != "" && slot2 != "" -> resultText.text = "$slot1 + $slot2"
            slot1 != ""                -> resultText.text = "Memory 1: $slot1"
            slot2 != ""                -> resultText.text = "Memory 2: $slot2"
            else                       -> resultText.text = "Insert Gaia Memories"
        }
    }

    // ============================
    // HENSHIN BUTTON
    // Sequence: Henshin SFX → Mem1 SFX → Mem2 SFX → VFX
    // ============================

    fun onForgeClick(view: View) {

        val resultText = findViewById<TextView>(R.id.tvResult)

        if (slot1 == "" || slot2 == "") {
            resultText.text = "Insert Gaia Memories!"
            return
        }

        // ✅ Trigger VFX IMMEDIATELY
        performTransformation()

        // ✅ Then run audio sequence in background
        val henshinSFX = MediaPlayer.create(this, R.raw.henshin)

        henshinSFX.setOnCompletionListener {
            it.release()

            val mem1Sound = when (slot1) {
                "Cyclone" -> R.raw.cyclone
                "Heat"    -> R.raw.heat
                "Luna"    -> R.raw.luna
                "Fang"    -> R.raw.fang
                else      -> null
            }

            if (mem1Sound != null) {
                val mem1SFX = MediaPlayer.create(this, mem1Sound)

                mem1SFX.setOnCompletionListener {
                    it.release()

                    val mem2Sound = when (slot2) {
                        "Joker"   -> R.raw.joker
                        "Metal"   -> R.raw.metal
                        "Trigger" -> R.raw.trigger
                        else      -> null
                    }

                    if (mem2Sound != null) {
                        val mem2SFX = MediaPlayer.create(this, mem2Sound)
                        mem2SFX.setOnCompletionListener { it.release() }
                        mem2SFX.start()
                    }
                }

                mem1SFX.start()
            }
        }

        henshinSFX.start()
    }

    // ============================
    // TRANSFORMATION VFX
    // ============================

    private fun performTransformation() {

        val resultText = findViewById<TextView>(R.id.tvResult)
        val resultImg  = findViewById<ImageView>(R.id.imgResult)
        val henshinBtn = findViewById<ImageView>(R.id.btnHenshin)
        val flashView  = findViewById<View>(R.id.flashView)

        if (slot1 == "" || slot2 == "") return

        val combo = setOf(slot1, slot2)

        val (formName, imageRes) = when {
            combo == setOf("Fang",    "Joker")   -> "FANG-JOKER"      to R.drawable.form_fj
            combo == setOf("Cyclone", "Joker")   -> "CYCLONE-JOKER"   to R.drawable.form_cj
            combo == setOf("Heat",    "Metal")   -> "HEAT-METAL"      to R.drawable.form_hm
            combo == setOf("Luna",    "Trigger") -> "LUNA-TRIGGER"    to R.drawable.form_lt
            combo == setOf("Cyclone", "Metal")   -> "CYCLONE-METAL"   to R.drawable.form_cm
            combo == setOf("Cyclone", "Trigger") -> "CYCLONE-TRIGGER" to R.drawable.form_ct
            combo == setOf("Heat",    "Joker")   -> "HEAT-JOKER"      to R.drawable.form_hj
            combo == setOf("Heat",    "Trigger") -> "HEAT-TRIGGER"    to R.drawable.form_ht
            combo == setOf("Luna",    "Joker")   -> "LUNA-JOKER"      to R.drawable.form_lj
            combo == setOf("Luna",    "Metal")   -> "LUNA-METAL"      to R.drawable.form_lm
            else                                 -> "UNSTABLE FORM"   to R.drawable.form_error
        }

        resultText.text = "HENSHIN!\n$formName"
        resultImg.setImageResource(imageRes)

        // Button Pulse
        henshinBtn.animate()
            .scaleX(1.1f)
            .scaleY(1.1f)
            .setDuration(100)
            .withEndAction {
                henshinBtn.animate()
                    .scaleX(1f)
                    .scaleY(1f)
                    .setDuration(100)
                    .start()
            }
            .start()

        // Flash
        flashView.alpha = 0f
        flashView.visibility = View.VISIBLE

        flashView.animate()
            .alpha(0.9f)
            .setDuration(100)
            .withEndAction {
                flashView.animate()
                    .alpha(0f)
                    .setDuration(200)
                    .start()
            }
            .start()

        // Fade + Scale
        resultImg.alpha  = 0f
        resultImg.scaleX = 0.8f
        resultImg.scaleY = 0.8f

        resultImg.animate()
            .alpha(1f)
            .scaleX(1f)
            .scaleY(1f)
            .setDuration(300)
            .start()

        // Fang Snap
        if (formName == "FANG-JOKER") {
            resultImg.rotation = -15f
            resultImg.animate()
                .rotation(0f)
                .setDuration(300)
                .start()
        }
    }

    // ============================
    // CLEAR / RESET
    // ============================

    fun onClearClick(view: View) {

        slot1 = ""
        slot2 = ""
        fangActive = false

        findViewById<TextView>(R.id.tvResult).text = "Insert Memories"
        findViewById<ImageView>(R.id.imgResult).setImageResource(0)

        val cycloneBtn = findViewById<ImageView>(R.id.btnCyclone)
        cycloneBtn.setImageResource(R.drawable.cyclone)
        cycloneBtn.tag = "Cyclone"
    }

    // ============================
    // INFO MODAL
    // ============================

    fun showInfoModal(view: View) {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_info)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        val closeBtn = dialog.findViewById<Button>(R.id.btnClose)
        closeBtn.setOnClickListener { dialog.dismiss() }
        dialog.show()
    }
}