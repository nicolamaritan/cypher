package com.project.passwordmanager.model

import android.content.Context

object WidgetCredentialsData
{
    fun getWidgetCredentials(context: Context, appWidgetId: Int) : List<WidgetCredentialWithCredential>?
    {
        val dao = CredentialDatabase.getInstance(context).widgetCredentialDao
        return dao.getWidgetCredentialsJoinCredentials(appWidgetId).value
    }

    suspend fun addWidgetCredential(context: Context, appWidgetId: Int, credential: Credential)
    {
        val widgetCredential = WidgetCredential(appWidgetId, credential.id)
        val widgetCredentialDao = CredentialDatabase.getInstance(context).widgetCredentialDao
        widgetCredentialDao.insert(widgetCredential)
    }
}