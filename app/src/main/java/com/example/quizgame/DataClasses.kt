package com.example.quizgame.dataclasses

import com.example.quizgame.Questions

data class Quiz (var questions: List<Questions>) {
    var score = 0

    fun checkAnswer(answer: Boolean) {
        //  check if the answer in the param matches the answer of the current question
        //if it does
        score++
        // else
        score--
    }
}

//var question : String,
   // var buttonLeft : String,
   // var buttonRight : String,
   // var leftPoints : Int,
   // var rightPoints : Int
   // )