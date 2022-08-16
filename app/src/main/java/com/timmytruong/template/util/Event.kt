package com.timmytruong.template.util

class Event<out T>(private val item: T) {

    private var hasBeenHandled: Boolean = false

    fun get() = if (hasBeenHandled) {
        null
    } else {
        hasBeenHandled = true
        item
    }

    fun peek() = item
}