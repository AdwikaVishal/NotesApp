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

class NoteFragment : Fragment(R.layout.fragment_adwika) {

    private lateinit var viewModel: NoteViewModel
    private lateinit var adapter: RcAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val db = NoteDatabase.getDatabase(requireContext())
        val repository = NoteRepository(db.noteDao())
        viewModel =
            ViewModelProvider(this, NoteViewModelFactory(repository)).get(NoteViewModel::class.java)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerview)
        adapter = RcAdapter(context = requireContext())
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        // Observe the list of notes
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.notes.collectLatest { notes ->
                adapter.submitList(notes)
            }
        }

        // Reference to EditTexts and Button
        val titleInput = view.findViewById<android.widget.EditText>(R.id.editTextTitle)
        val contentInput = view.findViewById<android.widget.EditText>(R.id.editTextContent)
        val addButton = view.findViewById<Button>(R.id.add_btn)

        // Save the note from user input
        addButton.setOnClickListener {
            val title = titleInput.text.toString().trim()
            val content = contentInput.text.toString().trim()

            if (title.isNotEmpty() && content.isNotEmpty()) {
                viewModel.addNote(title, content)

                // Clear fields after saving
                titleInput.text.clear()
                contentInput.text.clear()
            }
        }
    }
}
