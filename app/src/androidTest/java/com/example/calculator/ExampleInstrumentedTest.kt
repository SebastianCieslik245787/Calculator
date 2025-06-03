package com.example.calculator

import android.view.View
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import android.widget.TextView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBackUnconditionally
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.Matcher
import org.hamcrest.Matchers.containsString
import org.junit.After

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule

@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun onStart(){
        Intents.init()
        ActivityScenario.launch(MainActivity::class.java)
    }

    @After
    fun onClose() {
        Intents.release()
        ExpressionOperations.deleteExpression()
        activityRule.scenario.close()
    }


    @Test
    fun addingNumberTestInEasyCalc() {
        onView(withId(R.id.buttonEasyCalc)).perform(click())

        Intents.intended(hasComponent(EasyCalcActivity::class.java.name))

        onView(withId(R.id.buttonFive)).perform(click())
        onView(withId(R.id.buttonAdd)).perform(click())
        onView(withId(R.id.buttonOne)).perform(click())
        onView(withId(R.id.buttonTwo)).perform(click())
        onView(withId(R.id.buttonDot)).perform(click())
        onView(withId(R.id.buttonNine)).perform(click())
        checkTextView("5 + 12.9")

        onView(withId(R.id.buttonResult)).perform(click())
        checkTextView("5 + 12.9\n= 17.9")
    }

    @Test
    fun swappingBetweenActivities() {
        onView(withId(R.id.buttonEasyCalc)).perform(click())

        Intents.intended(hasComponent(EasyCalcActivity::class.java.name))

        onView(withId(R.id.buttonOne)).perform(click())

        onView(withId(R.id.buttonAdd)).perform(click())
        checkTextView("1 + ")

        onView(withId(R.id.buttonDelete)).perform(doubleClick())
        checkTextView("0")

        pressBackUnconditionally()

        Intents.intended(hasComponent(MainActivity::class.java.name))

        onView(withId(R.id.buttonAdvanced)).perform(click())

        Intents.intended(hasComponent(AdvancedCalcActivity::class.java.name))

        onView(withId(R.id.buttonOneAC)).perform(click())

        onView(withId(R.id.buttonAddAC)).perform(click())
        checkTextView("1 + ", R.id.resultFieldAC)

        onView(withId(R.id.buttonDeleteAC)).perform(doubleClick())
        checkTextView("0", R.id.resultFieldAC)
    }

    fun checkTextView(expected : String, viewId: Int = R.id.resultField){
        onView(withId(viewId)).check { view, _ ->
            val resultText = (view as TextView).text.toString()

            assert(resultText == expected) {
                "Tekst nie zawiera wyrażenia: $expected"
            }
        }
    }
}