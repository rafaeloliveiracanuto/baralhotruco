package com.example.baralhotrucoutfpr.screens

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import com.example.baralhotrucoutfpr.R
import com.example.baralhotrucoutfpr.logic.DEFAULT_NAME_1
import com.example.baralhotrucoutfpr.logic.DEFAULT_NAME_2
import com.example.baralhotrucoutfpr.logic.addPoints
import com.example.baralhotrucoutfpr.logic.hasWon
import com.example.baralhotrucoutfpr.logic.resetMessage
import com.example.baralhotrucoutfpr.logic.winnerMessage

class MainActivity : ComponentActivity() {

    private lateinit var tvName1: TextView
    private lateinit var tvScore1: TextView
    private lateinit var tvName2: TextView
    private lateinit var tvScore2: TextView

    private var score1 = 0
    private var score2 = 0
    private var wins1 = 0
    private var wins2 = 0
    private var name1 = DEFAULT_NAME_1
    private var name2 = DEFAULT_NAME_2

    private val namesLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            name1 = result.data?.getStringExtra("name1") ?: name1
            name2 = result.data?.getStringExtra("name2") ?: name2
            updateNameViews()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        savedInstanceState?.let {
            score1 = it.getInt("score1")
            score2 = it.getInt("score2")
            wins1 = it.getInt("wins1")
            wins2 = it.getInt("wins2")
            name1 = it.getString("name1", DEFAULT_NAME_1)!!
            name2 = it.getString("name2", DEFAULT_NAME_2)!!
        }

        bindViews()
        setupClickListeners()
        updateNameViews()
        updateScoreViews()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("score1", score1)
        outState.putInt("score2", score2)
        outState.putInt("wins1", wins1)
        outState.putInt("wins2", wins2)
        outState.putString("name1", name1)
        outState.putString("name2", name2)
    }

    private fun bindViews() {
        tvName1 = findViewById(R.id.tv_name1)
        tvScore1 = findViewById(R.id.tv_score1)
        tvName2 = findViewById(R.id.tv_name2)
        tvScore2 = findViewById(R.id.tv_score2)
    }

    private fun setupClickListeners() {
        listOf(
            Triple(R.id.btn_p1_1, 0, 1),
            Triple(R.id.btn_p1_3, 0, 3),
            Triple(R.id.btn_p1_6, 0, 6),
            Triple(R.id.btn_p1_9, 0, 9),
            Triple(R.id.btn_p1_12, 0, 12),
            Triple(R.id.btn_p2_1, 1, 1),
            Triple(R.id.btn_p2_3, 1, 3),
            Triple(R.id.btn_p2_6, 1, 6),
            Triple(R.id.btn_p2_9, 1, 9),
            Triple(R.id.btn_p2_12, 1, 12)
        ).forEach { (id, playerIndex, points) ->
            findViewById<Button>(id).setOnClickListener {
                onPointsClicked(playerIndex, points)
            }
        }

        findViewById<Button>(R.id.btn_history).setOnClickListener { openHistory() }
        findViewById<Button>(R.id.btn_reset).setOnClickListener { resetHistory() }
        findViewById<Button>(R.id.btn_names).setOnClickListener { openNames() }
    }

    private fun onPointsClicked(playerIndex: Int, points: Int) {
        if (playerIndex == 0) {
            score1 = addPoints(score1, points)
            tvScore1.text = score1.toString()
            if (hasWon(score1)) showWinDialog(0)
        } else {
            score2 = addPoints(score2, points)
            tvScore2.text = score2.toString()
            if (hasWon(score2)) showWinDialog(1)
        }
    }

    private fun showWinDialog(playerIndex: Int) {
        val winnerName = if (playerIndex == 0) name1 else name2
        AlertDialog.Builder(this)
            .setTitle("Fim de Partida!")
            .setMessage(winnerMessage(winnerName))
            .setPositiveButton("OK") { _, _ ->
                if (playerIndex == 0) wins1++ else wins2++
                score1 = 0
                score2 = 0
                updateScoreViews()
            }
            .show()
    }

    private fun resetHistory() {
        score1 = 0; score2 = 0; wins1 = 0; wins2 = 0
        updateScoreViews()
        Toast.makeText(this, resetMessage(), Toast.LENGTH_SHORT).show()
    }

    private fun openHistory() {
        startActivity(Intent(this, HistoryActivity::class.java).apply {
            putExtra("name1", name1)
            putExtra("name2", name2)
            putExtra("wins1", wins1)
            putExtra("wins2", wins2)
        })
    }

    private fun openNames() {
        namesLauncher.launch(Intent(this, NamesActivity::class.java).apply {
            putExtra("name1", name1)
            putExtra("name2", name2)
        })
    }

    private fun updateNameViews() {
        tvName1.text = "$name1:"
        tvName2.text = "$name2:"
    }

    private fun updateScoreViews() {
        tvScore1.text = score1.toString()
        tvScore2.text = score2.toString()
    }
}
