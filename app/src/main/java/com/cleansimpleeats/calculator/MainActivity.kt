package com.cleansimpleeats.calculator

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
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presenter = MainPresenter()
        setupSubmitButton()
    }

    private fun setupSubmitButton() {
        submitTextView.setOnClickListener {
            createSubmitParams()?.let(presenter::onSubmit) ?: run {
                toast("Please enter the data first")
            }
        }
    }

    private fun createSubmitParams(): SubmitParams? {
        val ageString = ageEditText.text.toString()
        val weightLbsString = weightEditText.text.toString()
        if (ageString.isEmpty() || weightLbsString.isEmpty()) return null

        return SubmitParams(
            gender = when (genderRadioGroup.checkedRadioButtonId) {
                R.id.maleRadioButton -> Gender.MALE
                R.id.femaleRadioButton -> Gender.FEMALE
                else -> throw IllegalStateException("Unsupported gender RadioButton")
            },
            age = ageString.toInt(),
            weightLbs = weightLbsString.toInt(),
            heightItemPosition = heightSpinner.selectedItemPosition,
            weeklyActivityItemPosition = weeklyActivitySpinner.selectedItemPosition,
            weightGoalItemPosition = weightGoalSpinner.selectedItemPosition
        )
    }

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
        heightSpinner.setItemAdapter(heightItems)
    }

    override fun showWeeklyActivityItems(weeklyActivityItems: List<WeeklyActivity>) {
        weeklyActivitySpinner.setItemAdapter(weeklyActivityItems.map(WeeklyActivity::text))
    }

    override fun showWeightGoals(weightGoals: List<WeightGoal>) {
        weightGoalSpinner.setItemAdapter(weightGoals.map(WeightGoal::text))
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