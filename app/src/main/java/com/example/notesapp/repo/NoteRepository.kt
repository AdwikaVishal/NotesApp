package com.example.adwikatest.repo

import com.example.adwikatest.dao.NoteDao
import com.example.adwikatest.data.Note
import kotlinx.coroutines.flow.Flow

class NoteRepository(private val dao: NoteDao) {

    val allNotes: Flow<List<Note>> = dao.getAllNotes()

    suspend fun insert(note: Note) = dao.insertNote(note)
    suspend fun update(note: Note) = dao.updateNote(note)
    suspend fun delete(note: Note) = dao.deleteNote(note)
}
