package com.example.notesapp.view

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.notesapp.R
import com.example.notesapp.model.Note
import com.example.notesapp.viewmodel.NoteviewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class Addnoteactivity : AppCompatActivity() {

    private lateinit var noteTitleEdit: EditText
    private lateinit var notediscEdit: EditText
    private lateinit var addupdateBtn: Button
    private lateinit var backBtn: Button
    private lateinit var viewmodel: NoteviewModel
    private var noteID = -1

    @SuppressLint("MissingInflatedId", "SetTextI18n", "SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.addnoteactivity)

        supportActionBar?.title = "Add Note"
        supportActionBar!!.setDisplayHomeAsUpEnabled(false)
        noteTitleEdit = findViewById(R.id.editTitle)
        notediscEdit = findViewById(R.id.editDisc)
        addupdateBtn = findViewById(R.id.button)
        backBtn = findViewById(R.id.BackBtn)

        backBtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            this.finish()
        }

        viewmodel = ViewModelProvider(
            this, ViewModelProvider.AndroidViewModelFactory
                .getInstance(application)
        )[NoteviewModel::class.java]

        val notetype = intent.getStringExtra("noteType")
        if (notetype.equals("Edit")) {
            val notetitle = intent.getStringExtra("noteTitle")
            val notedisc = intent.getStringExtra("notedisc")
            noteID = intent.getIntExtra("noteId", -1)
            addupdateBtn.text = "Update"

            noteTitleEdit.setText(notetitle)
            notediscEdit.setText(notedisc)

        } else {
            addupdateBtn.text = "Save"
        }
        addupdateBtn.setOnClickListener {
            // on below line we are getting
            // title and desc from edit text.
            val noteTitle = noteTitleEdit.text.toString()
            val noteDescription = notediscEdit.text.toString()
            // on below line we are checking the type
            // and then saving or updating the data.
            if (notetype.equals("Edit")) {
                if (noteTitle.isNotEmpty() || noteDescription.isNotEmpty()) {
                    val sdf = SimpleDateFormat(" dd MMM, yyyy - HH:mm")
                    val currentDateAndTime: String = sdf.format(Date())
                    val updatedNote = Note(noteTitle, noteDescription, currentDateAndTime)
                    updatedNote.id = noteID
                    viewmodel.updateNote(updatedNote)
                    Toast.makeText(this, "Note Updated", Toast.LENGTH_SHORT).show()
                }
            } else {
                // if the string is not empty we are calling a
                // add note method to add data to our room database.
                if (noteTitle.isNotEmpty() || noteDescription.isNotEmpty()) {
                    val sdf = SimpleDateFormat(" dd MMM, yyyy - HH:mm")
                    val currentDateAndTime: String = sdf.format(Date())
                    // checks with duplicate title
                    lifecycleScope.launch {
                        if (viewmodel.addNoteIfNotDuplicate
                                (Note(noteTitle, noteDescription, currentDateAndTime))
                        ) {
                            viewmodel.addNote(Note(noteTitle, noteDescription, currentDateAndTime))
                            Toast.makeText(
                                this@Addnoteactivity, "$noteTitle Added",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            Toast.makeText(
                                this@Addnoteactivity,
                                "Note with same title already exists",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
            // opening the new activity on below line
            startActivity(Intent(applicationContext, MainActivity::class.java))
            this.finish()
        }


    }

}


