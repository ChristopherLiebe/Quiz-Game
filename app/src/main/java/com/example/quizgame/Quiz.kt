package com.example.quizgame

data class Quiz (var questions: List<Question>) {
    var score = 0
    var questionNumber = 0

    fun left() {
        score += questions[questionNumber].LeftPoints
        questionNumber++
    }

    fun right() {
        score += questions[questionNumber].RightPoints
        questionNumber++
    }

    /*fun checkAnswer(answer: Boolean) {
        //  check if the answer in the param matches the answer of the current question
        //if it does
        score++
        // else
        score--
    }*/
}