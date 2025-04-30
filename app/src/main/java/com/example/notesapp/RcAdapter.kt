package com.example.adwikatest

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.adwikatest.data.Note
import com.example.notesapp.R

class RcAdapter(context: Context) : ListAdapter<Note, RcAdapter.ViewHolder>(DiffCallback()) {

    private val inflater = LayoutInflater.from(context)

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.textviewTitle)
        val contentTextView: TextView = itemView.findViewById(R.id.textviewContent)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(inflater.inflate(R.layout.text_view_terms, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val note = getItem(position)
        holder.titleTextView.text = note.title
        holder.contentTextView.text = note.content
    }

    class DiffCallback : DiffUtil.ItemCallback<Note>() {
        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean = oldItem == newItem
    }
}
