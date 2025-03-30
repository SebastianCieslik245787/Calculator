package com.example.calculator

import android.app.Activity
import android.widget.Toast
import com.notkamui.keval.Keval

class ExpressionOperations {

    companion object {
        private var doesDotExistInLastNumber: Boolean = false
        private var isLastDot: Boolean = false
        private var isLastOperation: Boolean = false
        private var isLastZero: Boolean = true
        private var isLastNumber: Boolean = true
        private var indexOfNumberStart: Int = 0
        private var isNumberNegative: Boolean = false
        private var expression: String = "0"

        fun addToExpression(activity: Activity, element: String): String {
            if (element >= "0" && element <= "9") {

                if (isLastZero) {

                    if (element == "0") return expression

                    expression = expression.substring(0, expression.length - 1) + element
                }

                else expression += element

                isLastOperation = false
                isLastZero = false
                isLastNumber = true
                isLastDot = false
            }

            else if (element == "+" || element == "-" || element == "/" || element == "*") {
                if (isLastOperation) {
                    displayToast(activity, "Nie można wykonać kolejnej operacji bezpośrednio po ostatniej operacji!")

                    return expression
                }

                if (isLastDot) expression += "0"

                if (isNumberNegative) {
                    expression += ")"
                    isNumberNegative = false
                }

                expression += " $element "

                isLastOperation = true
                isLastDot = false
                isLastZero = false
                isLastNumber = false
                doesDotExistInLastNumber = false
                indexOfNumberStart = expression.length
            }

            else if (element == "-1") {

                if (!isLastNumber){
                    displayToast(activity, "Możliość zmiany znaku wyłącznie po wprowadzeniu liczby!")

                    return expression
                }

                expression = if (isNumberNegative) expression.substring(0, indexOfNumberStart) + expression.substring(indexOfNumberStart + 2, expression.length)
                else expression.substring(0, indexOfNumberStart) + "(-" + expression.substring(indexOfNumberStart, expression.length)

                isNumberNegative = !isNumberNegative
            }

            else if (element == ".") {

                if (doesDotExistInLastNumber){
                    displayToast(activity, "Ta liczba jest już zmiennoprzecinkowa!")

                    return expression
                }

                else if(isLastOperation){
                    expression += "0"
                    isLastOperation = false
                }

                expression += "."

                doesDotExistInLastNumber = true
                isLastDot = true
            }

            return expression
        }

        fun displayToast(activity : Activity, message : String){
            Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
        }

        fun getExpression(): String { return expression }

        fun dropLastElement() : String{
            if(isLastOperation){
                indexOfNumberStart = 0
                expression = expression.dropLast(3)
                for(i in expression.length-1 downTo 0){
                    if(expression[i] == '-') isNumberNegative = true
                    if(expression[i] == '.') doesDotExistInLastNumber = true
                    if(expression[i] == ' '){
                        indexOfNumberStart = i + 1
                        break
                    }
                }

                if(isNumberNegative) expression = expression.dropLast(1)
            }
            else expression = expression.dropLast(1)

            if(expression.isEmpty()){
                doesDotExistInLastNumber = false
                isLastDot = false
                isLastOperation = false
                isLastZero = true
                isLastNumber = true
                indexOfNumberStart = 0
                isNumberNegative = false
                expression = "0"
                return expression
            }

            when(expression[expression.length-1]){
                ' ' -> {
                    isLastOperation = true
                    isLastDot = false
                    isLastZero = false
                    isLastNumber = false
                    doesDotExistInLastNumber = false
                    indexOfNumberStart = expression.length
                }

                '.' -> {
                    isLastZero = expression[expression.length-2] == '0'
                    isLastOperation = false
                    isLastDot = true
                    isLastZero = false
                    isLastNumber = false
                    doesDotExistInLastNumber = true
                }

                '-' -> {
                    expression = expression.dropLast(2)
                    isNumberNegative = false
                    isLastOperation = true
                    doesDotExistInLastNumber = false
                    isLastNumber = false
                    isLastZero = false
                    indexOfNumberStart = expression.length
                }

                else -> {
                    isLastOperation = false
                    isLastNumber = true
                }
            }

            return expression
        }

        fun deleteExpression() : String{
            doesDotExistInLastNumber = false
            isLastDot = false
            isLastOperation = false
            isLastZero = true
            isLastNumber = true
            indexOfNumberStart = 0
            isNumberNegative = false

            expression = "0"

            return expression
        }

        fun validateData(activity: Activity) : Boolean{
            var listOfElements : List<String> = expression.split(" ")

            for(i in 0..listOfElements.size-1){
                if(listOfElements[i] == "/" && listOfElements[i + 1].toDouble() != 0.0){
                    displayToast(activity, "W wyrażeniu znajduje sie dzielenie przez 0!")
                    return false
                }
            }

            if(isLastOperation) {
                displayToast(activity, "Wyrażenie kończy się operacją!")
                return false
            }

            if(isLastDot) expression += "0"

            if(isNumberNegative && expression[expression.length-1] != ')') expression += ")"
            return true
        }

        fun calculateExpression() : String{
            var result = Keval.eval(expression).toString()
            expression += "\n= $result"
            return expression
        }
    }
}
