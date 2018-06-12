package com.cleansimpleeats.calculator

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.FragmentManager
import android.support.v4.content.ContextCompat
import android.support.v7.widget.AppCompatImageView
import android.support.v7.widget.AppCompatTextView
import android.util.TypedValue
import android.view.*
import android.widget.LinearLayout
import androidx.core.os.bundleOf
import androidx.core.view.setMargins
import kotlinx.android.synthetic.main.dialog_suggestion.*
import java.text.NumberFormat
import java.util.*

private const val TAG = BuildConfig.APPLICATION_ID + ".SuggestionDialogFragment"
private const val ARG_CALORIES = "calories"
private const val ARG_SUGGESTIONS = "suggestions"

class SuggestionDialogFragment : DialogFragment() {

    companion object {
        fun show(fragmentManager: FragmentManager, calories: Float, suggestions: Array<String>) {
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
            val textView = createSuggestionTextView(suggestion)
            if (index != 0) {
                val separatorImageView = createSeparatorImageView()
                suggestionLinearLayout.addView(separatorImageView, suggestionLinearLayout.indexOfChild(changeTextView))
            }
            suggestionLinearLayout.addView(textView, suggestionLinearLayout.indexOfChild(changeTextView))
        }
    }

    private fun createSuggestionTextView(suggestion: String?) = AppCompatTextView(context).apply {
        id = View.generateViewId()
        text = suggestion
        setTextSize(TypedValue.COMPLEX_UNIT_SP, 14f)
        gravity = Gravity.CENTER_HORIZONTAL
        setTextColor(ContextCompat.getColor(context, R.color.cse_black))
    }

    private fun createSeparatorImageView() = AppCompatImageView(context).apply {
        id = View.generateViewId()
        setImageResource(R.drawable.ic_suggestion_separator)
        val size = context.dpToPixels(24)
        layoutParams = LinearLayout.LayoutParams(size, size).apply {
            gravity = Gravity.CENTER_HORIZONTAL
            setMargins(context.dpToPixels(8))
        }
    }

    private fun setupCaloriesText() {
        val calories = (arguments!!.getFloat(ARG_CALORIES)).toInt()
        val formattedCalories = NumberFormat.getNumberInstance(Locale.US).format(calories)
        caloriesTextView.text = formattedCalories
    }

    override fun onStart() {
        super.onStart()
        dialog.window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
    }
}