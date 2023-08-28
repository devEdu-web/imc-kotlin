package com.example.imc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_result.*
import java.math.BigDecimal
import java.math.MathContext
import java.math.RoundingMode

class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val bodyMassIndex = intent.getParcelableExtra<BodyMassIndex>("value")

        if(bodyMassIndex == null){
            finish()
        }

        bodyMassIndex!!.calculate()

        name.text = bodyMassIndex.name
        body_mass_index_type.text = bodyMassIndex.type.toString()

        weight.text = "${weight.text} ${bodyMassIndex.weight}"
        height.text = "${height.text} ${bodyMassIndex.height}"
        body_mass_index.text = "${body_mass_index.text} ${BigDecimal(bodyMassIndex.imc).setScale(2, RoundingMode.CEILING)}"

        button_back.setOnClickListener { onBackPressed(it) }
    }

    fun onBackPressed(view: android.view.View) {
        finish()
    }
}
