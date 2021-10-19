package com.example.quizgame

import android.os.Bundle
import android.util.Log
import android.view.View.GONE
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MainActivity : AppCompatActivity() {

    val TAG = "MainActivity"

    lateinit var appTitle : TextView
    lateinit var question : TextView
    lateinit var leftButton : Button
    lateinit var rightButton : Button
    lateinit var progressBar : ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        wireWidgets()
        appTitle.text = getString(R.string.app_name)

        val inputStream = resources.openRawResource(R.raw.questions)
        val jsonText = inputStream.bufferedReader().use {
            it.readText()
        }

        Log.d(TAG, "onCreate: $jsonText")
        val gson = Gson()
        val type = object : TypeToken<List<Question>>() { }.type
        val questions = gson.fromJson<List<Question>>(jsonText, type)
        Log.d(TAG, "onCreate: \n${questions.toString()}")


        val quiz = Quiz(questions)
        progressBar.max = quiz.questions.size

        fun presentScore() {
            leftButton.visibility = GONE
            rightButton.visibility = GONE
            progressBar.visibility = GONE
            if (quiz.score < 61) {
                question.text = getString(R.string.main_personality_one)
            } else if (quiz.score < 101) {
                question.text = getString(R.string.main_personality_two)
            } else if (quiz.score < 151) {
                question.text = getString(R.string.main_personality_three)
            } else if (quiz.score < 201) {
                question.text = getString(R.string.main_personality_four)
            } else if (quiz.score < 236) {
                question.text = getString(R.string.main_personality_five)
            } else {
                question.text = getString(R.string.main_personality_six)
            }
        }

        fun updateScreen() {
            progressBar.progress = quiz.questionNumber
            if(quiz.questionNumber >= quiz.questions.size) {
                presentScore()
            }
            else {
                leftButton.text = quiz.questions[quiz.questionNumber].LeftAnswer
                rightButton.text = quiz.questions[quiz.questionNumber].RightAnswer
                question.text = quiz.questions[quiz.questionNumber].Question
            }
        }

        updateScreen()

        leftButton.setOnClickListener() {
            quiz.left()
            updateScreen()
        }

        rightButton.setOnClickListener() {
            quiz.right()
            updateScreen()
        }
    }

    fun wireWidgets() {
        appTitle = findViewById(R.id.appTitle)
        question = findViewById(R.id.question)
        leftButton = findViewById(R.id.buttonLeft)
        rightButton = findViewById(R.id.buttonRight)
        progressBar = findViewById(R.id.main_progressBar)
    }
}
