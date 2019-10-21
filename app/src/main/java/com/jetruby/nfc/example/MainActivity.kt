package com.jetruby.nfc.example

import android.content.Intent
import android.nfc.NfcAdapter
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

import com.jetruby.nfc.example.nfc.OutcomingNfcManager

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sender = findViewById<Button>(R.id.btn_sender)
        val receiver = findViewById<Button>(R.id.btn_receiver)
        sender.setOnClickListener {
            val intent = Intent(this, SenderActivity::class.java)
            startActivity(intent)
        }
        receiver.setOnClickListener {
            val intent = Intent(this, ReceiverActivity::class.java)
            startActivity(intent)
        }
    }
}
