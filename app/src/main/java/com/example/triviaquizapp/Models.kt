package com.example.triviaquizapp

import android.widget.Button

class StatFeed(val name: String, val image: Int);

class AllResults(
    val results: List<ResultFeed>,
    val response_code: Int
)

class QuizResults(
    val results: String
)

class ResultFeed(
    val category: String,
    val type: String,
    val difficulty: String,
    val question: String,
    val correct_answer: String,
    val incorrect_answers: ArrayList<String>
)

class JoinedFeed(
    val questions: ArrayList<String>,
    val answers: ArrayList<ArrayList<String>>,
    val correct_answers: ArrayList<String>
)

class DoneFeed(
    val qNumbers: String,
    val qCorrectAnswers: String,
    val qNegative: String
)