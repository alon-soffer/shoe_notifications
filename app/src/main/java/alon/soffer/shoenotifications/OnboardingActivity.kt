package alon.soffer.shoenotifications

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment

class OnboardingActivity : AppCompatActivity() {
    private lateinit var sharedViewModel: SharedViewModel
    private val totalOnboardingSteps = "5"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)

        val onboardingProgress = findViewById<TextView>(R.id.onboardingProgress)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        sharedViewModel = ViewModelProvider(this).get(SharedViewModel::class.java)
        // set observer for progress of onboarding
        val stepIdxObserver = Observer<String> { newIdx ->
            onboardingProgress.text =  "$newIdx/$totalOnboardingSteps"
        }
        sharedViewModel.onboardingStep.observe(this, stepIdxObserver)

        // set observer for onborading done
        val onboardingDoneObserver = Observer<Boolean> {newValue ->
            if (newValue)
            {
                // update sp so next time won't open onboarding and go to after activity
                val app = applicationContext as ShoeNotificationApp
                app.setOnboardedToSp()

                val inProgressIntent = Intent(this, AfterOnboardingActivity::class.java)
                startActivity(inProgressIntent)
                this.finish()
            }
        }
        sharedViewModel.onboardingDone.observe(this, onboardingDoneObserver)
    }
}