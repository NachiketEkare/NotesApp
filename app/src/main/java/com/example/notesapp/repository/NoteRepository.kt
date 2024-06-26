package com.example.notesapp.repository

import androidx.lifecycle.LiveData
import com.example.notesapp.database.Notesdao
import com.example.notesapp.model.Note

class NoteRepository(private val notesDao: Notesdao) {

    val allnotes: LiveData<List<Note>> = notesDao.getAllnotes()

    suspend fun insert (note: Note){
        notesDao.insert(note)
    }
    suspend fun delete (note: Note){
        notesDao.delete(note)
    }
    suspend fun update (note: Note){
        notesDao.update(note)
    }
    suspend fun deleteAllnotes(){
        notesDao.deleteAllNotes()
    }

    fun getNotesWithTitleLike(searchQuery: String): LiveData<List<Note>> {
        return notesDao.getNotesWithTitleLike(searchQuery)
    }

}