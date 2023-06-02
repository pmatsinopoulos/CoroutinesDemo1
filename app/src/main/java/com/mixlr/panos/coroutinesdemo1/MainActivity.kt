package com.mixlr.panos.coroutinesdemo1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    private var count = 0
    private lateinit var messageTextView: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var textView = findViewById<TextView>(R.id.tvCount)
        var countButton = findViewById<Button>(R.id.btnCount)
        var downloadButton = findViewById<Button>(R.id.btnDownload)
        messageTextView = findViewById<TextView>(R.id.tvMessage)

        countButton.setOnClickListener {
            textView.text = count++.toString()
        }

        downloadButton.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                downloadUserData()
            }
        }
    }

    private suspend fun downloadUserData() { // more like "async" function in JavaScript?
        for (i in 1..200_000) {
            Log.i("MyTag", "Downloading user $i in ${Thread.currentThread().name}")
            withContext(Dispatchers.Main) {
                messageTextView.text = "Download user $i" // it will not be possible to try to write from the non-main thread to the main thread UI.
            }
            delay(100) // milliseconds something like "await" in JavaScript?
        }
    }
}
