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
import androidx.lifecycle.Observer
import androidx.navigation.findNavController

class NamesFragment : Fragment() {

    companion object {
        fun newInstance() = NamesFragment()
    }

    // ----- view models -----
    private lateinit var viewModel: NamesViewModel
    private lateinit var sharedViewModel: SharedViewModel

    // ----- UI elements -----
    private lateinit var nextButton: Button
    private lateinit var firstName : EditText
    private lateinit var lastName : EditText

    // ---- constants -----
    private val stepIdx = "5"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.names_fragment, container, false)
        // update step in headline
        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        sharedViewModel.onboardingStep.value = stepIdx

        // init button
        nextButton = rootView.findViewById(R.id.nextStepButton)
        nextButton.setOnClickListener { view ->
            view.findNavController().navigate(R.id.afterOnboardingFragment)
        }
        nextButton.isEnabled = false


        // set text change listeners
        firstName = rootView.findViewById(R.id.firstName)
        lastName = rootView.findViewById(R.id.lastName)

        firstName.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                viewModel.firstNameLD.value = s.toString()
            }
        })

        lastName.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                viewModel.lastNameLD.value = s.toString()
            }
        })


        // init viewModel
        viewModel = ViewModelProvider(this).get(NamesViewModel::class.java)
        viewModel.lastNameLD.value = ""
        viewModel.firstNameLD.value = ""

        // set observers for when names change - update validNames
        val firstNameObserver = Observer<String> { newName ->
            // check if first name or last name are empty: yes: validNames = false, else true
            viewModel.validNames.value = (newName != "" && viewModel.lastNameLD.value != "")
        }

        val lastNameObserver = Observer<String> { newName ->
            // check if last name or first name are empty: yes: validNames = false, else true
            viewModel.validNames.value = !(newName == "" || viewModel.firstNameLD.value == "")
        }
        viewModel.firstNameLD.observe(viewLifecycleOwner, firstNameObserver)
        viewModel.lastNameLD.observe(viewLifecycleOwner, lastNameObserver)


        // set observer for when validNames change - enable/disable button
        val validNamesObserver = Observer<Boolean> { newVal ->
            nextButton.isEnabled = newVal
        }
        viewModel.validNames.observe(viewLifecycleOwner, validNamesObserver)

        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        viewModel = ViewModelProvider(this).get(NamesViewModel::class.java)
//        sharedViewModel = ViewModelProvider(this).get(SharedViewModel::class.java)
        // TODO: Use the ViewModel
    }

}