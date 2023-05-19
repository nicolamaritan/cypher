package com.project.passwordmanager.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.project.passwordmanager.R
import com.project.passwordmanager.databinding.FragmentCredentialsBinding

class CredentialsFragment : Fragment()
{
    private var _binding: FragmentCredentialsBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCredentialsBinding.inflate(inflater, container, false)
        val view = binding.root

        // Test binding
        binding.helloTv.text = "Binding changed the textview"

        return view
    }

    override fun onDestroyView()
    {
        super.onDestroyView()
        _binding = null
    }

}