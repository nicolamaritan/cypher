package com.project.passwordmanager.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.project.passwordmanager.R
import com.project.passwordmanager.model.Credential


class WidgetConfigurationCredentialsAdapter(context: Context):
    RecyclerView.Adapter<WidgetConfigurationCredentialsAdapter.WidgetConfigurationCredentialsViewHolder>(){

    // Definition of the data type we will work with
    var data = listOf<Credential>()
        // Custom setter that tells the recyclerView if data changed
        set(value){
            field = value
            notifyDataSetChanged()
        }

    val selectedCredentialsIds: MutableList<Int> = mutableListOf()

    inner class WidgetConfigurationCredentialsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {

        private val service: TextView = itemView.findViewById(R.id.service)
        private val username: TextView = itemView.findViewById(R.id.user)
        private val checkBox: CheckBox = itemView.findViewById(R.id.add_cb)

        fun bind(credential: Credential)
        {
            service.text = credential.service
            username.text = credential.username
            checkBox.isChecked = false

            // Checking a credential implies putting it into the list
            checkBox.setOnCheckedChangeListener{ _, b ->
                if (b)
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

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: WidgetConfigurationCredentialsViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)
    }

}