package com.project.passwordmanager.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.project.passwordmanager.R
import com.project.passwordmanager.common.CredentialDiffItemCallback
import com.project.passwordmanager.model.Credential


class WidgetConfigurationCredentialsAdapter():
    ListAdapter<Credential, WidgetConfigurationCredentialsAdapter.WidgetConfigurationCredentialsViewHolder>
        (CredentialDiffItemCallback()){

    /**
     * Contains the selected ids to show in the widget.
     */
    var selectedCredentialsIds: MutableList<Int> = mutableListOf()

    inner class WidgetConfigurationCredentialsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {

        private val service: TextView = itemView.findViewById(R.id.service)
        private val username: TextView = itemView.findViewById(R.id.user)
        private val checkBox: CheckBox = itemView.findViewById(R.id.add_cb)

        fun bind(credential: Credential)
        {
            service.text = credential.service
            username.text = credential.username
            checkBox.isChecked = selectedCredentialsIds.contains(credential.id)

            // Checking a credential implies putting it into the list
            checkBox.setOnCheckedChangeListener{ _, checked ->
                if (checked)
                {
                    selectedCredentialsIds.add(credential.id)
                }
                else
                {
                    selectedCredentialsIds.remove(credential.id)
                }
            }
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WidgetConfigurationCredentialsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.widget_configuration_credentials_recyclerview_item, parent, false)

        return WidgetConfigurationCredentialsViewHolder(view)
    }

    override fun onBindViewHolder(holder: WidgetConfigurationCredentialsViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

}