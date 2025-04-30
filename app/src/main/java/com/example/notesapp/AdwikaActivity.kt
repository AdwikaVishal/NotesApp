package com.example.adwikatest
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.adwikatest.dao.NoteDatabase
import com.example.adwikatest.repo.NoteRepository
import com.example.adwikatest.viewmodel.NoteViewModel
import com.example.adwikatest.viewmodel.NoteViewModelFactory
import com.example.notesapp.R
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class AdwikaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adwika)

        // Initialize database, repository, and ViewModel
        val db = NoteDatabase.getDatabase(applicationContext)
        val repository = NoteRepository(db.noteDao())
        val viewModel: NoteViewModel = ViewModelProvider(this, NoteViewModelFactory(repository)).get(NoteViewModel::class.java)

        // Assuming addNote is a suspend function, so call it inside a coroutine
        lifecycleScope.launch {
            viewModel.addNote("Title new", "content new")
        }

        // Collecting notes from the ViewModel's Flow
        lifecycleScope.launch {
            viewModel.notes.collectLatest { notes ->
                Log.d("All notes", notes.toString())
            }
        }
    }
}
