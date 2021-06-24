package alon.soffer.shoenotifications

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class NamesViewModel : ViewModel() {
    val firstNameLD = MutableLiveData<String>()
    val lastNameLD = MutableLiveData<String>()
    var validNames = MutableLiveData<Boolean>()


}