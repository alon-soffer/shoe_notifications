package alon.soffer.shoenotifications

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {

    val onboardingStep = MutableLiveData<String>()
    var returnToMath = false
    var onboardingDone = MutableLiveData<Boolean>(false)

}