package com.tugascloudcompt.myapplication

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    // Daftar untuk menyimpan catatan (sementara di memori)
    private val notesList = mutableListOf<Note>()
    private lateinit var noteAdapter: NoteAdapter
    private lateinit var rvNotes: RecyclerView

    // Penangan untuk menerima hasil dari AddNoteActivity
    private val addNoteLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            val data = result.data
            val title = data?.getStringExtra("EXTRA_TITLE") ?: "Tanpa Judul"
            val content = data?.getStringExtra("EXTRA_CONTENT") ?: ""

            // Buat ID unik sederhana
            val newNote = Note(
                id = System.currentTimeMillis(),
                title = title.ifEmpty { "Tanpa Judul" },
                content = content
            )

            addNoteToList(newNote)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Ini adalah baris yang crash sebelumnya jika tema salah
        setContentView(R.layout.activity_main)

        rvNotes = findViewById(R.id.rvNotesList)
        val fabAdd: FloatingActionButton = findViewById(R.id.fabAddNote)

        // Setup Adapter
        noteAdapter = NoteAdapter(notesList) { noteToDelete ->
            // Ini adalah aksi lambda 'onDeleteClick'
            deleteNoteFromList(noteToDelete)
        }

        // Setup RecyclerView
        rvNotes.adapter = noteAdapter
        rvNotes.layoutManager = LinearLayoutManager(this)

        // Setup Tombol Tambah (FAB)
        fabAdd.setOnClickListener {
            val intent = Intent(this, AddNoteActivity::class.java)
            addNoteLauncher.launch(intent)
        }
    }

    private fun addNoteToList(note: Note) {
        notesList.add(0, note) // Tambah di posisi paling atas
        noteAdapter.notifyItemInserted(0)
        rvNotes.scrollToPosition(0) // Scroll ke atas
    }

    private fun deleteNoteFromList(note: Note) {
        val position = notesList.indexOf(note)
        if (position != -1) {
            notesList.removeAt(position)
            noteAdapter.notifyItemRemoved(position)
        }
    }
}