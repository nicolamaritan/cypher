package com.project.passwordmanager.fragments

import android.app.Activity
import android.appwidget.AppWidgetManager
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RemoteViews
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.project.passwordmanager.R
import com.project.passwordmanager.adapters.WidgetConfigurationCredentialsAdapter
import com.project.passwordmanager.common.WidgetPreferencesManager
import com.project.passwordmanager.databinding.FragmentWidgetConfigurationBinding
import com.project.passwordmanager.factories.WidgetConfigurationViewModelFactory
import com.project.passwordmanager.model.CredentialDatabase
import com.project.passwordmanager.viewmodels.WidgetConfigurationViewModel
import com.project.passwordmanager.widgets.credentials.PasswordManagerWidget

class WidgetConfigurationFragment : Fragment()
{
    private var _binding: FragmentWidgetConfigurationBinding? = null
    private val binding get() = _binding!!
    lateinit var viewModel: WidgetConfigurationViewModel

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
        viewModel = ViewModelProvider(this, viewModelFactory)[WidgetConfigurationViewModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        // Adapter setup
        val adapter = WidgetConfigurationCredentialsAdapter(requireContext())
        adapter.selectedCredentialsIds = viewModel.checkedCredentialsIds    // Retrieves previously selected ids from viewmodel
                                                                            // ViewModel is updated as it holds the list reference
        binding.configurationCredentialsRv.adapter = adapter

        // Passing data to the adapter
        viewModel.credentials.observe(viewLifecycleOwner) {
            it?.let {
                adapter.data = it
            }
        }

        // Retrieves data from viewModel
        binding.widgetNameEt.setText(viewModel.widgetName)

        // Get the intent which invoked the configuration activity, which will contain tha appWidgetId
        val activity = requireActivity()
        val appWidgetId = activity.intent?.extras?.getInt(
            AppWidgetManager.EXTRA_APPWIDGET_ID,
            AppWidgetManager.INVALID_APPWIDGET_ID
        ) ?: AppWidgetManager.INVALID_APPWIDGET_ID


        val resultValue = Intent().putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
        activity.setResult(Activity.RESULT_CANCELED, resultValue)

        binding.fab.setOnClickListener{

            // Represents the selected ids that will be added to the widget
            val toBeAddedIds = adapter.selectedCredentialsIds

            // To instantiate the widget you need to select at least one credential
            if (toBeAddedIds.isNotEmpty())
            {
                activity.setResult(Activity.RESULT_OK, resultValue)

                val widgetPreferencesManager = WidgetPreferencesManager(requireContext(), appWidgetId)
                widgetPreferencesManager.insertName(binding.widgetNameEt.text.toString())
                widgetPreferencesManager.insertAddedIds(toBeAddedIds)


                val viewMapping = PasswordManagerWidget.getWidgetViewMapping(requireContext(), appWidgetId)
                // The returned RemoteViews is chosen based on the widget size, according to the mapping
                val views = RemoteViews(viewMapping)

                val appWidgetManager = AppWidgetManager.getInstance(context)
                appWidgetManager.updateAppWidget(appWidgetId, views)

                activity.finish()
            }
            else
            {
                Toast.makeText(context, requireContext().getString(R.string.select_at_least), Toast.LENGTH_SHORT).show()
            }

        }

        return view
    }

    override fun onPause()
    {
        super.onPause()
        viewModel.widgetName = binding.widgetNameEt.text.toString()
    }

    override fun onDestroy()
    {
        super.onDestroy()
        _binding = null
    }

}