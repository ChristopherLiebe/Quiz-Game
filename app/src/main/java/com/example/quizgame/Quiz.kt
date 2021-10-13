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
}