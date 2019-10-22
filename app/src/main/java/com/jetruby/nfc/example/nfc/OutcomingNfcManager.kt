package com.jetruby.nfc.example.nfc;

import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcEvent;
import com.jetruby.nfc.example.MIME_TEXT_PLAIN

class OutcomingNfcManager(
    private val nfcActivity: NfcActivity
) :
    NfcAdapter.CreateNdefMessageCallback,
    NfcAdapter.OnNdefPushCompleteCallback {

    override fun createNdefMessage(event: NfcEvent): NdefMessage {
        val outString = nfcActivity.getOutcomingMessage()

        with(outString) {
            val outBytes = this.toByteArray()
            val outRecord = NdefRecord.createMime(MIME_TEXT_PLAIN, outBytes)
            return NdefMessage(outRecord)
        }
    }

    override fun onNdefPushComplete(event: NfcEvent) {
        nfcActivity.signalResult()
    }

    interface NfcActivity {
        fun getOutcomingMessage(): String

        fun signalResult()
    }
}
