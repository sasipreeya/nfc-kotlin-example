package com.jetruby.nfc.example

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sender = findViewById<Button>(R.id.sender)
        val receiver = findViewById<Button>(R.id.receiver)
        sender.setOnClickListener {
            intent = Intent(applicationContext, ReceiverActivity::class.java)
            startActivity(intent)
        }

        receiver.setOnClickListener {
            intent = Intent(applicationContext, SenderActivity::class.java)
            startActivity(intent)
        }
    }
}
