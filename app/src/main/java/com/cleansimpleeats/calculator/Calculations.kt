package com.cleansimpleeats.calculator

import kotlin.math.roundToInt


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


val weeklyActivity = mapOf(
    "0 Days" to 1.2,
    "1-2 days/week" to 1.325,
    "3-4 days/week" to 1.45,
    "5-6 days/week" to 1.575,
    "7 days/week" to 1.7
)

typealias TotalDailyEnergyExpenditure = Double
typealias TDEE = TotalDailyEnergyExpenditure

fun calculateTdee(bmr: BMR, weeklyActivityFactor: Double): TDEE =
    bmr * weeklyActivityFactor

val weightGoals = mapOf(
    "Weight Loss (30% below)" to -0.30,
    "Weight Loss (25% below)" to -0.25,
    "Weight Loss (20% below)" to -0.20,
    "Weight Loss (15% below)" to -0.15,
    "Weight Loss (10% below)" to -0.1,
    "Maintain Current Weight" to 0,
    "Weight Gain (10% above)" to 0.1,
    "Weight Gain (15%+ above)" to 0.15
)

typealias DailyCalorieTarget = Int
typealias DCT = DailyCalorieTarget

fun calculateDct(tdee: TDEE, weightGoalFactor: Double): DCT =
    (tdee + (tdee * weightGoalFactor)).roundToInt()

fun getDisplayServingSuggestions(dct: DCT): String {
    require(dct >= 0)
    return when (dct) {
        in 0..1499 ->
            """3 meals / 1 snack
            3 meals / 2 power balls"""
        in 1500..1749 ->
            """3 meals / 2 snacks
            3 meals / 1 snack / 2 power balls"""
        in 1750..1999 ->
            """3 meals / 3 snacks
            3 meals / 2 snacks / 2 power balls"""
        in 2000..2249 ->
            """3 meals / 4 snacks
            3 meals / 3 snacks / 2 power balls
            4 meals / 2 snack / 1 power ball"""
        in 2250..2499 ->
            """4 meals / 3 snacks / 1 power ball
            3 meals / 4 snacks / 2 power balls
            3 meals / 5 snacks"""
        in 2500..2749 ->
            """4 meals / 4 snacks / 1 power ball
            5 meals / 3 snacks
            5 meals / 2 snacks / 2 power balls"""
        in 2750..2999 ->
            """5 meals / 3 snacks / 1 power ball
            6 meals / 2 snacks / 2 power balls
            6 meals / 3 snacks"""
        in 3000..Int.MAX_VALUE ->
            """5 meals / 5 snacks / 1 dessert
            6 meals / 4 snacks
            6 meals / 3 snacks / 2 desserts"""
        else -> throw IllegalArgumentException()
    }.trimMargin()
}