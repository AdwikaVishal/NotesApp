package com.example.adwikatest

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapp.R
import com.example.adwikatest.data.Note // Assuming your Note class is in this package

class RcAdapter(context: Context) : ListAdapter<Note, RcAdapter.ViewHolder>(DiffCallback()) {

    private val inflater = LayoutInflater.from(context)

    // ViewHolder class
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.textvie) // Assuming your TextView's id for title
        val contentTextView: TextView = itemView.findViewById(R.id.textviewContent) // Assuming your TextView's id for content
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(inflater.inflate(R.layout.text_view_terms, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val note = getItem(position) // Get the Note object at the given position
        holder.titleTextView.text = note.title // Set title
        holder.contentTextView.text = note.content // Set content
    }

    // DiffCallback to compare Notes
    class DiffCallback : DiffUtil.ItemCallback<Note>() {
        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem == newItem // Assuming that Notes have correct equals implementation
        }

        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem == newItem
        }
    }
}
