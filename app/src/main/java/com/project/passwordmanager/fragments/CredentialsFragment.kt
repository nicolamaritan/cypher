package com.project.passwordmanager.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.project.passwordmanager.R
import com.project.passwordmanager.adapters.CredentialsAdapter
import com.project.passwordmanager.common.Constants
import com.project.passwordmanager.common.CredentialsOrder
import com.project.passwordmanager.databinding.FragmentCredentialsBinding
import com.project.passwordmanager.factories.CredentialsViewModelFactory
import com.project.passwordmanager.listeners.DeleteListener
import com.project.passwordmanager.model.CredentialDatabase
import com.project.passwordmanager.viewmodels.CredentialsViewModel

class CredentialsFragment : Fragment(), DeleteListener
{
    private var _binding: FragmentCredentialsBinding? = null
    private val binding get() = _binding!!
    lateinit var viewModel: CredentialsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View
    {
        _binding = FragmentCredentialsBinding.inflate(inflater, container, false)
        val view = binding.root

        //Build the database (if it doesn't already exist)
        val application = requireNotNull(this.activity).application
        val dao = CredentialDatabase.getInstance(application).credentialDao

        // Retrieves the checked option of ordering in the shared preferences
        val sharedPref = requireContext().getSharedPreferences(Constants.SYSTEM_PREFERENCES, Context.MODE_PRIVATE)
        val credentialsOrder = sharedPref.getInt(Constants.CREDENTIALS_ORDER, CredentialsOrder.CHRONOLOGICAL)

        //Get the view model
        val viewModelFactory = CredentialsViewModelFactory(dao)
        viewModel = ViewModelProvider(
            this, viewModelFactory)[CredentialsViewModel::class.java]

        viewModel.updateCredentialsOrder(credentialsOrder)
        //setting the data binding for the layout
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner


        val adapter = CredentialsAdapter(requireContext())
        binding.homeRecyclerView.adapter = adapter
        adapter.unlockedCredentials = viewModel.unlockedCredentials

        // The Fragment listens for the deletion of a credential
        adapter.setDeleteListener(this)

        //passes the data to the adapter
        viewModel.credentials.observe(viewLifecycleOwner) {
            it?.let {
                adapter.submitList(it)
            }
        }

        binding.fab.setOnClickListener{
            viewModel.showDialog(parentFragmentManager)
        }

        return view
    }


    override fun onDestroyView()
    {
        super.onDestroyView()
        _binding = null
    }

    override fun onDeleteCredential(credentialId: Int)
    {
        viewModel.deleteCredential(credentialId)
        Toast.makeText(context, getString(R.string.password_deleted), Toast.LENGTH_LONG).show()
    }

}