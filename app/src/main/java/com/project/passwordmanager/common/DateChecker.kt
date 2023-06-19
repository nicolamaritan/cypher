package com.project.passwordmanager.common

import java.time.LocalDate

/**
 * The DateChecker class allows you to check the age of a given date
 * relative to a specified current date.
 *
 * @property currentDate The reference date against which other dates will be compared.
 */
class DateChecker(private val currentDate: LocalDate) {

    /**
     * Checks the age of the given date relative to the current date.
     *
     * @param date The date to be checked.
     * @return The age of the given date as a DateStatus enumeration value.
     */
    fun checkDate(date: LocalDate): DateStatus
    {
        return when
        {
            date.isBefore(currentDate.minusMonths(3))
                    && date.isAfter(currentDate.minusMonths(6)) -> DateStatus.THREE_MONTHS_OLD
            date.isBefore(currentDate.minusMonths(6)) -> DateStatus.SIX_MONTHS_OLD
            else -> DateStatus.NEW
        }
    }
}

