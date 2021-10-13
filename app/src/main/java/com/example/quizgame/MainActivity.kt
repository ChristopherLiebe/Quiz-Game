package com.example.quizgame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View.GONE
import android.widget.Button
import android.widget.TextView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MainActivity : AppCompatActivity() {

    val TAG = "MainActivity"

    lateinit var appTitle : TextView
    lateinit var questionNumber : TextView
    lateinit var question : TextView
    lateinit var leftButton : Button
    lateinit var rightButton : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        wireWidgets()

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

        fun presentScore() {
            leftButton.visibility = GONE
            rightButton.visibility = GONE
            if (quiz.score < 61) {
                question.text = "@string/main_score"
            } else if (quiz.score < 101) {
                question.text = "You are a normal human being..."
            } else if (quiz.score < 151) {
                question.text = "You game as a hobby..."
            } else if (quiz.score < 201) {
                question.text = "You are an average gamer!"
            } else if (quiz.score < 236) {
                question.text = "You game more than you sleep!"
            } else {
                question.text = "You are the gaming prodigy!"
            }
        }

        fun updateScreen() {
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
        questionNumber = findViewById(R.id.questionNumber)
        question = findViewById(R.id.question)
        leftButton = findViewById(R.id.buttonLeft)
        rightButton = findViewById(R.id.buttonRight)
    }
}
