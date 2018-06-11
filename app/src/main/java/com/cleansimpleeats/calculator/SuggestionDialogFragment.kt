package com.cleansimpleeats.calculator

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.FragmentManager
import android.support.v4.content.ContextCompat
import android.util.TypedValue
import android.view.*
import android.widget.TextView
import androidx.core.os.bundleOf
import kotlinx.android.synthetic.main.dialog_suggestion.*

private const val TAG = BuildConfig.APPLICATION_ID + ".SuggestionDialogFragment"
private const val ARG_CALORIES = "calories"
private const val ARG_SUGGESTIONS = "suggestions"

class SuggestionDialogFragment : DialogFragment() {

    companion object {
        fun show(fragmentManager: FragmentManager, calories: Int, suggestions: Array<String>) {
            SuggestionDialogFragment().run {
                arguments = bundleOf(
                    ARG_CALORIES to calories,
                    ARG_SUGGESTIONS to suggestions
                )
                show(fragmentManager, TAG)
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.dialog_suggestion, container)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupCaloriesText()
        setupSuggestions()
        setupChangeButton()
    }

    private fun setupChangeButton() {
        changeTextView.setOnClickListener { dismiss() }
    }

    private fun setupSuggestions() {
        val suggestions = arguments!!.getStringArray(ARG_SUGGESTIONS)
        suggestions.forEachIndexed { index, suggestion ->
            val textView = TextView(context).apply {
                text = suggestion
                setTextSize(TypedValue.COMPLEX_UNIT_SP, 14f)
                gravity = Gravity.CENTER_HORIZONTAL
                setTextColor(ContextCompat.getColor(context, R.color.cse_black))
                if (index != 0) {
                    setCompoundDrawablesWithIntrinsicBounds(null, context.getDrawable(R.drawable.ic_suggestion_separator), null, null)
                }
            }
            suggestionLinearLayout.addView(textView, suggestionLinearLayout.indexOfChild(changeTextView))
        }
    }

    private fun setupCaloriesText() {
        val calories = arguments!!.getInt(ARG_CALORIES)
        caloriesTextView.text = calories.toString()
    }

    override fun onStart() {
        super.onStart()
        dialog.window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
    }
}