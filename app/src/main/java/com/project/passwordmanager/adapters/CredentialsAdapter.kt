package com.project.passwordmanager.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.project.passwordmanager.R
import com.project.passwordmanager.model.Credential

class CredentialsAdapter:
    RecyclerView.Adapter<CredentialsAdapter.PwmViewHolder>(){

    //definition of the data type we will work with
    var data = listOf<Credential>()
        //custom setter that tells the recyclerView if data changed
        set(value){
            field = value
            notifyDataSetChanged()
        }


    class PwmViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        private val appName: TextView = itemView.findViewById(R.id.service)
        private val appUser: TextView = itemView.findViewById(R.id.user)
        private val appPw: TextView = itemView.findViewById(R.id.password)

        fun bind(name:String, user:String, pw:String){
            appName.text = name
            appUser.text = user
            appPw.text = pw
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PwmViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.credentials_recyclerview_item, parent, false)

        return PwmViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: PwmViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item.service, item.username, item.password)
    }


}