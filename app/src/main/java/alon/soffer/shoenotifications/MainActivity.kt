package alon.soffer.shoenotifications

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHost
import androidx.navigation.fragment.NavHostFragment

class MainActivity : AppCompatActivity() {
    private lateinit var sharedViewModel: SharedViewModel
    private val totalOnboardingSteps = "5"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val onboardingProgress = findViewById<TextView>(R.id.onboardingProgress)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        sharedViewModel = ViewModelProvider(this).get(SharedViewModel::class.java)
        val stepIdxObserver = Observer<String> { newIdx ->
            onboardingProgress.text =  "$newIdx/$totalOnboardingSteps"
        }
        sharedViewModel.onboardingStep.observe(this, stepIdxObserver)
    }
}