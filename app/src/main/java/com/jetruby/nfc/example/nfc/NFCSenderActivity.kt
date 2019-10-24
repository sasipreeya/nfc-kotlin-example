package com.jetruby.nfc.example.nfc

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.jetruby.nfc.example.Launcher.Companion.RECEIVER
import com.jetruby.nfc.example.Launcher.Companion.TYPE
import com.jetruby.nfc.example.R

class NFCSenderActivity : AppCompatActivity() {
    companion object {
        const val userID = "SCBONE EIEI"
    }
    private lateinit var nfcType: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wait_for_nfc)
        nfcType = intent.getStringExtra(TYPE)
    }



    fun waitingNFC() {
        if (nfcType == RECEIVER) {
            intent = Intent(applicationContext, NFCSenderActivity::class.java)
//            intent.putExtra(TYPE, nfcType)
//            startActivity(intent)
        }
        else {

        }
    }

//    fun changeToTarget() {
//        if (nfcType == SENDER) {
//            intent = Intent(applicationContext, NFCSenderActivity::class.java)
//            intent.putExtra(TYPE, nfcType)
//            startActivity(intent)
//        }
//        else {
//
//        }
//    }
}