package com.project.passwordmanager.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.project.passwordmanager.R
import com.project.passwordmanager.adapters.CredentialsAdapter
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

        //Get the view model
        val viewModelFactory = CredentialsViewModelFactory(dao)
        viewModel = ViewModelProvider(
            this, viewModelFactory)[CredentialsViewModel::class.java]

        //setting the data binding for the layout
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner


        val adapter = CredentialsAdapter(requireContext())
        binding.homeRecyclerView.adapter = adapter

        // The Fragment listens for the deletion of a credential
        adapter.setDeleteListener(this)

        //passes the data to the adapter
        viewModel.credentials.observe(viewLifecycleOwner) {
            it?.let {
                adapter.data = it
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