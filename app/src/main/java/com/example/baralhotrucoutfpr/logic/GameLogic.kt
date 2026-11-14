package com.example.baralhotrucoutfpr.logic

const val WIN_SCORE = 12
const val DEFAULT_NAME_1 = "Jogador 1"
const val DEFAULT_NAME_2 = "Jogador 2"

fun addPoints(currentScore: Int, points: Int): Int = currentScore + points

fun hasWon(score: Int): Boolean = score >= WIN_SCORE

fun winnerMessage(name: String): String = "$name ganhou a partida!"

fun resetMessage(): String = "Histórico zerado!"
