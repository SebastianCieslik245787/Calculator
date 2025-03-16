package com.example.calculator

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private var isDotExistInNumber: Boolean = false
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
    private lateinit var resultView: TextView

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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
        }
    }

    private fun setLayoutObjects() {
        buttonZero = findViewById(R.id.buttonZero)
        buttonOne = findViewById(R.id.buttonOne)
        buttonTwo = findViewById(R.id.buttonTwo)
        buttonThree = findViewById(R.id.buttonThree)
        buttonFour = findViewById(R.id.buttonFour)
        buttonFive = findViewById(R.id.buttonFive)
        buttonSix = findViewById(R.id.buttonSix)
        buttonSeven = findViewById(R.id.buttonSeven)
        buttonEight = findViewById(R.id.buttonEight)
        buttonNine = findViewById(R.id.buttonNine)
        buttonAdd = findViewById(R.id.buttonAdd)
        buttonSubtract = findViewById(R.id.buttonSubtract)
        buttonMultiply = findViewById(R.id.buttonMultiply)
        buttonDivide = findViewById(R.id.buttonDivide)
        buttonDelete = findViewById(R.id.buttonDelete)
        buttonClear = findViewById(R.id.buttonClear)
        buttonResult = findViewById(R.id.buttonResult)
        buttonDot = findViewById(R.id.buttonDot)
        resultView = findViewById(R.id.resultField)
    }

    @SuppressLint("SetTextI18n")
    private fun addSignToResultView(sign: String) {
        if (resultView.text.length == 1 && resultView.text == "0" && (sign >= "0" && sign <= "9")) {
            resultView.text = sign
            return
        }
        if (sign == ".") isDotExistInNumber = true
        else if(!(sign >= "0" && sign <= "9")) isDotExistInNumber = false
        resultView.text = resultView.text.toString() + sign

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
        while (true) {
            if (splitData.size == 1) break
            var isCounted: Boolean = false
            for (i in splitData.indices) {
                if (splitData[i] == "*" || splitData[i] == "/") {
                    var previousValue: Double = splitData[i - 1].toDouble()
                    var nextValue: Double = splitData[i + 1].toDouble()
                    var result: Double = if (splitData[i] == "*") previousValue * nextValue
                    else previousValue / nextValue
                    splitData = splitData.toMutableList().apply {
                        removeAt(i + 1)
                        removeAt(i)
                        set(i - 1, result.toString())
                    }
                    isCounted = true
                    break
                }
            }
            if (!isCounted) {
                for (i in splitData.indices) {
                    if (splitData[i] == "+" || splitData[i] == "-") {
                        var previousValue: Double = splitData[i - 1].toDouble()
                        var nextValue: Double = splitData[i + 1].toDouble()
                        var result: Double = if (splitData[i] == "+") previousValue + nextValue
                        else previousValue - nextValue
                        splitData = splitData.toMutableList().apply {
                            removeAt(i + 1)
                            removeAt(i)
                            set(i - 1, result.toString())
                        }
                        break
                    }
                }
            }
        }
        return splitData[0]
    }
}