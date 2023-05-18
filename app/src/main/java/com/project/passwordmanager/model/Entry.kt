package com.project.passwordmanager.model

data class Entry(var service: String, var username: String, var password: String, var encrypted: Boolean = true)