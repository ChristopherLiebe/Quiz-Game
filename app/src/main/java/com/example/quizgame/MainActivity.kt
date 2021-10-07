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

        // reading the json from the raw folder

        // step 1: open the raw resource as an InputStream
        // R.raw.questions --> R is the Resources class, raw is folder, questions is the file
        val inputStream = resources.openRawResource(R.raw.questions)
        // step 2: use a buffered reader on the inputstream tp read the text into a string
        val jsonText = inputStream.bufferedReader().use {
            // the last line of the use function is returned
            it.readText()
        }
        Log.d(TAG, "onCreate: $jsonText")

        val gson = Gson()
        val type = object : TypeToken<List<Question>>() { }.type
        val questions = gson.fromJson<List<Question>>(jsonText, type)
        Log.d(TAG, "onCreate: \n${questions.toString()}")

        val quiz = Quiz(questions)

        wireWidgets()

        fun presentScore() {
            questionNumber.text = "Score: ${quiz.score}"
            leftButton.visibility = GONE
            rightButton.visibility = GONE
            if (quiz.score < 61) {
                question.text =
                    "You are nowhere near being a gamer... Why are you taking this quiz?"
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
                questionNumber.text = "${quiz.questionNumber + 1}"
            }
        }

        updateScreen()

        // use gson to convert the jsonText into a List<Question>
        // https://medium.com/@hissain.khan/parsing-with-google-gson-library-in-android-kotlin-7920e26f5520
        // check the section called "Parsing between a Collection, List, or Array"
        //Log the list of questions and see if your Question objects all appear correct


        //wireWidgets()

        /*val questions = MutableList(1) { Question("Are you an introvert or an extrovert?", "Introvert", "Extrovert", 20, 10 ) }
        questions.add(Question("Do you like warm or cold colors?", "Warm", "Cold", 10, 20))
        questions.add(Question("Are you a night owl or a morning owl?", "Night Owl", "Morning Owl", 40, 5))
        questions.add(Question("Do you procrastinate your work?", "Yes", "No", 30, 5))
        questions.add(Question("Do you like Math/Sciences or English/History/Arts?", "Math/Sciences", "English/History/Arts", 5, 5))
        questions.add(Question("How long do you spend on the computer everyday?", "Less than an hour", "More than an hour", 5, 40))
        questions.add(Question("Do you like traveling?", "Yes", "No", 5, 25))
        questions.add(Question("Do you clean your room?", "Yes", "No", 10, 20))
        questions.add(Question("Do you like school?", "Yes", "No", 0, 30))
        questions.add(Question("Do you prefer the Summer/Spring or Winter/Fall", "Summer/Spring", "Winter/Fall", 5, 5))

        leftButton.text = questions[i].buttonLeft
        rightButton.text = questions[i].buttonRight
        question.text = questions[i].question
        questionNumber.text = "Question #1"

        leftButton.setOnClickListener {
            if (i < 9) {
                score += questions[i].leftPoints
                i++
                leftButton.text = questions[i].buttonLeft
                rightButton.text = questions[i].buttonRight
                question.text = questions[i].question
                questionNumber.text = "Question #${i + 1}"
            }
            else {
                score += questions[i].leftPoints
                presentScore()
            }
        }

        rightButton.setOnClickListener {
            if (i < 9) {
                score += questions[i].rightPoints
                i++
                leftButton.text = questions[i].buttonLeft
                rightButton.text = questions[i].buttonRight
                question.text = questions[i].question
                questionNumber.text = "Question #${i + 1}"
            }
            else {
                score += questions[i].rightPoints
                presentScore()
            }
        }



    }*/





        leftButton.setOnClickListener() {
            quiz.left()
            updateScreen()
        }

        rightButton.setOnClickListener() {
            quiz.right()
            updateScreen()
        }
        // answer1Button.setonCLicklistener {
        // any quiz - related actions -- scorekeeping, checking if answers are right or wrong, keeping track of which question we're on, if there are more questions remaining all duties of the Quiz class

        // MainActivity is in charge of the UI and passing information to and from the Quiz and class

        // Tell the quiz what was clicked on and let the quiz determine if the answer was right or wrong
        // update the score text view based on the current score
        //  ask the quiz if there are more questions, and if there are set the question text and button text to the new question and answer choices

        // if there aren't any more questions, then hide a bunch of UI and give the final score }

    }

    fun wireWidgets() {
        appTitle = findViewById(R.id.appTitle)
        questionNumber = findViewById(R.id.questionNumber)
        question = findViewById(R.id.question)
        leftButton = findViewById(R.id.buttonLeft)
        rightButton = findViewById(R.id.buttonRight)
    }
}