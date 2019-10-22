package com.jetruby.nfc.example

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.jetruby.nfc.example.nfc.WaitingNFCActivity

class Launcher : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launcher)

        val sender = findViewById<Button>(R.id.sender)
        val receiver = findViewById<Button>(R.id.receiver)
        sender.setOnClickListener {
            changeToWaitingNFC()
        }

        receiver.setOnClickListener {
            changeToWaitingNFC()
        }
    }

    fun changeToWaitingNFC() {
        intent = Intent(applicationContext, WaitingNFCActivity::class.java)
        startActivity(intent)
    }
}
