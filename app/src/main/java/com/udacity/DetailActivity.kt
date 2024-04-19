package com.udacity

import android.app.NotificationManager
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.udacity.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var button: FloatingActionButton
    private lateinit var fileTextView: TextView
    private lateinit var stateTextView: TextView

    private var file :String? = null
    private var statue :String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        button = findViewById(R.id.fab)

        button.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        file = intent.getStringExtra("file")

        fileTextView = findViewById(R.id.file_name)
        fileTextView.text = file

        statue = intent.getStringExtra("statue")

        stateTextView = findViewById(R.id.statue)
        stateTextView.text = statue

        val notificationManager =
            ContextCompat.getSystemService(
                applicationContext,
                NotificationManager::class.java
            ) as NotificationManager
        notificationManager.cancelAll()




    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        file = savedInstanceState.getString("file")
        statue = savedInstanceState.getString("statue")
    }

}