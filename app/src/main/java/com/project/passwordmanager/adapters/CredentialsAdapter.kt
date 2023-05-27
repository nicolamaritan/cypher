package com.project.passwordmanager.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.project.passwordmanager.R
import com.project.passwordmanager.model.Credential

class CredentialsAdapter(private val context: Context):
    RecyclerView.Adapter<CredentialsAdapter.PwmViewHolder>(){

    //definition of the data type we will work with
    var data = listOf<Credential>()
        //custom setter that tells the recyclerView if data changed
        set(value){
            field = value
            notifyDataSetChanged()
        }


    inner class PwmViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {

        private val appName: TextView = itemView.findViewById(R.id.service)
        private val appUser: TextView = itemView.findViewById(R.id.user)
        private var appPw: TextView = itemView.findViewById(R.id.password)
        private var locked: Boolean = true

        private val lockImageButton: ImageButton = itemView.findViewById(R.id.lock_imageButton)
        private val copyImageButton: ImageButton = itemView.findViewById(R.id.copy_imageButton)
        private val editImageButton: ImageButton = itemView.findViewById(R.id.edit_imageButton)

        fun bind(service:String, user:String, password:String){
            appName.text = service
            appUser.text = user
            updatePasswordTextView(password)

            lockImageButton.setOnClickListener{
                locked = !locked
                updatePasswordTextView(password)
            }
        }

        private fun updatePasswordTextView(clearPassword: String)
        {
            appPw.text = if(locked) context.getString(R.string.locked_password) else clearPassword
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