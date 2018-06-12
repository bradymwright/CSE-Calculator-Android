package com.cleansimpleeats.calculator

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import androidx.core.widget.toast
import kotlinx.android.synthetic.main.activity_main.*


interface MainView {
    fun selectGender(gender: Gender)
    fun showHeightItems(heightItems: List<ImperialHeight>)
    fun showWeeklyActivityItems(weeklyActivityItems: List<WeeklyActivity>)
    fun showWeightGoals(weightGoals: List<WeightGoal>)
    fun showSuggestionDialog(calories: Int, suggestions: Array<String>)
}

class MainActivity : AppCompatActivity(), MainView {

    private lateinit var presenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initPresenter()
        setupEditTexts()
        setupSubmitButton()
    }

    private fun initPresenter() {
        presenter = MainPresenter()
    }

    private fun setupEditTexts() {
        arrayOf(ageEditText, weightEditText).forEach {
            it.setHintTextColor(Color.GRAY)
            it.setOnFocusChangeListener { view, hasFocus ->
                if (!hasFocus) view.hideKeyboard()
            }
        }
    }

    private fun setupSubmitButton() {
        submitTextView.setOnClickListener {
            createSubmitParams()?.let(presenter::onSubmit) ?: run {
                toast(R.string.data_not_entered_message)
            }
        }
    }

    private val isInputValid: Boolean
        get() = genderRadioGroup.checkedRadioButtonId != -1
                && ageEditText.text.toString().isNotBlank()
                && weightEditText.text.toString().isNotBlank()
                && heightSpinner.selectedItemPosition != 0
                && weeklyActivitySpinner.selectedItemPosition != 0
                && weightGoalSpinner.selectedItemPosition != 0

    private fun createSubmitParams(): SubmitParams? =
        if (isInputValid) {
            SubmitParams(
                gender = when (genderRadioGroup.checkedRadioButtonId) {
                    R.id.maleRadioButton -> Gender.MALE
                    R.id.femaleRadioButton -> Gender.FEMALE
                    else -> throw IllegalStateException("Unsupported gender RadioButton")
                },
                age = ageEditText.text.toString().toInt(),
                weightLbs = weightEditText.text.toString().toInt(),
                heightItemPosition = heightSpinner.selectedItemPosition - 1,
                weeklyActivityItemPosition = weeklyActivitySpinner.selectedItemPosition - 1,
                weightGoalItemPosition = weightGoalSpinner.selectedItemPosition - 1
            )
        } else null

    override fun onStart() {
        super.onStart()
        presenter.onViewAttached(this)
    }

    override fun onStop() {
        presenter.onViewDetached()
        super.onStop()
    }

    override fun showSuggestionDialog(calories: Int, suggestions: Array<String>) {
        SuggestionDialogFragment.show(supportFragmentManager, calories, suggestions)
    }

    override fun selectGender(gender: Gender) {
        val radioButtonId = when (gender) {
            Gender.MALE -> R.id.maleRadioButton
            Gender.FEMALE -> R.id.femaleRadioButton
        }
        genderRadioGroup.check(radioButtonId)
    }

    override fun showHeightItems(heightItems: List<ImperialHeight>) {
        heightSpinner.setItemAdapter(heightItems, getString(R.string.height_spinner_hint))
    }

    override fun showWeeklyActivityItems(weeklyActivityItems: List<WeeklyActivity>) {
        weeklyActivitySpinner.setItemAdapter(weeklyActivityItems.map(WeeklyActivity::text), getString(R.string.weekly_exercise_spinner_hint))
    }

    override fun showWeightGoals(weightGoals: List<WeightGoal>) {
        weightGoalSpinner.setItemAdapter(weightGoals.map(WeightGoal::text), getString(R.string.weight_goal_spinner_hint))
    }
}

data class SubmitParams(
    val gender: Gender,
    val age: Int,
    val heightItemPosition: Int,
    val weightLbs: Int,
    val weeklyActivityItemPosition: Int,
    val weightGoalItemPosition: Int
)