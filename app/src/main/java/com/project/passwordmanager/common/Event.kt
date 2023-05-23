package com.project.passwordmanager.common

/**
 * Represents an event that can carry a payload of type T.
 *
 * @param T the type of the payload carried by the event.
 *
 * @constructor Creates an instance of the Event class with the specified content.
 * @param content the payload content of type T.
 */
open class Event<out T>(private val content: T) {

    /**
     * Indicates whether the event has been handled.
     */
    var handled = false
        private set // Allow external read but not write

    /**
     * Retrieves the content of the event.
     *
     * If the event has already been handled, returns null.
     * Otherwise, sets the event as handled and returns the content.
     *
     * @return the content of type T if the event has not been handled, or null if it has.
     */
    fun getContentIfNotHandled(): T? {
        if (handled) {
            return null
        }
        handled = true
        return content
    }

    /**
     * Peeks at the content of the event without marking it as handled.
     *
     * @return the content of type T.
     */
    fun peekContent(): T = content
}
