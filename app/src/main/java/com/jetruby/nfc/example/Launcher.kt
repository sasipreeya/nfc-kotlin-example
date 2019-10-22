package com.jetruby.nfc.example

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_launcher.*

class Launcher : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launcher)

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
