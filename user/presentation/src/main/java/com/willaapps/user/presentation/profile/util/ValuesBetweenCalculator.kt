package com.willaapps.user.presentation.profile.util

fun calculateValuesBetween(maxValue: Int): List<Int> {

    val listOfValuesBetween = mutableListOf<Int>()
    val maxValueMultiplied = (maxValue * 3) / 2

    listOfValuesBetween.add(0)
    listOfValuesBetween.add(maxValueMultiplied)
    val averageInt = findAverageIntOrNull(0, maxValueMultiplied)

    listOfValuesBetween.add(averageInt ?: return listOfValuesBetween)
    listOfValuesBetween.add(
        findAverageIntOrNull(averageInt, maxValueMultiplied) ?: return listOfValuesBetween
    )
    listOfValuesBetween.add(
        findAverageIntOrNull(0, averageInt) ?: return listOfValuesBetween
    )

    return listOfValuesBetween
}

private fun findAverageIntOrNull(
    minValue: Int,
    maxValue: Int
): Int? {
    return when (val average = ((minValue + maxValue) / 2)) {
        minValue -> null
        maxValue -> null
        else -> average
    }
}