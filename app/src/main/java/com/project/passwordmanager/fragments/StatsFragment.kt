package com.project.passwordmanager.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.project.passwordmanager.R
import com.project.passwordmanager.adapters.CredentialsStatusAdapter
import com.project.passwordmanager.databinding.FragmentStatsBinding
import com.project.passwordmanager.factories.StatsViewModelFactory
import com.project.passwordmanager.model.CredentialDatabase
import com.project.passwordmanager.viewmodels.StatsViewModel

class StatsFragment : Fragment()
{
    private var _binding: FragmentStatsBinding? = null
    private val binding get() = _binding!!
    lateinit var viewModel: StatsViewModel
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) : View
    {
        _binding = FragmentStatsBinding.inflate(inflater, container, false)
        val view = binding.root

        //Build the database (if it doesn't already exist)
        val application = requireNotNull(this.activity).application
        val dao = CredentialDatabase.getInstance(application).credentialDao

        //Get the view model
        val viewModelFactory = StatsViewModelFactory(dao)
        viewModel = ViewModelProvider(this, viewModelFactory)[StatsViewModel::class.java]

        //setting the data binding for the layout
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        val credentialsStatusAdapter = CredentialsStatusAdapter(requireContext())
        binding.passwordStatusRv.adapter = credentialsStatusAdapter

        viewModel.oldCredentialsCount.observe(viewLifecycleOwner){
            it?.let {
                binding.numberOfOldPasswordsTv.text = getString(R.string.number_of_old_passwords, it.toString())
            }
        }

        //passes the data to the adapter
        viewModel.credentials.observe(viewLifecycleOwner) {
            it?.let {
                credentialsStatusAdapter.submitList(it)
                binding.numberOfPwStoredTv.text =
                    resources.getString(R.string.number_of_passwords_stored, it.size.toString())
            }
        }

        return view
    }

    override fun onDestroyView()
    {
        super.onDestroyView()
        _binding = null
    }
}