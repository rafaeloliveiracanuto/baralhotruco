package com.example.baralhotrucoutfpr.screens

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.ComponentActivity
import com.example.baralhotrucoutfpr.R
import com.example.baralhotrucoutfpr.logic.DEFAULT_NAME_1
import com.example.baralhotrucoutfpr.logic.DEFAULT_NAME_2

class NamesActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_names)

        val etName1 = findViewById<EditText>(R.id.et_name1)
        val etName2 = findViewById<EditText>(R.id.et_name2)

        val currentName1 = intent.getStringExtra("name1") ?: DEFAULT_NAME_1
        val currentName2 = intent.getStringExtra("name2") ?: DEFAULT_NAME_2

        etName1.setText(currentName1)
        etName2.setText(currentName2)

        findViewById<Button>(R.id.btn_confirm).setOnClickListener {
            setResult(RESULT_OK, Intent().apply {
                putExtra("name1", etName1.text.toString().ifBlank { currentName1 })
                putExtra("name2", etName2.text.toString().ifBlank { currentName2 })
            })
            finish()
        }
    }
}
