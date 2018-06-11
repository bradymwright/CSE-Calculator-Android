package com.cleansimpleeats.calculator


enum class Gender { MALE, FEMALE }

typealias BasalMetabolicRate = Double
typealias BMR = BasalMetabolicRate

fun calculateBmr(gender: Gender, weightLbs: Int, heightCm: Int, age: Int): BMR = when (gender) {
    Gender.MALE -> calculateBmrMale(weightLbs, heightCm, age)
    Gender.FEMALE -> calculateBmrFemale(weightLbs, heightCm, age)
}

private fun calculateBmrMale(weightLbs: Int, heightCm: Int, age: Int): BMR =
    66 + (6.23 * weightLbs) + (5 * heightCm) - (6.8 * age)

private fun calculateBmrFemale(weightLbs: Int, heightCm: Int, age: Int): BMR =
    655 + (4.35 * weightLbs) + (1.8 * heightCm) - (4.7 * age)

data class WeeklyActivity(val factor: Double, val text: String)

val weeklyActivities = listOf(
    WeeklyActivity(1.2, "0 Days"),
    WeeklyActivity(1.325, "1-2 days/week"),
    WeeklyActivity(1.45, "3-4 days/week"),
    WeeklyActivity(1.575, "5-6 days/week"),
    WeeklyActivity(1.7, "7 days/week")
)

typealias TotalDailyEnergyExpenditure = Double
typealias TDEE = TotalDailyEnergyExpenditure

fun calculateTdee(bmr: BMR, weeklyActivityFactor: Double): TDEE =
    bmr * weeklyActivityFactor

data class WeightGoal(val factor: Double, val text: String)

val weightGoals = listOf(
    WeightGoal(-0.30, "Weight Loss (30% below)"),
    WeightGoal(-0.25, "Weight Loss (25% below)"),
    WeightGoal(-0.20, "Weight Loss (20% below)"),
    WeightGoal(-0.15, "Weight Loss (15% below)"),
    WeightGoal(-0.1, "Weight Loss (10% below)"),
    WeightGoal(0.0, "Maintain Current Weight"),
    WeightGoal(0.1, "Weight Gain (10% above)"),
    WeightGoal(0.15, "Weight Gain (15%+ above)")
)

typealias DailyCalorieTarget = Int
typealias DCT = DailyCalorieTarget

fun calculateDct(tdee: TDEE, weightGoalFactor: Double): DCT =
    (tdee + (tdee * weightGoalFactor)).toInt()

fun getDisplayServingSuggestions(dct: DCT): Array<String> {
    require(dct >= 0)
    return when (dct) {
        in 0..1499 -> arrayOf("3 meals / 1 snack", "3 meals / 2 power balls")
        in 1500..1749 -> arrayOf("3 meals / 2 snacks", "3 meals / 1 snack / 2 power balls")
        in 1750..1999 -> arrayOf("3 meals / 3 snacks", "3 meals / 2 snacks / 2 power balls")
        in 2000..2249 -> arrayOf("3 meals / 4 snacks", "3 meals / 3 snacks / 2 power balls", "4 meals / 2 snack / 1 power ball")
        in 2250..2499 -> arrayOf("4 meals / 3 snacks / 1 power ball", "3 meals / 4 snacks / 2 power balls", "3 meals / 5 snacks")
        in 2500..2749 -> arrayOf("4 meals / 4 snacks / 1 power ball", "5 meals / 3 snacks", "5 meals / 2 snacks / 2 power balls")
        in 2750..2999 -> arrayOf("5 meals / 3 snacks / 1 power ball", "6 meals / 2 snacks / 2 power balls", "6 meals / 3 snacks")
        in 3000..Int.MAX_VALUE -> arrayOf("5 meals / 5 snacks / 1 dessert", "6 meals / 4 snacks", "6 meals / 3 snacks / 2 desserts")
        else -> throw IllegalArgumentException()
    }
}

const val CENTIMETERS_IN_INCH = 2.54
const val MIN_HEIGHT_FEET = 4
const val MAX_HEIGHT_FEET = 7
const val INCHES_IN_FOOT = 12
const val AVAILABLE_HEIGHTS = (MAX_HEIGHT_FEET - MIN_HEIGHT_FEET) * INCHES_IN_FOOT + 1

val heights = List(AVAILABLE_HEIGHTS) { index ->
    ImperialHeight.fromInches(MIN_HEIGHT_FEET * INCHES_IN_FOOT + index)
}

class ImperialHeight(val feet: Int, val inches: Int) {
    companion object {
        fun fromInches(inches: Int) = ImperialHeight(
            feet = inches / INCHES_IN_FOOT,
            inches = inches % INCHES_IN_FOOT
        )
    }

    fun toCentimeters(): Int = ((feet * INCHES_IN_FOOT + inches) * CENTIMETERS_IN_INCH).toInt()

    override fun toString() = """$feet' $inches""""
}