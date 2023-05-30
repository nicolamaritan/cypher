package com.project.passwordmanager.fragments

import android.app.Activity
import android.appwidget.AppWidgetManager
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.project.passwordmanager.adapters.CredentialsAdapter
import com.project.passwordmanager.databinding.FragmentWidgetConfigurationBinding
import com.project.passwordmanager.factories.CredentialsViewModelFactory
import com.project.passwordmanager.factories.WidgetConfigurationViewModelFactory
import com.project.passwordmanager.model.CredentialDatabase
import com.project.passwordmanager.viewmodels.WidgetConfigurationViewModel

class WidgetConfigurationFragment : Fragment()
{
    private var _binding: FragmentWidgetConfigurationBinding? = null
    private val binding get() = _binding!!
    lateinit var viewModel: ViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentWidgetConfigurationBinding.inflate(inflater, container, false)
        val view = binding.root

        // Setup view model and binding's viewmodel
        val application = requireNotNull(this.activity).application
        val dao = CredentialDatabase.getInstance(application).credentialDao
        val viewModelFactory = WidgetConfigurationViewModelFactory(dao)
        val viewModel = ViewModelProvider(this, viewModelFactory)[WidgetConfigurationViewModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        // Adapter setup
        val adapter = CredentialsAdapter(requireContext())
        binding.configurationCredentialsRv.adapter = adapter

        // Passing data to the adapter
        viewModel.credentials.observe(viewLifecycleOwner) {
            it?.let {
                adapter.data = it
            }
        }

        val activity = requireActivity()

        // Get the intent which invoked the configuration activity, which will contain tha appWidgetId
        val appWidgetId = activity.intent?.extras?.getInt(
            AppWidgetManager.EXTRA_APPWIDGET_ID,
            AppWidgetManager.INVALID_APPWIDGET_ID
        ) ?: AppWidgetManager.INVALID_APPWIDGET_ID

        val resultValue = Intent().putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
        activity.setResult(Activity.RESULT_CANCELED, resultValue)



        //val appWidgetManager = AppWidgetManager.getInstance(applicationContext)
        //val views = RemoteViews(applicationContext.packageName, R.layout.example_appwidget)
        //appWidgetManager.updateAppWidget(appWidgetId, views)

        binding.fab.setOnClickListener{
            activity.setResult(Activity.RESULT_OK, resultValue)
            activity.finish()
        }

        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}