package com.example.notesapp.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapp.R
import com.example.notesapp.model.Note

class NoteAdapter(
    private val noteClickInterface: NoteClickInterface,
    private val noteDeleteInterface: NoteDeleteInterface
):RecyclerView.Adapter<NoteAdapter.ViewHolder>() {


    private val allNotes = ArrayList<Note>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemview = LayoutInflater.from(parent.context)
            .inflate(R.layout.each_row,parent,false)
        return ViewHolder(itemview)
    }

    override fun getItemCount(): Int {
        return allNotes.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.note.text = allNotes[position].noteTitle
        holder.noteContent.text = allNotes[position].description
        holder.timestamp.text = "Last Updated"+ allNotes[position].timestamp

        holder.btnDelete.setOnClickListener {
            noteDeleteInterface.onDelete(allNotes[position])
        }
        holder.itemView.setOnClickListener {
            noteClickInterface.onNoteclick(allNotes[position])
        }
    }
    class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {

        val note: TextView = itemView.findViewById(R.id.notetitle)
        val noteContent: TextView = itemView.findViewById(R.id.description)
        val timestamp: TextView = itemView.findViewById(R.id.timestamp)
        val btnDelete: ImageView = itemView.findViewById(R.id.deletebtn)


    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(newList: List<Note>) {
        allNotes.clear()
        allNotes.addAll(newList)
        notifyDataSetChanged()
    }

    fun getNote(position: Int): Note {
        return allNotes[position]
    }

    fun addNoteIfNotDuplicate(note: Note): Boolean {
        if (allNotes.any { it.noteTitle == note.noteTitle }) {
            return false // Duplicate found, return false
        }
        allNotes.add(note)
        notifyItemInserted(allNotes.size - 1)
        return true

    }

}

interface NoteClickInterface{
    fun onNoteclick(note: Note)
}

interface NoteDeleteInterface{
    fun onDelete(note: Note)
}