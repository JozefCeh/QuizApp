package com.example.triviaquizapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import android.widget.Toolbar
import androidx.navigation.fragment.NavHostFragment

class MainActivity : AppCompatActivity() {

    var isRemember = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button = findViewById<Button>(R.id.button)
        val name = findViewById<EditText>(R.id.etName)

        val sharedPref = getSharedPreferences("addName", Context.MODE_PRIVATE)
        val edit = sharedPref.edit()

        isRemember = sharedPref.getBoolean("remember", false)

        if (isRemember) {
            val intent = Intent(this, Stats::class.java)
            startActivity(intent)
        }

        button.setOnClickListener {

            if (name.text.toString().isEmpty() ) {
                Toast.makeText(this@MainActivity, "Please, enter your name!", Toast.LENGTH_SHORT).show()
            }
            else if (name.text.length > 12) {
                Toast.makeText(this@MainActivity, "Your name is too long", Toast.LENGTH_SHORT).show()
            } else {
                edit.putString("name", name.text.toString())
                edit.putBoolean("remember", true)
                edit.commit()

                val intent = Intent(this, Stats::class.java)
                startActivity(intent)
            }
        }
    }
}