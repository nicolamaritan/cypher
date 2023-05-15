package com.project.passwordmanager

import android.content.Context

class Datasource(private val context:Context){

    //OVVIAMENTE DOVRO' CONSIDERARE IL DATABASE, per ora uso Datasource come prova

    //returns the list of the applications' name (related to the passwords stored)
    fun getAppNameList(): Array<String>
    {
        return context.resources.getStringArray(R.array.lista_app)
    }

    //returns the list of the users' name
    fun getUserList(): Array<String>
    {
        return context.resources.getStringArray(R.array.lista_users)
    }

    //returns the list of the passwords
    fun getPasswordList(): Array<String>
    {
        return context.resources.getStringArray(R.array.lista_password)
    }
}