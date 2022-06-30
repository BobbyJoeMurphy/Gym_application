package com.example.gym_application

import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.LargeTest
import androidx.test.runner.AndroidJUnit4
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class MainTest {

    private lateinit var scenario: ActivityScenario<MainActivity>

    @Before
    fun Settup(){
        scenario = launchActivity()
        scenario.moveToState(Lifecycle.State.RESUMED)

    }
    @Test
    fun testCreateAUser(){
        onView(withId(R.id.Register_Button)).perform(click())

        val username = "testing1234@hotmail.com"
        val password = "fdnshjuf"
        onView(withId(R.id.Email)).perform(ViewActions.typeText(username.toString()))
        onView(withId(R.id.password2)).perform(ViewActions.typeText(password.toString()))
        Espresso.closeSoftKeyboard()
        onView(withId(R.id.button2))
    }


}