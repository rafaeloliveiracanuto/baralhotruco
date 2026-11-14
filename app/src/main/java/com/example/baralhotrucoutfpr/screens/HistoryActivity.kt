package com.example.baralhotrucoutfpr.screens

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity
import com.example.baralhotrucoutfpr.R
import com.example.baralhotrucoutfpr.logic.DEFAULT_NAME_1
import com.example.baralhotrucoutfpr.logic.DEFAULT_NAME_2

class HistoryActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        val name1 = intent.getStringExtra("name1") ?: DEFAULT_NAME_1
        val name2 = intent.getStringExtra("name2") ?: DEFAULT_NAME_2
        val wins1 = intent.getIntExtra("wins1", 0)
        val wins2 = intent.getIntExtra("wins2", 0)

        findViewById<TextView>(R.id.tv_history_player1).text = "$name1: $wins1 partidas"
        findViewById<TextView>(R.id.tv_history_player2).text = "$name2: $wins2 partidas"

        findViewById<Button>(R.id.btn_back).setOnClickListener { finish() }
    }
}
