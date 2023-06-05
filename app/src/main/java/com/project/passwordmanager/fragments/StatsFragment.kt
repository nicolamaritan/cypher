package com.project.passwordmanager.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.project.passwordmanager.R
import com.project.passwordmanager.adapters.PwStatusAdapter
import com.project.passwordmanager.databinding.FragmentStatsBinding
import com.project.passwordmanager.factories.CredentialsViewModelFactory
import com.project.passwordmanager.factories.StatsViewModelFactory
import com.project.passwordmanager.model.CredentialDatabase
import com.project.passwordmanager.viewmodels.CredentialsViewModel
import com.project.passwordmanager.viewmodels.StatsViewModel
import java.time.LocalDate

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

        val pwStatusAdapter = PwStatusAdapter()
        binding.passwordStatusRv.adapter = pwStatusAdapter

        //passes the data to the adapter
        viewModel.credentials.observe(viewLifecycleOwner) {
            it?.let {
                pwStatusAdapter.data = it

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