package com.example.triviaquizapp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity

class DoneAdapter(private val context: Context,
                  private val info: DoneFeed
): BaseAdapter() {

    override fun getCount(): Int {
        return 1
    }

    override fun getItem(position: Int): Any {
        return  position.toLong()
    }

    override fun getItemId(position: Int): Long {
        return  position.toLong()
    }

    override fun getView(position: Int, view: View?, viewGroup: ViewGroup?): View {
        val layoutInflater: LayoutInflater= LayoutInflater.from(context)
        val statRow: View = layoutInflater.inflate(R.layout.donelist, viewGroup, false)

        statRow.findViewById<TextView>(R.id.q_number).text = "No. of Question: ${info.qNumbers}"
        statRow.findViewById<TextView>(R.id.correct).text = "Correct Answer: ${info.qCorrectAnswers}"
        statRow.findViewById<TextView>(R.id.negative).text = "Negative Answer: ${info.qNegative}"


        return statRow

    }
}