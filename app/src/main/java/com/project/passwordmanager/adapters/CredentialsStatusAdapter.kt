package com.project.passwordmanager.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.project.passwordmanager.R
import com.project.passwordmanager.common.DateChecker
import com.project.passwordmanager.common.DateStatus
import com.project.passwordmanager.model.Credential
import java.time.LocalDate

//Adapter for the password status's recyclerview
class CredentialsStatusAdapter(val context: Context):
    RecyclerView.Adapter<CredentialsStatusAdapter.StatsViewHolder>(){

    //definition of the data type we will work with
    var data = listOf<Credential>()
        //custom setter that tells the recyclerView if data changed
        set(value){
            field = value
            notifyDataSetChanged()
        }

    inner class StatsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
    {
        private val service: TextView = itemView.findViewById(R.id.stats_service)
        private val username: TextView = itemView.findViewById(R.id.stats_user)
        private val warningImg: ImageView = itemView.findViewById(R.id.warning_img)
        private val timePassed: TextView = itemView.findViewById(R.id.time_passed_value)

        fun bind(credential: Credential){
            service.text = credential.service
            username.text = credential.username
            timePassed.text = LocalDate.ofEpochDay(credential.date!!.toEpochDay()).toString()

            // Gets color in function of the date status
            val timePassedColor = when (DateChecker(LocalDate.now()).checkDate(credential.date!!)){
                DateStatus.NEW -> context.getColor(R.color.green)
                DateStatus.THREE_MONTHS_OLD -> context.getColor(R.color.orange)
                DateStatus.SIX_MONTHS_OLD -> context.getColor(R.color.red)
            }

            timePassed.setTextColor(timePassedColor)
            warningImg.setColorFilter(timePassedColor)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatsViewHolder
    {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.credentials_status_recyclerview_item, parent, false)

        return StatsViewHolder(view)
    }

    override fun getItemCount(): Int
    {
        return data.size
    }

    override fun onBindViewHolder(holder: StatsViewHolder, position: Int)
    {
        val item = data[position]
        holder.bind(item)
    }
}