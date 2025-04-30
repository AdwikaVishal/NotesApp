package com.example.adwikatest

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.adwikatest.dao.NoteDatabase
import com.example.adwikatest.repo.NoteRepository
import com.example.adwikatest.viewmodel.NoteViewModel
import com.example.adwikatest.viewmodel.NoteViewModelFactory
import com.example.notesapp.R
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class AdwikaFragment : Fragment(R.layout.fragment_adwika) {

    private lateinit var viewModel: NoteViewModel
    private lateinit var adapter: RcAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize the database and repository
        val db = NoteDatabase.getDatabase(requireContext())
        val repository = NoteRepository(db.noteDao())

        // Initialize the ViewModel using the ViewModelProvider
        viewModel = ViewModelProvider(this, NoteViewModelFactory(repository)).get(NoteViewModel::class.java)

        // Set up RecyclerView and Adapter
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerview)
        adapter = RcAdapter(context = requireContext()) // Provide proper context here
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        // Collect notes and update the adapter
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.notes.collectLatest { notes ->
                adapter.submitList(notes)
            }
        }

        // Set up Add Note button click listener
        view.findViewById<Button>(R.id.add_btn).setOnClickListener {
            viewModel.addNote("Sample Title", "Sample Content")
        }
    }
}
