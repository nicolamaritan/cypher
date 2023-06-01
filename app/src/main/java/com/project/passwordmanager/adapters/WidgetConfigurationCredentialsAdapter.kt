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
    RecyclerView.Adapter<WidgetConfigurationCredentialsAdapter.PwmViewHolder>(){

    //definition of the data type we will work with
    var data = listOf<Credential>()
        //custom setter that tells the recyclerView if data changed
        set(value){
            field = value
            notifyDataSetChanged()
        }

    private var savedToBeAddedIds: List<Long>

    init
    {
        val sharedPreferences = context.getSharedPreferences(
            "widget_prefs",
            Context.MODE_PRIVATE
        )
        val toBeAddedIdsString = sharedPreferences.getString("toBeAddedIds", "") ?: ""
        savedToBeAddedIds = toBeAddedIdsString.split(",").mapNotNull { it.toLongOrNull() }
    }


    inner class PwmViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {

        private val service: TextView = itemView.findViewById(R.id.service)
        private val username: TextView = itemView.findViewById(R.id.user)
        private val checkBox: CheckBox = itemView.findViewById(R.id.add_cb)

        fun bind(credential: Credential)
        {
            service.text = credential.service
            username.text = credential.username
            checkBox.isChecked =  (credential.id in savedToBeAddedIds)
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PwmViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.widget_configuration_credentials_recyclerview_item, parent, false)

        return PwmViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: PwmViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)
    }

}