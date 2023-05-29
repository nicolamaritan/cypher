package com.project.passwordmanager.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.project.passwordmanager.databinding.FragmentStatsBinding
import com.project.passwordmanager.viewmodels.StatsViewModel

class StatsFragment : Fragment()
{
    private var _binding: FragmentStatsBinding? = null
    private val binding get() = _binding!!
    lateinit var viewModel: StatsViewModel
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) : View?
    {
        _binding = FragmentStatsBinding.inflate(inflater, container, false)
        val view = binding.root
        viewModel = ViewModelProvider(this)[StatsViewModel::class.java]

        // Test binding
        binding.addTv.text = "Binding changed the textview again again"

        return view
    }

    override fun onDestroyView()
    {
        super.onDestroyView()
        _binding = null
    }
}