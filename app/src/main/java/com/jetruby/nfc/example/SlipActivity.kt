package com.jetruby.nfc.example

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.layout_card_info.*
import kotlinx.android.synthetic.main.layout_redeem_confirmation_top_part.*


class SlipActivity : AppCompatActivity() {

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_slip)

        val name:String = intent.getStringExtra("name")
        val amount:String = intent.getStringExtra("amount")
        txv_item_description.text = " Item code: 00903 / Qty: 1"
        txv_card_number.text = "xxx-xxxx-589"
        txv_card_type.text = name
        textView.text = "โอนเงินสำเร็จ"
        txv_product_name.text = "$amount บาท"
    }
}
