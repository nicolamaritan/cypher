package com.project.passwordmanager.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.project.passwordmanager.R

//NOTE: modify with the database
class CredentialsAdapter(private var app_lst: Array<String>, private var user_lst: Array<String>, private var pw_lst: Array<String>):
    RecyclerView.Adapter<CredentialsAdapter.PwmViewHolder>(){


    class PwmViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val appName: TextView = itemView.findViewById(R.id.service)
        val appUser: TextView = itemView.findViewById(R.id.user)
        val appPw: TextView = itemView.findViewById(R.id.password)

        fun bind(name:String, user:String, pw:String){
            appName.text = name
            appUser.text = user
            appPw.text = pw
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PwmViewHolder {
        val view =  LayoutInflater.from(parent.context)
            .inflate(R.layout.password_manager_item, parent, false)

        return PwmViewHolder(view)
    }

    override fun getItemCount(): Int {
        return user_lst.size
    }

    override fun onBindViewHolder(holder: PwmViewHolder, position: Int) {
        holder.bind(app_lst[position], user_lst[position], pw_lst[position])
    }


}