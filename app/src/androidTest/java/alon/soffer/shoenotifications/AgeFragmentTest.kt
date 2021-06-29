package alon.soffer.shoenotifications

import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.espresso.Espresso.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.clearText
import junit.framework.TestCase
import org.hamcrest.CoreMatchers.not
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AgeFragmentTest : TestCase(){

    private lateinit var ageScenario: FragmentScenario<AgeFragment>

    @Before
    fun setup(){
        ageScenario = launchFragmentInContainer(themeResId = R.style.Theme_ShoeNotifications)
        ageScenario.moveToState(Lifecycle.State.STARTED)
    }

    @Test
    fun when_legalAges_then_buttonIsEnabled(){

        // setup
        val ageText = onView(withId(R.id.ageEditText))
        val button = onView(withId(R.id.nextStepButton))

        // make sure button is disabled in the beginning
        button.check(matches(not(isEnabled())))

        val ages = listOf(18, 55, 100, 19)
        for (age in ages){
            // set age
            ageText.perform(typeText(age.toString()))
            closeSoftKeyboard()
            // verify button is enabled
            button.check(matches(isEnabled()))
            // clear input
            ageText.perform(clearText())
        }
    }

    @Test
    fun when_ilegalAges_then_buttonIsDisabled(){

        // setup
        val ageText = onView(withId(R.id.ageEditText))
        val button = onView(withId(R.id.nextStepButton))

        // make sure button is enabled in the beginning
        button.check(matches(not(isEnabled())))

        val ages = listOf(1, 16, 17)
        for (age in ages){
            // set age
            ageText.perform(typeText(age.toString()))
            closeSoftKeyboard()
            // verify button is disabled
            button.check(matches(not(isEnabled())))
            // clear input
            ageText.perform(clearText())
        }
    }

    @Test
    fun when_ilegalAges_then_errorMessageIsVisable(){

        // setup
        val ageText = onView(withId(R.id.ageEditText))
        val button = onView(withId(R.id.nextStepButton))
        val under18Text = onView(withId(R.id.under18Text))

        // make sure button is disabled in the beginning and text not showing
        button.check(matches(not(isEnabled())))
        under18Text.check(matches(not(isDisplayed())))

        val age = 15
        ageText.perform(typeText(age.toString()))
        closeSoftKeyboard()
        // verify button is disabled and text is showing
        button.check(matches(not(isEnabled())))
        under18Text.check(matches(isDisplayed()))
    }


}