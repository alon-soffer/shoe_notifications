package alon.soffer.shoenotifications

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.RadioGroup
import androidx.navigation.findNavController

class TermsFragment : Fragment() {

    companion object {
        fun newInstance() = TermsFragment()
    }
    // ----- view models -----
    private lateinit var viewModel: TermsViewModel
    private lateinit var sharedViewModel: SharedViewModel
    // ----- UI elements -----
    private lateinit var nextButton: Button
    private var checkBox: CheckBox? = null
    // ---- constants -----
    private val stepIdx = "3"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.terms_fragment, container, false)

        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        sharedViewModel.onboardingStep.value = stepIdx
        nextButton = rootView.findViewById(R.id.nextStepButton)
        nextButton.setOnClickListener { view ->
            view.findNavController().navigate(R.id.action_termsFragment_to_mathFragment)
        }
        nextButton.isEnabled = false

        checkBox = rootView.findViewById(R.id.checkBox)
        checkBox!!.setOnCheckedChangeListener { _, _ -> nextButton.isEnabled = checkBox!!.isChecked }

        if (savedInstanceState != null){
            checkBox!!.isChecked = savedInstanceState.getBoolean("checked")
        }

        return rootView
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (checkBox != null)
            outState.putBoolean("checked", checkBox!!.isChecked)
    }

//    override fun onViewStateRestored(savedInstanceState: Bundle?) {
//        super.onViewStateRestored(savedInstanceState)
//        if (savedInstanceState != null){
//            checkBox.isChecked = savedInstanceState.getBoolean("checked")
//        }
//    }

}