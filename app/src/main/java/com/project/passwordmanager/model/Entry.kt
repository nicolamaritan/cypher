package com.project.passwordmanager.model

data class Entry(val service: String, val username: String, val password: String, val encrypted: Boolean = true)