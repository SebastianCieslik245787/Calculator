package com.example.calculator

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class AdvancedCalcActivity : AppCompatActivity() {
    private var isResultShown : Boolean = false
    private lateinit var gestureDetectorDelete: GestureDetector
    private val handlerDelete = Handler(Looper.getMainLooper())
    private lateinit var gestureDetectorBrackets: GestureDetector
    private val handlerBrackets = Handler(Looper.getMainLooper())
    private var isDoubleClickDelete = false
    private var isDoubleClickBrackets = false

    private var activity : Activity = this

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

    @SuppressLint("SetTextI18n", "ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.advanced_calculator)
        setLayoutObjects()

        gestureDetectorDelete = GestureDetector(this, object : GestureDetector.SimpleOnGestureListener() {
            override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
                isDoubleClickDelete = false
                handlerDelete.postDelayed({
                    if (!isDoubleClickDelete) {
                        resultView.text = ExpressionOperations.dropLastElement()
                    }
                }, 150)
                return true
            }

            override fun onDoubleTap(e: MotionEvent): Boolean {
                isDoubleClickDelete = true
                resultView.text = ExpressionOperations.deleteExpression()
                return true
            }
        })

        gestureDetectorBrackets = GestureDetector(this, object : GestureDetector.SimpleOnGestureListener() {
            override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
                isDoubleClickBrackets = false
                handlerBrackets.postDelayed({
                    if (!isDoubleClickBrackets) {
                        resultView.text = ExpressionOperations.addToExpression(activity, "(")
                    }
                }, 150)
                return true
            }

            override fun onDoubleTap(e: MotionEvent): Boolean {
                isDoubleClickBrackets = true
                resultView.text = ExpressionOperations.addToExpression(activity, ")")
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
            if(ExpressionOperations.calculateExpression(activity)){
                resultView.text = ExpressionOperations.getExpression()
                isResultShown = true
            }
        }

        buttonDelete.setOnTouchListener { _, event ->
            gestureDetectorDelete.onTouchEvent(event)
            true
        }

        buttonClear.setOnClickListener { resultView.text = ExpressionOperations.deleteExpression() }
        buttonDot.setOnClickListener { buttonAction(".") }
        buttonSign.setOnClickListener {buttonAction("-1") }

        buttonXY.setOnClickListener { buttonAction("^") }

        buttonX2.setOnClickListener { buttonAction("^2") }
        buttonSin.setOnClickListener { buttonAction("sin") }
        buttonCos.setOnClickListener { buttonAction("cos") }
        buttonTan.setOnClickListener { buttonAction("tan") }
        buttonLn.setOnClickListener { buttonAction("ln") }
        buttonLog.setOnClickListener { buttonAction("log2") }
        buttonSqrt.setOnClickListener { buttonAction("sqrt") }
        buttonPercent.setOnClickListener { buttonAction("%") }

        buttonBrackets.setOnTouchListener { _, event ->
            gestureDetectorBrackets.onTouchEvent(event)
            true
        }
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

    fun buttonAction(element : String){
        if(isResultShown) ExpressionOperations.deleteExpression()

        isResultShown = false

        resultView.text = ExpressionOperations.addToExpression(activity, element)
    }
}