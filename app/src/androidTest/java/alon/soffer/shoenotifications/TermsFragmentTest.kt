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
import androidx.test.espresso.action.ViewActions.click
import junit.framework.TestCase
import org.hamcrest.CoreMatchers.not
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TermsFragmentTest : TestCase(){

    private lateinit var termsScenario: FragmentScenario<TermsFragment>

    @Before
    fun setup(){
        termsScenario = launchFragmentInContainer(themeResId = R.style.Theme_ShoeNotifications)
        termsScenario.moveToState(Lifecycle.State.STARTED)
    }

    @Test
    fun when_checkBoxChecked_then_buttonIsEnabled(){

        // setup
        val checkBox = onView(withId(R.id.checkBox))
        val button = onView(withId(R.id.nextStepButton))

        // make sure button is enabled in the beginning
        button.check(matches(not(isEnabled())))

        // test
        checkBox.perform(click())

        // verify
        button.check(matches(isEnabled()))
    }

    @Test
    fun when_checkBoxCheckedAndThenUnchecked_then_buttonIsDisabled(){

        // setup
        val checkBox = onView(withId(R.id.checkBox))
        val button = onView(withId(R.id.nextStepButton))

        // make sure button is enabled in the beginning
        button.check(matches(not(isEnabled())))

        // test
        checkBox.perform(click())
        checkBox.perform(click())

        // verify
        button.check(matches(not(isEnabled())))
    }

}