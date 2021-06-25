package alon.soffer.shoenotifications

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class FirstActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first)

        val app = applicationContext as ShoeNotificationApp

        if (app.isOnboarded){
            // finished onbaording already - go to after activity
            val inProgressIntent = Intent(this, AfterOnboardingActivity::class.java)
            startActivity(inProgressIntent)
            this.finish()
        }
        else{
            // not yet onboarded - go to onboarding
            val inProgressIntent = Intent(this, OnboardingActivity::class.java)
            startActivity(inProgressIntent)
            this.finish()
        }
    }
}