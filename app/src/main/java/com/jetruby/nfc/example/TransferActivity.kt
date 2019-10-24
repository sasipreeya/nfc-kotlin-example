package com.jetruby.nfc.example

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.support.v4.content.LocalBroadcastManager
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class TransferActivity : AppCompatActivity() {
    private var tvIncomingMessage: TextView? = null
    private var transferButton: Button? = null
    private var etMessage: EditText? = null
    private var userid: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receiver)
        userid = intent.getStringExtra(ID)
        initViews()
    }

    private fun initViews() {
        this.tvIncomingMessage = findViewById(R.id.tv_in_message)
        this.transferButton = findViewById(R.id.transfer_btn)
        this.etMessage = findViewById(R.id.et_message)

        tvIncomingMessage?.text = userid
        transferButton?.setOnClickListener {
            val amount = etMessage?.text.toString()
            if (amount.isEmpty() || amount == "."){
                Toast.makeText(this, "กรุณาระบุจำนวนเงินให้ถูกต้อง", Toast.LENGTH_SHORT).show()
            }else{
                val intent = Intent(this, SlipActivity::class.java)
                intent.putExtra(ID,userid)
                intent.putExtra(AMOUNT,amount)
                startActivity(intent)
            }
        }
    }
}
