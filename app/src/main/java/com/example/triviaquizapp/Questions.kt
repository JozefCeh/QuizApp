package com.example.triviaquizapp


import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import com.google.gson.GsonBuilder
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList
import kotlin.concurrent.timer
import kotlin.math.abs
import kotlin.time.DurationUnit


class Questions : AppCompatActivity() {
    companion object {
        var allJoined: ArrayList<JoinedFeed> = ArrayList()
        var questionNr: Int = 0
        var selectedAnswers: ArrayList<String> = ArrayList()
        var isCorrect: Int = 0
        var isFailed: Int = 0
    }

    private lateinit var timer: CountDownTimer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_questions)
        val endpoint: String = "https://opentdb.com/api.php?amount=5&difficulty=easy&type=multiple";
        val questions: ArrayList<String> = ArrayList()
        val allanswers: ArrayList<ArrayList<String>> = ArrayList()
        val allcorrectanswers: ArrayList<String> = ArrayList()
        val donelayout: ConstraintLayout = findViewById(R.id.done)
        donelayout.visibility = View.GONE
        supportActionBar?.hide()

        val httpAsync = endpoint
            .httpGet()
            .responseString { request, response, result ->
                when (result) {
                    is Result.Failure -> {
                        val ex = result.getException()
                        println(ex)
                    }
                    is Result.Success -> {
                        val data: String = result.get()
                        val gson = GsonBuilder().create()

                        val mainData: AllResults = gson.fromJson(data, AllResults::class.java)
                        for ((index, value) in mainData.results.withIndex()) {

                            val mainFeed = mainData.results
                            val question = mainFeed[index].question
                            questions.add(question)

                            val answers = mainFeed[index].incorrect_answers
                            answers.add((0..3).random(), mainFeed[index].correct_answer)
                            allanswers.add(answers)

                            val canswers = mainFeed[index].correct_answer
                            allcorrectanswers.add(canswers)
                        }
                    }
                }
            }

        httpAsync.join()

        allJoined.add(
            JoinedFeed(
                questions = questions,
                answers = allanswers,
                correct_answers = allcorrectanswers
            )
        )
        startQuiz()
    }

    private fun startQuiz() {
        val answerTe = findViewById<TextView>(R.id.answer_text)
        val time = findViewById<TextView>(R.id.timer)
        val nextbtn = findViewById<ImageButton>(R.id.next_btn);
        val totalnum: TextView = findViewById<TextView>(R.id.total_num);
        val mainquestion: TextView = findViewById<TextView>(R.id.main_question);
        val donelayout: ConstraintLayout = findViewById(R.id.done);
        val quizlayout: ConstraintLayout = findViewById(R.id.quiz);
        val donepop: ListView = findViewById(R.id.done_pop);
        val qnumber: TextView = findViewById(R.id.textView6)

        timer = object: CountDownTimer(30000, 1) {
            override fun onTick(millisUntilFinished: Long) {
              //  time.text ="Time left: " + millisUntilFinished.toString()


                var sDuration:String= String.format(Locale.ENGLISH,
                    "%02d:%02d",
                    TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                    TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished)-TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)))
                    time.text ="Time left: " +sDuration
            }

            override fun onFinish() {
                Toast.makeText(this@Questions, "Time is up", Toast.LENGTH_SHORT).show()
            }

        }


        // Display Number
        var questionNum = questionNr

        // Get current Question
        val currentQuestion = allJoined[0].questions[questionNr]

        // Grab the listview
        val answerListView: ListView = findViewById(R.id.answers_container)

        // Increase the Display number to +1
        questionNum++
        totalnum.text = "${questionNum.toString()}/${allJoined[0].questions.count()}"
        qnumber.text = "${questionNum.toString()}"


        // Set the first Question
        mainquestion.text = currentQuestion

        // Set the first Question Answers
        var qanswers: ArrayList<String> = allJoined[0].answers[questionNr]
        setAnswers(qanswers)

        answerListView.setOnItemClickListener { parent, view, position, id ->
            val clickedID = id.toInt()
            val correctanswer = allJoined[0].correct_answers[questionNr]
           Toast.makeText(this, "Correct answer is: ${correctanswer}", Toast.LENGTH_SHORT).show()
            val selectedanswer = allJoined[0].answers[questionNr][clickedID]

            val answerIsCorrect = selectedanswer == correctanswer

         //   onStart()

            // Check if answer is correct
            if (answerIsCorrect) {
                isCorrect+= 1
            } else {
                isFailed = 0
            }

            if(questionNr == allJoined[0].questions.count() -1 && questionNum === 5){
                quizlayout.visibility = View.GONE
                donelayout.visibility= View.VISIBLE

                onStop()

                val info: DoneFeed = DoneFeed(
                    qNumbers = "${allJoined[0].questions.count()}",
                    qCorrectAnswers = "$isCorrect",
                    qNegative = "$isFailed"
                )

                donepop.adapter = DoneAdapter(this, info)

            } else {
                questionNum++
                questionNr++
            }

            nextbtn.setOnClickListener() {
                    totalnum.text = "${questionNum.toString()}/${allJoined[0].questions.count()}"
                    qnumber.text = "${questionNum.toString()}"
                    mainquestion.text = allJoined[0].questions[questionNr]

                    onStart()

                    //update answers
                    val newAnswers = allJoined[0].answers[questionNr]
                    setAnswers(newAnswers)
            }
        }
    }

    private fun setAnswers(qanswers: ArrayList<String>) {
        val answers: ListView = findViewById(R.id.answers_container)
        for ((index, value) in qanswers.withIndex()) {
            answers.adapter = AnswerAdapter(this, qanswers)
        }
    }

    override fun onStart() {
        super.onStart()
        timer.start()
    }

    override fun onStop() {
        super.onStop()
        timer.cancel()
    }

}