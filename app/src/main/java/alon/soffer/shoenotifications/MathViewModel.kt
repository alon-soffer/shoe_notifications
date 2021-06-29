package alon.soffer.shoenotifications

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MathViewModel : ViewModel() {
    val realAnswerLD = MutableLiveData<Int>()
    val userAnswerLD = MutableLiveData<String>()
    val problemLD = MutableLiveData<String>()
}