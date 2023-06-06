package com.project.passwordmanager.model

import androidx.room.TypeConverter
import java.time.LocalDate

/**
 * The LocalDateConverter class provides methods for converting LocalDate objects to a Long value and vice versa.
 * This class is used as a TypeConverter in database operations to store LocalDate values as Long values and retrieve them back.
 */
class LocalDateConverter
{

    /**
     * Converts a LocalDate object to a Long value representing the number of days since January 1, 1970 (epoch day).
     *
     * @param date The LocalDate object to be converted.
     * @return The Long value representing the date as the number of days since January 1, 1970, or null if the input is null.
     */
    @TypeConverter
    fun toLong(date: LocalDate?): Long?
    {
        return date?.toEpochDay()
    }

    /**
     * Converts a Long value representing the number of days since January 1, 1970 (epoch day) to a LocalDate object.
     *
     * @param value The Long value representing the date as the number of days since January 1, 1970.
     * @return The LocalDate object corresponding to the input value, or null if the input is null.
     */
    @TypeConverter
    fun fromLong(value: Long?): LocalDate?
    {
        return value?.let { LocalDate.ofEpochDay(it) }
    }
}
