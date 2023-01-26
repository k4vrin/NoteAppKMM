package dev.kavrin.note_app_kmm.domain.time

import kotlinx.datetime.*

/**
 * DateTimeUtil
 *
 * Some common time related functions
 */
object DateTimeUtil {
    /**
     * now
     *
     * @return [LocalDateTime] with system default time zone
     */
    fun now(): LocalDateTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())

    /**
     *
     */
    fun LocalDateTime.toEpochMillis(): Long = this.toInstant(TimeZone.currentSystemDefault()).toEpochMilliseconds()

    fun formatNoteDate(dateTime: LocalDateTime): String {
        val month = dateTime.month.name.lowercase().take(3).replaceFirstChar { it.uppercase() }
        val day = if (dateTime.dayOfMonth < 10) "0${dateTime.dayOfMonth}" else dateTime.dayOfMonth.toString()
        val year = dateTime.year
        val hour = if (dateTime.hour < 10) "0${dateTime.hour}" else dateTime.hour.toString()
        val minute = if (dateTime.minute < 10) "0${dateTime.minute}" else dateTime.minute.toString()

        return buildString {
            append(month)
            append(" ")
            append(day)
            append(" ")
            append(year)
            append(", ")
            append(hour)
            append(":")
            append(minute)
        }
    }
}