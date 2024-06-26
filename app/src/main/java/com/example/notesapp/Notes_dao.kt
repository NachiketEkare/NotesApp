package com.example.notesapp

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface Notes_dao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(note: Note):Long

    @Update
    suspend fun update(note: Note)

    @Delete
    suspend fun delete(note: Note)

    @Query("DELETE FROM Note_table")
    suspend fun deleteAllNotes()

    @Query("SELECT * FROM Note_table ORDER BY id ASC")
    fun getAllnotes(): LiveData<List<Note>>

    @Query("SELECT * FROM Note_table WHERE Title LIKE :searchQuery")
    fun getNotesWithTitleLike(searchQuery: String): LiveData<List<Note>>


}