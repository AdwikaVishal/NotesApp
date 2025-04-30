package com.example.adwikatest.viewmodel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

import com.example.adwikatest.data.Note
import com.example.adwikatest.repo.NoteRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class NoteViewModelFactory(private val repository: NoteRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NoteViewModel(repository) as T
    }
}

class NoteViewModel(private val repository: NoteRepository) : ViewModel() {

    val notes = repository.allNotes

    fun addNote(title: String, content: String) {
        viewModelScope.launch {
            repository.insert(Note(title = title, content = content))
        }
    }

    fun updateNote(note: Note) {
        viewModelScope.launch {
            repository.update(note)
        }
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch {
            repository.delete(note)
        }
    }
}


val db = NoteDatabase.getDatabase(applicationContext)
val repository = NoteRepository(db.noteDao())
val viewModel: NoteViewModel = ViewModelProvider(this, NoteViewModelFactory(repository))[NoteViewModel::class.java]
