package com.project.passwordmanager.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.project.passwordmanager.R
import com.project.passwordmanager.databinding.FragmentAddcredentialsBinding
import com.project.passwordmanager.viewmodels.CredentialsViewModel

class AddCredentialsFragment : Fragment()
{
    private var _binding: FragmentAddcredentialsBinding? = null
    private val binding get() = _binding!!
    lateinit var viewModel: CredentialsViewModel
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) : View?
    {
        _binding = FragmentAddcredentialsBinding.inflate(inflater, container, false)
        val view = binding.root
        viewModel = ViewModelProvider(this)[CredentialsViewModel::class.java]

        // Test binding
        binding.addTv.text = "Binding changed the textview again"

        return view
    }

    override fun onDestroyView()
    {
        super.onDestroyView()
        _binding = null
    }
}