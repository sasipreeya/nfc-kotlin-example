package com.jetruby.nfc.example

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.nfc.NfcAdapter
import android.os.Bundle
import android.support.v4.content.LocalBroadcastManager
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

import com.jetruby.nfc.example.nfc.OutcomingNfcManager

class SenderActivity : AppCompatActivity(), OutcomingNfcManager.NfcActivity {

    private lateinit var tvOutcomingMessage: TextView
    private lateinit var etOutcomingMessage: EditText
    private lateinit var btnSetOutcomingMessage: Button

    private var nfcAdapter: NfcAdapter? = null

    private lateinit var outcomingNfcCallback: OutcomingNfcManager
    private lateinit var outMessage : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wait_for_nfc)

        this.nfcAdapter = NfcAdapter.getDefaultAdapter(this)?.let { it }

//        initViews()
        setOutGoingMessage()

        this.outcomingNfcCallback = OutcomingNfcManager(this)
        this.nfcAdapter?.setOnNdefPushCompleteCallback(outcomingNfcCallback, this)
        this.nfcAdapter?.setNdefPushMessageCallback(outcomingNfcCallback, this)
    }

    private fun initViews() {
        this.tvOutcomingMessage = findViewById(R.id.tv_out_message)
        this.etOutcomingMessage = findViewById(R.id.et_message)
        this.btnSetOutcomingMessage = findViewById(R.id.btn_set_out_message)
        this.btnSetOutcomingMessage.setOnClickListener { setOutGoingMessage() }
    }

    override fun onNewIntent(intent: Intent) {
        this.intent = intent
    }

    private fun setOutGoingMessage() {
        // val outMessage = this.etOutcomingMessage.text.toString()
        outMessage = "SCB Fast Easy Banking"
//        this.tvOutcomingMessage.text = outMessage

    }

    override fun getOutcomingMessage(): String =
        this.outMessage


    override fun signalResult() {
        runOnUiThread {
            Toast.makeText(this, R.string.message_beaming_complete, Toast.LENGTH_SHORT).show()
        }
        LocalBroadcastManager.getInstance(this).registerReceiver(
                object : BroadcastReceiver(){
                    override fun onReceive(context: Context, intent: Intent) {
                        setOutGoingMessage()
                        val intent = Intent(this@SenderActivity, MainActivity::class.java)
                        startActivity(intent)
                    }
                },
                IntentFilter(RECEIVED_USERID)
        )
    }
}
