package com.tugascloudcompt.myapplication

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class AddNoteActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)

        val etTitle: EditText = findViewById(R.id.etNoteTitle)
        val etContent: EditText = findViewById(R.id.etNoteContent)
        val btnSave: Button = findViewById(R.id.btnSaveNote)

        btnSave.setOnClickListener {
            val title = etTitle.text.toString()
            val content = etContent.text.toString()

            if (title.isNotEmpty() || content.isNotEmpty()) {
                val resultIntent = Intent()
                resultIntent.putExtra("EXTRA_TITLE", title)
                resultIntent.putExtra("EXTRA_CONTENT", content)

                setResult(Activity.RESULT_OK, resultIntent)
                finish()
            } else {
                setResult(Activity.RESULT_CANCELED)
                finish()
            }
        }
    }
}