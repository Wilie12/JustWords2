package com.willaapps.user.domain

object EditProfileValidator {

    fun validateUsername(username: String): Boolean {
        return (username.isNotBlank() && username.isNotEmpty())
    }

    fun validateDailyGoal(dailyGoal: String): Boolean {
        val isDigit = dailyGoal.all { it.isDigit() }
        val isNotEmptyOrBlank = (dailyGoal.isNotBlank() && dailyGoal.isNotEmpty())
        return isDigit && isNotEmptyOrBlank
    }
}