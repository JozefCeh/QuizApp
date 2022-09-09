package com.example.triviaquizapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.GridView
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class Stats : AppCompatActivity() {

    lateinit var sharedPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stats)

        setSupportActionBar(findViewById(R.id.menu_main))

        val name = findViewById<TextView>(R.id.tvName)
         sharedPref = getSharedPreferences("addName", Context.MODE_PRIVATE)


        val namee = sharedPref.getString("name", "")
        name.text = "Hi " +namee+ "!"

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.stats -> {
                val intent = Intent(this, Stats::class.java)
                startActivity(intent)

            }
            R.id.category -> {
                val intent = Intent(this, Category::class.java)
                startActivity(intent)
            }
            R.id.exit -> {
                val editor: SharedPreferences.Editor = sharedPref.edit()
                editor.clear()
                editor.commit()

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)

            }
        }
        return true
    }

}