package com.project.passwordmanager.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.project.passwordmanager.R
import com.project.passwordmanager.model.Credential

//Adapter for the password status's recyclerview
class PwStatusAdapter():
    RecyclerView.Adapter<PwStatusAdapter.StatsViewHolder>(){

    //definition of the data type we will work with
    var data = listOf<Credential>()
        //custom setter that tells the recyclerView if data changed
        set(value){
            field = value
            notifyDataSetChanged()
        }

    inner class StatsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
    {
        private val appName: TextView = itemView.findViewById(R.id.stats_service)
        private val appUser: TextView = itemView.findViewById(R.id.stats_user)

        fun bind(service:String, user:String){
            appName.text = service
            appUser.text = user
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.pw_status_rv_item, parent, false)

        return StatsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: StatsViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item.service, item.username)
    }
}