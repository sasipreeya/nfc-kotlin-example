package com.jetruby.nfc.example.view.custom

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.TextView
import com.jetruby.nfc.example.R
import com.jetruby.nfc.example.ReceiverActivity.Companion.USERID
import com.jetruby.nfc.example.SlipActivity

class TransferActivity : AppCompatActivity() {
    private var tvIncomingMessage: TextView? = null
    private var transferButton: Button? = null
    private lateinit var userid: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sender)
        userid = intent.getStringExtra(USERID)
        initViews()
    }

    private fun initViews() {
        this.tvIncomingMessage = findViewById(R.id.tv_in_message)
        this.transferButton = findViewById(R.id.transfer_btn)

        tvIncomingMessage?.text = userid
        transferButton?.setOnClickListener {
            val intent = Intent(applicationContext, SlipActivity::class.java)
            startActivity(intent)
        }
    }
}
