package com.project.passwordmanager.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.project.passwordmanager.adapters.CredentialsAdapter
import com.project.passwordmanager.databinding.FragmentCredentialsBinding
import com.project.passwordmanager.factories.CredentialsViewModelFactory
import com.project.passwordmanager.model.CredentialDatabase
import com.project.passwordmanager.viewmodels.CredentialsViewModel

class CredentialsFragment : Fragment()
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


        val adapter = CredentialsAdapter()
        binding.homeRecyclerView.adapter = adapter

        //passes the data to the adapter
        viewModel.credentials.observe(viewLifecycleOwner, Observer {
            it?.let{
                adapter.data = it
            }
        })


        binding.addCredentialButton.setOnClickListener{
            viewModel.showDialog(parentFragmentManager)
        }

        return view
    }


    override fun onDestroyView()
    {
        super.onDestroyView()
        _binding = null
    }

}