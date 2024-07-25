package com.willaapps.auth.domain

interface PatternValidator {
    fun matches(value: String): Boolean
}