package com.cleansimpleeats.calculator

class MainPresenter {

    private var view: MainView? = null

    fun onViewAttached(view: MainView) {
        this.view = view
        showInitialViewState()
    }

    private fun showInitialViewState() {
        view?.run {
            showHeightItems(heights)
            showWeeklyActivityItems(weeklyActivities)
            showWeightGoals(weightGoals)
        }
    }

    fun onViewDetached() {
        view = null
    }

    fun onSubmit(submitParams: SubmitParams) {
        val bmr = calculateBmr(
            gender = submitParams.gender,
            weightLbs = submitParams.weightLbs.toFloat(),
            heightCm = heights[submitParams.heightItemPosition].toCentimeters(),
            age = submitParams.age
        )
        val tdee = calculateTdee(
            bmr = bmr,
            weeklyActivityFactor = weeklyActivities[submitParams.weeklyActivityItemPosition].factor
        )
        val dct = calculateDct(
            tdee = tdee,
            weightGoalFactor = weightGoals[submitParams.weightGoalItemPosition].factor
        )
        view?.showSuggestionDialog(
            calories = dct,
            suggestions = getDisplayServingSuggestions(dct)
        )
    }
}