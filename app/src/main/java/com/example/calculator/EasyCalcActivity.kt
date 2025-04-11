package com.example.calculator

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class EasyCalcActivity : AppCompatActivity() {
    private var isResultShown : Boolean = false
    private lateinit var gestureDetector: GestureDetector
    private val handler = Handler(Looper.getMainLooper())
    private var isDoubleClick = false

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
    private lateinit var resultView: TextView

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.easy_calculator)

        setLayoutObjects()

        gestureDetector = GestureDetector(this, object : GestureDetector.SimpleOnGestureListener() {
            override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
                isDoubleClick = false
                handler.postDelayed({
                    if (!isDoubleClick) {
                        resultView.text = ExpressionOperations.dropLastElement()
                    }
                }, 150)
                return true
            }

            override fun onDoubleTap(e: MotionEvent): Boolean {
                isDoubleClick = true
                resultView.text = ExpressionOperations.deleteExpression()
                return true
            }
        })

        resultView.text = ExpressionOperations.getExpression()

        buttonZero.setOnClickListener { buttonAction("0") }

        buttonOne.setOnClickListener { buttonAction("1") }

        buttonTwo.setOnClickListener { buttonAction("2") }

        buttonThree.setOnClickListener { buttonAction("3") }

        buttonFour.setOnClickListener { buttonAction("4") }

        buttonFive.setOnClickListener { buttonAction("5") }

        buttonSix.setOnClickListener { buttonAction("6") }

        buttonSeven.setOnClickListener { buttonAction("7") }

        buttonEight.setOnClickListener { buttonAction("8") }

        buttonNine.setOnClickListener { buttonAction("9") }

        buttonAdd.setOnClickListener { buttonAction("+") }

        buttonSubtract.setOnClickListener { buttonAction("-") }

        buttonDivide.setOnClickListener { buttonAction("/") }

        buttonMultiply.setOnClickListener { buttonAction("*") }

        buttonResult.setOnClickListener {
            ExpressionOperations.addMissingBrackets()
            if(ExpressionOperations.calculateExpression(this)){
                resultView.text = ExpressionOperations.getExpression()
                isResultShown = true
            }
        }

        buttonDelete.setOnTouchListener { _, event ->
            gestureDetector.onTouchEvent(event)
            true
        }

        buttonClear.setOnClickListener { resultView.text = ExpressionOperations.deleteExpression() }

        buttonDot.setOnClickListener { buttonAction(".") }

        buttonSign.setOnClickListener { buttonAction("-1") }
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
        buttonSign = findViewById(R.id.buttonSign)
        resultView = findViewById(R.id.resultField)

    }

    fun buttonAction(element : String){
        if(isResultShown) ExpressionOperations.deleteExpression()

        isResultShown = false

        resultView.text = ExpressionOperations.addToExpression(this, element)
    }
}