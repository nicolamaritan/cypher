package com.project.passwordmanager.common

import java.time.LocalDate

class DateChecker(private val currentDate: LocalDate)
{
    fun checkDate(date: LocalDate): DateStatus
    {
        return when {
            date.isBefore(currentDate.minusMonths(3))
                    && date.isAfter(currentDate.minusMonths(6)) -> DateStatus.THREE_MONTHS_OLD
            date.isBefore(currentDate.minusMonths(6)) -> DateStatus.SIX_MONTHS_OLD
            else -> DateStatus.NEW
        }
    }
}

