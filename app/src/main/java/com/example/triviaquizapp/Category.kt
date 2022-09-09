package com.example.triviaquizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast

class Category : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)
        supportActionBar?.hide()

        val startBtn: Button = findViewById(R.id.startBtn)
        val startBtn1: Button = findViewById(R.id.startBtn1)

        startBtn.setOnClickListener {
            val intent = Intent(this, Questions::class.java)
            startActivity(intent)
        }

        startBtn1.setOnClickListener {
            Toast.makeText(this, "Not implemented yet. Please choose random category", Toast.LENGTH_SHORT).show()
        }
    }
}