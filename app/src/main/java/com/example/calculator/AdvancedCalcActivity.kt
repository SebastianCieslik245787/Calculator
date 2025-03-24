package com.example.calculator

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class AdvancedCalcActivity : AppCompatActivity() {
    private var isDotExistInNumber: Boolean = false
    private var isOperationLast: Boolean = false
    private lateinit var splitData: List<String>
    private lateinit var buttonClear: Button
    private lateinit var buttonDelete: Button
    private lateinit var buttonDivide: Button
    private lateinit var buttonMultiply: Button
    private lateinit var buttonAdd: Button
    private lateinit var buttonSubtract: Button
    private lateinit var buttonResult: Button
    private lateinit var buttonOne: Button
    private lateinit var buttonTwo: Button
    private lateinit var buttonThree: Button
    private lateinit var buttonFour: Button
    private lateinit var buttonFive: Button
    private lateinit var buttonSix: Button
    private lateinit var buttonSeven: Button
    private lateinit var buttonEight: Button
    private lateinit var buttonNine: Button
    private lateinit var buttonZero: Button
    private lateinit var buttonDot: Button
    private lateinit var buttonSign: Button
    private lateinit var buttonBrackets: Button
    private lateinit var buttonXY: Button
    private lateinit var buttonX2: Button
    private lateinit var buttonSin: Button
    private lateinit var buttonCos: Button
    private lateinit var buttonTan: Button
    private lateinit var buttonLn: Button
    private lateinit var buttonLog: Button
    private lateinit var buttonSqrt: Button
    private lateinit var buttonPercent: Button

    private lateinit var resultView: TextView

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.advanced_calculator)
        setLayoutObjects()

        resultView.text = "0"
        /*buttonZero.setOnClickListener { addSignToResultView("0") }
        buttonOne.setOnClickListener { addSignToResultView("1") }
        buttonTwo.setOnClickListener { addSignToResultView("2") }
        buttonThree.setOnClickListener { addSignToResultView("3") }
        buttonFour.setOnClickListener { addSignToResultView("4") }
        buttonFive.setOnClickListener { addSignToResultView("5") }
        buttonSix.setOnClickListener { addSignToResultView("6") }
        buttonSeven.setOnClickListener { addSignToResultView("7") }
        buttonEight.setOnClickListener { addSignToResultView("8") }
        buttonNine.setOnClickListener { addSignToResultView("9") }
        buttonAdd.setOnClickListener { addSignToResultView(" + ") }
        buttonSubtract.setOnClickListener { addSignToResultView(" - ") }
        buttonDivide.setOnClickListener { addSignToResultView(" / ") }
        buttonMultiply.setOnClickListener { addSignToResultView(" * ") }
        buttonResult.setOnClickListener {
            if (validateData()) resultView.text =
                resultView.text.toString() + "\n= " + countResult()
        }
        buttonDelete.setOnClickListener {
            if (resultView.text.length == 1) resultView.text = "0"
            else {
                if (!isLastSignNumber()) resultView.text =
                    resultView.text.substring(0, resultView.text.length - 3)
                else resultView.text = resultView.text.substring(0, resultView.text.length - 1)
            }
        }
        buttonClear.setOnClickListener { resultView.text = "0" }
        buttonDot.setOnClickListener {
            if (!isDotExistInNumber && isLastSignNumber()) {
                addSignToResultView(".")
            }
        }*/
    }

    private fun setLayoutObjects() {
        buttonZero = findViewById(R.id.buttonZeroAC)
        buttonOne = findViewById(R.id.buttonOneAC)
        buttonTwo = findViewById(R.id.buttonTwoAC)
        buttonThree = findViewById(R.id.buttonThreeAC)
        buttonFour = findViewById(R.id.buttonFourAC)
        buttonFive = findViewById(R.id.buttonFiveAC)
        buttonSix = findViewById(R.id.buttonSixAC)
        buttonSeven = findViewById(R.id.buttonSevenAC)
        buttonEight = findViewById(R.id.buttonEightAC)
        buttonNine = findViewById(R.id.buttonNineAC)
        buttonAdd = findViewById(R.id.buttonAddAC)
        buttonSubtract = findViewById(R.id.buttonSubtractAC)
        buttonMultiply = findViewById(R.id.buttonMultiplyAC)
        buttonDivide = findViewById(R.id.buttonDivideAC)
        buttonDelete = findViewById(R.id.buttonDeleteAC)
        buttonClear = findViewById(R.id.buttonClearAC)
        buttonResult = findViewById(R.id.buttonResultAC)
        buttonDot = findViewById(R.id.buttonDotAC)
        buttonSign = findViewById(R.id.buttonSignAC)
        resultView = findViewById(R.id.resultFieldAC)

        buttonBrackets = findViewById(R.id.buttonBrackets)
        buttonXY = findViewById(R.id.buttonXY)
        buttonX2 = findViewById(R.id.buttonX2)
        buttonSin = findViewById(R.id.buttonSin)
        buttonCos = findViewById(R.id.buttonCos)
        buttonTan = findViewById(R.id.buttonTan)
        buttonLn = findViewById(R.id.buttonLn)
        buttonLog = findViewById(R.id.buttonLog)
        buttonSqrt = findViewById(R.id.buttonSqrt)
        buttonPercent = findViewById(R.id.buttonPercent)
    }
}