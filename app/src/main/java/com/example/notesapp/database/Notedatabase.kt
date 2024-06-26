package com.example.notesapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.notesapp.model.Note

@Database(entities = [Note::class], version = 1, exportSchema = false)
abstract class Notedatabase:RoomDatabase() {

    abstract fun notedao(): Notesdao

    companion object{
        @Volatile
        private var INSTANCE: Notedatabase? = null

        fun getDatabase(context: Context): Notedatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    Notedatabase::class.java,
                    "word_database"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
    }

