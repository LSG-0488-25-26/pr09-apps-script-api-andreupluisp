package com.example.api.util

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.Locale

private val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
private val outputFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.forLanguageTag("ca-ES"))

fun todayIsoDate(): String {
    return LocalDate.now().format(inputFormatter)
}

fun isValidIsoDate(value: String): Boolean {
    return try {
        LocalDate.parse(value, inputFormatter)
        true
    } catch (_: DateTimeParseException) {
        false
    }
}

fun formatReleaseDate(value: String): String {
    return try {
        LocalDate.parse(value, inputFormatter).format(outputFormatter)
    } catch (_: DateTimeParseException) {
        value.ifBlank { "-" }
    }
}
