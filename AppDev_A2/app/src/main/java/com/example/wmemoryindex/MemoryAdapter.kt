package com.example.wmemoryindex

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView

class MemoryAdapter(
    context: Context,
    private val memoryNames: Array<String>
) : ArrayAdapter<String>(context, R.layout.item_memory, memoryNames) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val view = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.item_memory, parent, false)

        val nameText = view.findViewById<TextView>(R.id.memoryNameText)
        val icon = view.findViewById<ImageView>(R.id.memoryIcon)

        val name = memoryNames[position]
        nameText.text = name

        val imageRes = when (name) {
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

        if (imageRes != 0) {
            icon.setImageResource(imageRes)
        }

        return view
    }
}