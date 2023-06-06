package com.project.passwordmanager.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.project.passwordmanager.R
import com.project.passwordmanager.model.Credential
import java.time.LocalDate

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
        private val warningImg: ImageView = itemView.findViewById(R.id.warning_img)
        private val timePassed: TextView = itemView.findViewById(R.id.time_passed_value)

        fun bind(service:String, user:String, date:LocalDate?){
            appName.text = service
            appUser.text = user

            timePassed.text = LocalDate.ofEpochDay(date!!.toEpochDay()).toString()

            // Change the color based on the time passed from the last modify
            val numDays: Long = LocalDate.now().toEpochDay() - date.toEpochDay()
            if(numDays > 180) {   //if are passed about 6 months...
                warningImg.setColorFilter(Color.RED)
                timePassed.setTextColor(Color.RED)
            }
            else if(numDays > 90) { //...else if are passed about 3 months...
                warningImg.setColorFilter(Color.YELLOW)
                timePassed.setTextColor(Color.YELLOW)
            }
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
        holder.bind(item.service, item.username, item.date)
    }
}