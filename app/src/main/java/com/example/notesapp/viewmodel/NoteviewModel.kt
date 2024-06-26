package com.example.notesapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.notesapp.repository.NoteRepository
import com.example.notesapp.database.Notedatabase
import com.example.notesapp.model.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteviewModel(application: Application) : AndroidViewModel(application) {

    val allnotes: LiveData<List<Note>>
    private val repository: NoteRepository

    init {
        val dao = Notedatabase.getDatabase(application).notedao()
        repository = NoteRepository(dao)
        allnotes = repository.allnotes

    }

    fun deleteNote(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(note)
    }

    fun updateNote(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(note)
    }

    fun addNote(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(note)
    }

    fun deleteallNotes() = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteAllnotes()
    }

    fun searchDatabase(searchQuery: String): LiveData<List<Note>> {
        return repository.getNotesWithTitleLike(searchQuery)
    }

    suspend fun addNoteIfNotDuplicate(note: Note): Boolean {
        return repository.addNoteIfNotDuplicate(note)
    }
}