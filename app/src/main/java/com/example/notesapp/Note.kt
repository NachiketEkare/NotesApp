package com.example.notesapp

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "Note_table")
class Note (
            @ColumnInfo(name = "Title") val noteTitle : String,
            @ColumnInfo(name = "description") val description : String,
            @ColumnInfo(name = "Timestamp") val Timestamp : String
){
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}