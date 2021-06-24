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

class MathFragment : Fragment() {

    companion object {
        fun newInstance() = MathFragment()
    }

    // ----- view models -----
    private lateinit var viewModel: MathViewModel
    private lateinit var sharedViewModel: SharedViewModel

    // ----- UI elements -----
    private lateinit var nextButton: Button
    private lateinit var problem: TextView
    private lateinit var answer: EditText

    // ----- math problem related -----
    private lateinit var currentProblem : String
    private var currentAnswer : Int = 0
    private val operations : List<String> = mutableListOf("+", "-", "*")
    private val mathProblems : HashMap<String, Int> = hashMapOf("15-4*4" to -1, "18+5-10" to 13)

    // ---- constants -----
    private val stepIdx = "4"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.math_fragment, container, false)
        // update step in headline
        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        sharedViewModel.onboardingStep.value = stepIdx

        // init button
        nextButton = rootView.findViewById(R.id.nextStepButton)
        nextButton.setOnClickListener { view ->
            view.findNavController().navigate(R.id.action_mathFragment_to_namesFragment)
        }
        nextButton.isEnabled = false


        // set math problem and wait for answer from user
        problem = rootView.findViewById(R.id.problem)
        answer = rootView.findViewById(R.id.answer)
        getRandomMathProblem()  //TODO!!!! if came back from next screen don't change problem
        problem.text = currentProblem

        answer.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                try {
                    val userAnswer = s.toString().toInt()
                    nextButton.isEnabled = (userAnswer == currentAnswer)
                }
                catch (e: java.lang.NumberFormatException) {
                    nextButton.isEnabled = false
                }
            }
        })

        return rootView
    }

    private fun getRandomMathProblem(){
        while (true)
        {
            val oper1 = operations[(operations.indices).random()]
            val oper2 = operations[(operations.indices).random()]
            val num1 = (0..9).random()
            val num2 = (0..10).random()
            val num3 = (0..9).random()

            currentProblem = getProblem(oper1, oper2, num1.toString(), num2.toString(), num3.toString())
            currentAnswer = solveProblem(oper1, oper2, num1, num2, num3)
            if (currentAnswer >= 0)
                break
        }
    }

    private fun getProblem(op1 : String, op2 : String, num1 : String, num2 : String, num3 : String)
    : String{
        return "(" + num1 + op1 + num2 + ")" + op2 + num3
    }

    private fun solveProblem(op1 : String, op2 : String, num1 : Int, num2 : Int, num3 : Int) : Int{
        var ans = 0
        when (op1){
            "+" -> {
                ans = num1 + num2
            }
            "-" -> {
                ans = num1 - num2
            }
            "*" -> {
                ans = num1 * num2
            }
        }
        when (op2){
            "+" -> {
                ans = ans + num3
            }
            "-" -> {
                ans = ans - num3
            }
            "*" -> {
                ans = ans * num3
            }
        }
        return ans
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

}