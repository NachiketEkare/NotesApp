package com.example.notesapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class NoteAdapter(
    val noteClickInterface: noteClickInterface,
    val noteDeleteInterface: noteDeleteInterface
):RecyclerView.Adapter<NoteAdapter.viewHolder>() {


    private val allNotes = ArrayList<Note>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val itemview = LayoutInflater.from(parent.context).inflate(R.layout.each_row,parent,false)
        return viewHolder(itemview)
    }

    override fun getItemCount(): Int {
        return allNotes.size
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        holder.Note.setText(allNotes.get(position).noteTitle)
        holder.Timestamp.setText("Last Updated"+allNotes.get(position).Timestamp)

        holder.Btndelete.setOnClickListener {
            noteDeleteInterface.onDelete(allNotes.get(position))
        }
        holder.itemView.setOnClickListener {
            noteClickInterface.onNoteclick(allNotes.get(position))
        }
    }
    class viewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {

        val Note = itemView.findViewById<TextView>(R.id.notetitle)
        val Timestamp = itemView.findViewById<TextView>(R.id.timestamp)
        val Btndelete = itemView.findViewById<ImageView>(R.id.deletebtn)

    }

    fun updateList(newList: List<Note>) {
        allNotes.clear()
        allNotes.addAll(newList)
        notifyDataSetChanged()
    }

    fun getNote(position: Int):Note{
        return allNotes[position]
    }

}

interface noteClickInterface{
    fun onNoteclick(note: Note)
}

interface noteDeleteInterface{
    fun onDelete(note: Note)
}