package com.example.adwikatest

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.adwikatest.dao.NoteDatabase
import com.example.adwikatest.repo.NoteRepository
import com.example.adwikatest.viewmodel.NoteViewModel
import com.example.adwikatest.viewmodel.NoteViewModelFactory
import com.example.notesapp.R
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class AdwikaActivity : AppCompatActivity() {

    private lateinit var viewModel: NoteViewModel
    private lateinit var adapter: RcAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adwika)

        // Initialize UI elements
        val titleInput = findViewById<EditText>(R.id.titleInput)
        val contentInput = findViewById<EditText>(R.id.contentInput)
        val addNoteButton = findViewById<Button>(R.id.addNoteButton)
        val recyclerView = findViewById<RecyclerView>(R.id.notesRecyclerView)

        // Setup RecyclerView
        adapter = RcAdapter(this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        // Setup ViewModel
        val db = NoteDatabase.getDatabase(applicationContext)
        val repository = NoteRepository(db.noteDao())
        viewModel = ViewModelProvider(this, NoteViewModelFactory(repository)).get(NoteViewModel::class.java)

        // Add note on button click
        addNoteButton.setOnClickListener {
            val title = titleInput.text.toString().trim()
            val content = contentInput.text.toString().trim()
            if (title.isNotEmpty() || content.isNotEmpty()) {
                viewModel.addNote(title, content)
                titleInput.text.clear()
                contentInput.text.clear()
            }
        }

        // Observe notes and update UI
        lifecycleScope.launch {
            viewModel.notes.collectLatest { notes ->
                adapter.submitList(notes)
            }
        }
    }
}
