package com.tugascloudcompt.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class NoteAdapter(
    private val notes: MutableList<Note>,
    private val onDeleteClick: (Note) -> Unit
) : RecyclerView.Adapter<NoteAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvTitle: TextView = view.findViewById(R.id.tvNoteTitle)
        val tvContent: TextView = view.findViewById(R.id.tvNoteContent)
        val btnDelete: ImageButton = view.findViewById(R.id.btnDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_note, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val note = notes[position]

        holder.tvTitle.text = note.title
        holder.tvContent.text = note.content

        holder.btnDelete.setOnClickListener {
            onDeleteClick(note)
        }
    }
}