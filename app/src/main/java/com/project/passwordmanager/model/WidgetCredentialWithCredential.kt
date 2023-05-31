package com.project.passwordmanager.model

import androidx.room.Embedded

data class WidgetCredentialWithCredential(
    @Embedded val widgetCredential: WidgetCredential,
    @Embedded val credential: Credential
)