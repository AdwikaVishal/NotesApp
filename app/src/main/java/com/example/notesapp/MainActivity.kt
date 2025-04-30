package com.example.notesapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.adwikatest.NoteFragment

class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val openFragmentButton:Button = findViewById(R.id.add_btn)
        openFragmentButton.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container_view, NoteFragment())
                .commit()
        }



    }
}
