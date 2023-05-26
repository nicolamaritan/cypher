package com.project.passwordmanager.viewmodels

import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.project.passwordmanager.fragments.CredentialsDialogFragment
import com.project.passwordmanager.model.Credential
import com.project.passwordmanager.model.CredentialDao

class CredentialsViewModel(private val dao: CredentialDao) : ViewModel()
{
    private val credentials = dao.getAll()  //used for the RecyclerView to show all the tuples
    //take the credentials data and remap them in a more simple list of Strings
    /*val credentialsString = credentials.map() {
            credentials -> formatCredentials(credentials)
    }*/


    fun serviceCredential(credential: LiveData<List<Credential>>): Array<String>{
        val serviceList: MutableList<String> = mutableListOf()
        // Observe the LiveData and access its value
        credential.observeForever { credentials ->
            credentials?.let {
                for (item in credentials)
                    serviceList.add(item.service)
            }
        }
        return serviceList.toTypedArray()
    }

    fun userCredential(credential: LiveData<List<Credential>>): Array<String>{
        val userList: MutableList<String> = mutableListOf()
        // Observe the LiveData and access its value
        credential.observeForever { credentials ->
            credentials?.let {
                for (item in credentials)
                    userList.add(item.service)
            }
        }
        return userList.toTypedArray()
    }

    fun pwCredential(credential: LiveData<List<Credential>>): Array<String>{
        val pwList: MutableList<String> = mutableListOf()
        // Observe the LiveData and access its value
        credential.observeForever { credentials ->
            credentials?.let {
                for (item in credentials)
                    pwList.add(item.service)
            }
        }
        return pwList.toTypedArray()
    }

    val services = serviceCredential(credentials)
    val users = userCredential(credentials)
    val pws = pwCredential(credentials)



    fun showDialog(fragmentManager: FragmentManager)
    {
        val newFragment = CredentialsDialogFragment()
        newFragment.show(fragmentManager, TAG)
    }

    companion object
    {
        private val TAG = CredentialsViewModel::javaClass.toString()
    }
}