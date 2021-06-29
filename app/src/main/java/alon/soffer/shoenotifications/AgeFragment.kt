package alon.soffer.shoenotifications

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.navigation.findNavController

class AgeFragment : Fragment() {

    companion object {
        fun newInstance() = AgeFragment()
    }

    // ----- view models -----
    private lateinit var viewModel: AgeViewModel
    private lateinit var sharedViewModel: SharedViewModel
    // ----- UI elements -----
    private lateinit var nextButton: Button
    private var ageText: EditText? = null
    private lateinit var under18Text: TextView

    // ---- constants -----
    private val stepIdx = "2"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.age_fragment, container, false)
        // update step in headline
        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        sharedViewModel.onboardingStep.value = stepIdx

        // init button
        nextButton = rootView.findViewById(R.id.nextStepButton)
        nextButton.setOnClickListener { view ->
            view.findNavController().navigate(R.id.action_ageFragment_to_termsFragment)
        }
        nextButton.isEnabled = false

        under18Text = rootView.findViewById(R.id.under18Text)
        under18Text.visibility = View.INVISIBLE


        ageText = rootView.findViewById(R.id.ageEditText)
        if (savedInstanceState != null)
            ageText!!.setText(savedInstanceState.getString("age",))
        ageText!!.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                val ageString = s.toString()
                try {
                    if (ageString.toInt() >= 18) {
                        nextButton.isEnabled = true
                        under18Text.visibility = View.INVISIBLE
                    }
                    else{
                        nextButton.isEnabled = false
                        under18Text.visibility = View.VISIBLE
                    }
                }
                catch (e: java.lang.NumberFormatException){
                    under18Text.visibility = View.INVISIBLE
                    nextButton.isEnabled = false
                }
            }
        })
        return rootView
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (ageText != null)
            outState.putString("age", ageText!!.text.toString())
    }

//    override fun onViewStateRestored(savedInstanceState: Bundle?) {
//        super.onViewStateRestored(savedInstanceState)
//        if (savedInstanceState != null)
//            ageText!!.setText(savedInstanceState.getString("age"))
//    }

}