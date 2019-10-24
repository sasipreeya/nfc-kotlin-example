package com.jetruby.nfc.example

import android.app.PendingIntent
import android.content.Intent
import android.content.IntentFilter
import android.nfc.NdefMessage
import android.nfc.NfcAdapter
import android.os.Bundle
import android.support.v4.content.LocalBroadcastManager
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.TextView

const val MIME_TEXT_PLAIN = "text/plain"

class ReceiverActivity : AppCompatActivity() {

    private var tvIncomingMessage: TextView? = null
    private var transferButton: Button? = null
    private var nfcAdapter: NfcAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wait_for_nfc)

        this.nfcAdapter = NfcAdapter.getDefaultAdapter(this)?.let { it }

//        initViews()
    }

//    private fun initViews() {
//        this.tvIncomingMessage = findViewById(R.id.tv_in_message)
//        this.transferButton = findViewById(R.id.transfer_btn)
//    }

    override fun onNewIntent(intent: Intent) {
        receiveMessageFromDevice(intent)
    }

    override fun onResume() {
        super.onResume()

        enableForegroundDispatch(this, this.nfcAdapter)
        receiveMessageFromDevice(intent)
    }

    override fun onPause() {
        super.onPause()
        disableForegroundDispatch(this, this.nfcAdapter)
    }

    private fun receiveMessageFromDevice(intent: Intent) {
        val action = intent.action
        if (NfcAdapter.ACTION_NDEF_DISCOVERED == action) {
            val parcelables = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES)
            with(parcelables) {
                val inNdefMessage = this[0] as NdefMessage
                val inNdefRecords = inNdefMessage.records
                val ndefRecord_0 = inNdefRecords[0]

                val inMessage = String(ndefRecord_0.payload)
                tvIncomingMessage?.text = inMessage
                sendBroadcastMessage(inMessage)
                changeActivity(inMessage)
            }
        }
    }

    private fun enableForegroundDispatch(activity: AppCompatActivity, adapter: NfcAdapter?) {

        val intent = Intent(activity.applicationContext, activity.javaClass)
        intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP

        val pendingIntent = PendingIntent.getActivity(activity.applicationContext, 0, intent, 0)

        val filters = arrayOfNulls<IntentFilter>(1)
        val techList = arrayOf<Array<String>>()

        filters[0] = IntentFilter()
        with(filters[0]) {
            this?.addAction(NfcAdapter.ACTION_NDEF_DISCOVERED)
            this?.addCategory(Intent.CATEGORY_DEFAULT)
            try {
                this?.addDataType(MIME_TEXT_PLAIN)
            } catch (ex: IntentFilter.MalformedMimeTypeException) {
                throw RuntimeException("Check your MIME type")
            }
        }

        adapter?.enableForegroundDispatch(activity, pendingIntent, filters, techList)
    }

    private fun disableForegroundDispatch(activity: AppCompatActivity, adapter: NfcAdapter?) {
        adapter?.disableForegroundDispatch(activity)
    }

    private fun sendBroadcastMessage(content: String) {
        Intent(RECEIVED_USERID).let {
            it.putExtra(USERID, content)
            LocalBroadcastManager.getInstance(applicationContext).sendBroadcast(it)
        }
    }

    private fun changeActivity(id : String) {
        val intent = Intent(this, TransferActivity::class.java)
        intent.putExtra(ID, id)
        startActivity(intent)
    }
}
