package com.jetruby.nfc.example

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class SlipActivity : AppCompatActivity() {

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_slip)
        val description = findViewById<TextView>(R.id.txv_item_description)
        val cardNumber = findViewById<TextView>(R.id.txv_card_number)
        val cardType = findViewById<TextView>(R.id.txv_card_type)
        val textView = findViewById<TextView>(R.id.textView)
        val productName = findViewById<TextView>(R.id.txv_product_name)

        val amount:String = intent.getStringExtra("amount")
        description.text = " Item code: 00903 / Qty: 1"
        cardNumber.text = "xxx-xxxx-589"
        cardType.text = "นายหนึ่ง สองสามสี่"
        textView.text = "โอนเงินสำเร็จ"
        productName.text = "$amount บาท"
    }
}
