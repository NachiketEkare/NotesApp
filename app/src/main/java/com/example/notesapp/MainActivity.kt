package com.example.notesapp

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity(), noteClickInterface, noteDeleteInterface,androidx.appcompat.widget.SearchView.OnQueryTextListener {

    lateinit var FAB : FloatingActionButton
    lateinit var eachitem : RecyclerView
    lateinit var viewModel: NoteviewModel
    private lateinit var searchView: androidx.appcompat.widget.SearchView
    private val noteAdapter:NoteAdapter by lazy { NoteAdapter(this,this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.title = "My Notes"
        eachitem = findViewById(R.id.recyclerview)
        FAB = findViewById(R.id.FAB)

        eachitem.layoutManager = LinearLayoutManager(this)

        searchView = findViewById(R.id.SearchView)

        eachitem.adapter = noteAdapter
        viewModel = ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory.getInstance(application))[NoteviewModel::class.java]
        viewModel.allnotes.observe(this) { list ->
            list?.let {
                noteAdapter.updateList(it)
            }
        }

        searchView.setOnQueryTextListener(this)

        FAB.setOnClickListener {
            try {
                val intent = Intent(this@MainActivity,addnoteactivity::class.java)
                startActivity(intent)
                this.finish()
            }
            catch (e:Exception){
                println(e.message)
            }

        }

        ItemTouchHelper(object:ItemTouchHelper.SimpleCallback(0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT,){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                TODO("Not yet implemented")
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                viewModel.deleteNote(noteAdapter.getNote(viewHolder.adapterPosition))
                Toast.makeText(this@MainActivity,"Note is deleted",Toast.LENGTH_LONG).show()
            }

        }).attachToRecyclerView(eachitem)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.deleteAllnotes -> ShowDialogMsg()
        }
        return true
    }

    fun ShowDialogMsg(){
        val dialogmsg = AlertDialog.Builder(this)
        dialogmsg.setTitle("Are you sure ?")
        dialogmsg.setMessage("Do you want to delete All Notes")
        dialogmsg.setPositiveButton("Yes") { dialogInterface, i ->
            viewModel.deleteallNotes()
        }
        dialogmsg.setNegativeButton("No",{dialogInterface,i->
        })
        dialogmsg.create().show()
    }

    override fun onNoteclick(note: Note) {
        val intent = Intent(this@MainActivity,addnoteactivity::class.java)
        intent.putExtra("noteType","Edit")
        intent.putExtra("noteTitle",note.noteTitle)
        intent.putExtra("notedisc",note.description)
        intent.putExtra("noteId",note.id)
        startActivity(intent)
        this.finish()
    }

    override fun onDelete(note: Note) {

        val deleteBuilder = AlertDialog.Builder(this)
        deleteBuilder.setTitle("Are you sure ?")
        deleteBuilder.setMessage("Do you want to delete Note")
        deleteBuilder.setPositiveButton("Yes") { dialogInterface, i ->
            viewModel.deleteNote(note)
            Toast.makeText(this, "${note. noteTitle} is deleted", Toast.LENGTH_LONG).show()
        }
        deleteBuilder.setNegativeButton("No",{dialogInterface,i ->

        })
        deleteBuilder.show()

    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if(query!=null){
            searchDatabase(query)
        }
        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {
        if(query!=null){
            searchDatabase(query)
        }
        return true
    }

    private fun searchDatabase(query:String){
        val searchQuery = "%$query%"

        viewModel.searchDatabase(searchQuery).observe(this) { List ->
            List.let {
                noteAdapter.updateList(it)
            }
        }
    }

}