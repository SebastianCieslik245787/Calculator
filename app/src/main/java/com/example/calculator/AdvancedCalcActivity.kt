package com.example.calculator

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.SystemClock
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.notkamui.keval.Keval

class AdvancedCalcActivity : AppCompatActivity() {
    private var isDotExistInNumber: Boolean = false
    private var isResultPressed: Boolean = false
    private var isOperationLast: Boolean = false
    private var isNegativeLastNumber: Boolean = false
    private var startOfNumber: Int = 0
    private lateinit var splitData: List<String>
    private var openedBrackets = 0
    var lastClickTime: Long = 0

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
        buttonZero.setOnClickListener { addSignToResultView("0") }
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
            if (!isResultPressed && validateData()) resultView.text =
                resultView.text.toString() + "\n= " + countResult()
            isResultPressed = true
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
        }
        buttonSign.setOnClickListener {
            if (isNegativeLastNumber) {
                resultView.text =
                    resultView.text.substring(0, startOfNumber) + resultView.text.substring(
                        startOfNumber + 2,
                        resultView.text.length
                    )
                isNegativeLastNumber = false
            } else if (resultView.text.length - 1 != startOfNumber) {
                isNegativeLastNumber = true
                resultView.text =
                    StringBuilder(resultView.text).insert(startOfNumber, "(-").toString()
            }
        }

        setupDoubleClickListener(buttonBrackets)

        buttonXY.setOnClickListener { resultView.text = resultView.text.toString() + "^" }

        buttonX2.setOnClickListener { resultView.text = resultView.text.toString() + "^2" }
        buttonSin.setOnClickListener { resultView.text = resultView.text.toString() + "sin(" }
        buttonCos.setOnClickListener { resultView.text = resultView.text.toString() + "cos(" }
        buttonTan.setOnClickListener { resultView.text = resultView.text.toString() + "tan(" }
        buttonLn.setOnClickListener { resultView.text = resultView.text.toString() + "ln(" }
        buttonLog.setOnClickListener { resultView.text = resultView.text.toString() + "log2(" }
        buttonSqrt.setOnClickListener { resultView.text = resultView.text.toString() + "sqrt(" }
        //buttonPercent.setOnClickListener { resultView.text = resultView.text.toString() + "sin(" }
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

    @SuppressLint("SetTextI18n")
    private fun addSignToResultView(sign: String) {
        if (isResultPressed) {
            resultView.text = ""
            isResultPressed = false
        }
        if (resultView.text.length <= 1 && resultView.text == "0" && (sign >= "0" && sign <= "9")) {
            resultView.text = sign
            return
        }
        if (sign >= "0" && sign <= "9") {
            resultView.text = resultView.text.toString() + sign
            isOperationLast = false
        } else if (sign == "." && !isDotExistInNumber) {
            isDotExistInNumber = true
            resultView.text = resultView.text.toString() + sign
        } else {
            if (!isOperationLast) {
                if (isNegativeLastNumber) resultView.text = resultView.text.toString() + ")"
                isDotExistInNumber = false
                resultView.text = resultView.text.toString() + sign
                isOperationLast = true
                startOfNumber = resultView.text.length - 1
            }
        }
    }

    private fun isLastSignNumber(): Boolean {
        return ((resultView.text.last() in '0'..'9'))
    }

    private fun displayToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun validateData(): Boolean {
        if (!isLastSignNumber() || resultView.text.last() == '.') {
            displayToast("Obliczenia nie mogą kończyć się operacją bądz przecinkiem!!")
            return false
        }
        splitData = resultView.text.split(" ")
        for (i in splitData.indices) {
            if (splitData[i] == "/" && splitData[i + 1].toDouble() == 0.0) {
                displayToast("W obliczeniach znajduje się zabronione dzielenie przez 0!!")
                return false
            }
        }
        return true
    }

    private fun countResult(): String {
        return Keval.eval(resultView.text.toString()).toString()
    }

    @SuppressLint("SetTextI18n")
    fun setupDoubleClickListener(button: Button) {
        button.setOnClickListener {
            val currentTime = SystemClock.elapsedRealtime()
            var temp : String = resultView.text.toString()
            if (currentTime - lastClickTime < 400) {
                resultView.text = temp.substring(0, temp.length-1) + ")"
            }
            else{
                resultView.text = "$temp("
            }
            lastClickTime = currentTime
        }
    }
}