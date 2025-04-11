package com.example.calculator

import android.app.Activity
import android.widget.Toast
import com.notkamui.keval.Keval
import com.notkamui.keval.KevalZeroDivisionException

class ExpressionOperations {

    companion object {
        private var doesDotExistInLastNumber: Boolean = false
        private var isLastDot: Boolean = false
        private var isLastOperation: Boolean = false
        private var isLastZero: Boolean = true
        private var isLastNumber: Boolean = true
        private var canBeNumberNext : Boolean = true
        private var indexOfNumberStart: Int = 0
        private var isNumberNegative: Boolean = false
        private var expression: String = "0"
        private var openBrackets : Int = 0

        fun addToExpression(activity: Activity, element: String): String {
            if (element >= "0" && element <= "9") {

                if (isLastZero) {

                    if (element == "0") return expression

                    expression = expression.substring(0, expression.length - 1) + element

                    isLastOperation = false
                    isLastZero = false
                    isLastNumber = true
                    isLastDot = false
                    canBeNumberNext = true

                    return expression
                }
                if(!canBeNumberNext){
                    displayToast(activity, "Nie można wstawić liczby po ^2, % oraz )")

                    return expression
                }
                else expression += element
                isLastOperation = false
                isLastZero = false
                isLastNumber = true
                isLastDot = false
                canBeNumberNext = true
            }

            else if (element == "+" || element == "-" || element == "/" || element == "*") {
                if (isLastOperation) {
                    displayToast(activity, "Nie można wykonać kolejnej operacji bezpośrednio po ostatniej operacji!")

                    return expression
                }

                if (isLastDot) expression += "0"

                if (isNumberNegative) {
                    expression += ")"
                    openBrackets--
                    isNumberNegative = false
                }

                expression += " $element "

                isLastOperation = true
                isLastDot = false
                isLastZero = false
                isLastNumber = false
                doesDotExistInLastNumber = false
                canBeNumberNext = true
                indexOfNumberStart = expression.length
            }

            else if (element == "-1") {

                if (!isLastNumber && expression[expression.length - 1] != ')'){
                    displayToast(activity, "Możliość zmiany znaku wyłącznie po wprowadzeniu liczby!")

                    return expression
                }
                if(expression[expression.length - 1] == ')'){
                    isLastNumber = true
                    openBrackets++
                    expression = expression.dropLast(1)
                }
                expression = if (isNumberNegative) expression.substring(0, indexOfNumberStart) + expression.substring(indexOfNumberStart + 2, expression.length)
                else expression.substring(0, indexOfNumberStart) + "(-" + expression.substring(indexOfNumberStart, expression.length)
                canBeNumberNext = true
                isNumberNegative = !isNumberNegative
                if(isNumberNegative) openBrackets++
                else openBrackets--
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
                canBeNumberNext = true
                doesDotExistInLastNumber = true
                isLastDot = true
            }

            else if(element == "sin" || element == "cos" || element == "tan" || element == "ln" || element == "log2" || element == "sqrt"){
                if(isLastDot) expression += "0"

                if(isLastZero) expression = expression.substring(0, expression.length-1)

                expression += "$element("

                isLastDot = false
                isLastOperation = true
                isLastZero = false
                isLastNumber = false
                isNumberNegative = false
                canBeNumberNext = true
                indexOfNumberStart = expression.length
                openBrackets++
            }

            else if(element == "^"){
                if(isLastOperation){
                    displayToast(activity, "Potęgować można jedynie liczbe bądz wyrażenie!")

                    return expression
                }

                if(isLastDot) expression += "0"

                if(isNumberNegative) expression += ")"

                expression += "^"

                isLastOperation = true
                isLastDot = false
                isLastZero = false
                isNumberNegative = false
                isLastNumber = false
                canBeNumberNext = true
                indexOfNumberStart = expression.length
            }

            else if(element == "^2"){
                if(isLastOperation){
                    displayToast(activity, "Potęgować można jedynie liczbe bądz wyrażenie!")

                    return expression
                }

                if(isLastDot) expression += "0"

                if(isNumberNegative) expression += ")"

                expression += "^2"
                isLastOperation = false
                isLastDot = false
                isLastZero = false
                isNumberNegative = false
                canBeNumberNext = false
                isLastNumber = false
            }

            else if(element == "("){
                if(isLastZero){
                    expression = ""
                }
                isLastZero = false
                isLastNumber = false
                isLastDot = false
                isNumberNegative = false
                isLastOperation = true
                expression += "("
                canBeNumberNext = true
                indexOfNumberStart = expression.length
                openBrackets++
            }

            else if(element == ")"){
                if(openBrackets == 0){
                    displayToast(activity, "Nie ma żadnego otwartego nawiasu!")

                    return expression
                }

                isLastZero = false
                isLastNumber = false
                isLastDot = false
                expression += ")"
                canBeNumberNext = false
                openBrackets--
            }

            else if(element == "%"){
                if(isLastOperation){
                    displayToast(activity, "Nie można obliczyć procętów bezpośrednio po operacji!")

                    return expression
                }

                if(isLastDot) expression += "0"

                if(isNumberNegative) expression += ")"

                isLastZero = false
                isLastNumber = false
                isLastDot = false
                isLastOperation = false
                expression += "%"
                canBeNumberNext = false
                openBrackets--
            }

            return expression
        }

        fun displayToast(activity : Activity, message : String){
            Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
        }

        fun getExpression(): String { return expression }

        fun dropLastElement() : String{
            if(isLastOperation){
                if(expression.length > 3){
                    var lastFourSigns = expression.substring(expression.length-4, expression.length)
                    if(lastFourSigns == "sin(" || lastFourSigns == "cos(" || lastFourSigns == "tan(") expression = expression.dropLast(4)
                    else if(lastFourSigns == "qrt(" || lastFourSigns == "og2(") expression = expression.dropLast(5)
                    else if(expression[expression.length - 2] == '^') expression =expression.dropLast(1)
                    else if(expression[expression.length - 2] == ')') {
                        expression = expression.dropLast(1)
                        openBrackets++
                    }
                    else if(expression[expression.length - 2] == '(') {
                        expression = expression.dropLast(1)
                        openBrackets--
                    }
                    else expression = expression.dropLast(3)
                }
                else{
                    if(expression[expression.length - 2] == '^') expression =expression.dropLast(1)
                    if(expression[expression.length - 2] == ')') {
                        expression = expression.dropLast(1)
                        openBrackets++
                    }
                    else if(expression[expression.length - 2] == '(') {
                        expression = expression.dropLast(1)
                        openBrackets--
                    }
                    else expression = expression.dropLast(3)
                }
                indexOfNumberStart = 0
                for(i in expression.length-1 downTo 0){
                    if(expression[i] == '(' && !isNumberNegative){
                        indexOfNumberStart = i
                        break
                    }
                    if(expression[i] == '-') isNumberNegative = true
                    if(expression[i] == '.') doesDotExistInLastNumber = true
                    if(expression[i] == ' '){
                        indexOfNumberStart = i + 1
                        break
                    }
                }

                if(isNumberNegative && expression[expression.length-1] == ')') expression = expression.dropLast(1)

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
            canBeNumberNext = true
            openBrackets = 0

            expression = "0"

            return expression
        }

        fun calculateExpression(activity: Activity) : Boolean{
            replacePercent(0)
            if(isLastOperation){
                displayToast(activity, "W wyrażeniu znajduje się niedokończona operacja!")
                return false
            }
            var result: String = expression
            try{
                result = Keval.eval(expression).toString()
                if(result == "NaN"){
                    displayToast(activity, "W wyrażeniu znajduje się niedozwolony arguument funkcji logarytmicznych bądź trygonometrycznych!")
                    return false
                }
                expression += "\n= $result"
            }
            catch(_ : KevalZeroDivisionException){
                displayToast(activity, "W wyrażeniu znajduje się dzielenie przez Zero!")
                return false
            }

            return true
        }

        fun replacePercent(index : Int){
            var lastIndex : Int = index
            var percentFound = false
            for(i in lastIndex..expression.length-1){
                if(expression[i] == '%'){
                    expression = expression.substring(0,i) + "* 0.01" + expression.substring(i+1, expression.length)
                    lastIndex = i
                    percentFound = true
                    break
                }
            }
            if(percentFound) replacePercent(lastIndex)
        }

        fun addMissingBrackets(){
            repeat(openBrackets) {
                expression += ")"
            }
            openBrackets = 0
        }
    }
}
