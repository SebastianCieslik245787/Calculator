package com.example.calculator

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {

    private lateinit var buttonEasyCalc : Button
    private lateinit var buttonAdvancedCalc : Button
    private lateinit var buttonAboutMe : Button
    private lateinit var buttonExit : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setObjects()

        buttonEasyCalc.setOnClickListener {
            val intent = Intent(this, EasyCalcActivity::class.java)
            startActivity(intent)
        }

        buttonAdvancedCalc.setOnClickListener {
            val intent = Intent(this, AdvancedCalcActivity::class.java)
            startActivity(intent)
        }
        buttonAboutMe.setOnClickListener {  }
        buttonExit.setOnClickListener {
            finishAffinity()
            exitProcess(0)
        }
    }

    fun setObjects(){
        buttonEasyCalc = findViewById(R.id.buttonEasyCalc)
        buttonAdvancedCalc = findViewById(R.id.buttonAdvanced)
        buttonAboutMe = findViewById(R.id.buttonAboutMe)
        buttonExit = findViewById(R.id.buttonExit)
    }
}