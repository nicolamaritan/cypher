package com.project.passwordmanager.common

/**
 * Enumeration representing the age status of a date.
 */
enum class DateStatus {
    /**
     * Indicates that the date is between 3 and 6 months old (exclusive).
     */
    THREE_MONTHS_OLD,

    /**
     * Indicates that the date is 6 months or older.
     */
    SIX_MONTHS_OLD,

    /**
     * Indicates that the date is newer than the current date.
     */
    NEW
}