package com.project.passwordmanager.adapters

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.project.passwordmanager.R
import com.project.passwordmanager.fragments.ModifyDialogFragment
import com.project.passwordmanager.fragments.UnlockDialogFragment
import com.project.passwordmanager.listeners.DeleteListener
import com.project.passwordmanager.listeners.UnlockDialogListener
import com.project.passwordmanager.model.Credential
import com.project.passwordmanager.security.Cryptography


class CredentialsAdapter(private val context: Context):
    RecyclerView.Adapter<CredentialsAdapter.PwmViewHolder>(){

    //definition of the data type we will work with
    var data = listOf<Credential>()
        //custom setter that tells the recyclerView if data changed
        set(value){
            field = value
            notifyDataSetChanged()
        }

    private var deleteListener: DeleteListener? = null

    fun setDeleteListener(listener: DeleteListener)
    {
        deleteListener = listener
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
        private val deleteImageButton: ImageButton = itemView.findViewById(R.id.delete_imageButton)

        fun bind(credentialId:Int, service:String, user:String, password:String){
            appName.text = service
            appUser.text = user
            appPw.text = context.getString(R.string.locked_password)

            // --------- Lock Button ----------
            lockImageButton.setOnClickListener {
                if (locked)
                {
                    val activity = context as FragmentActivity
                    val fm: FragmentManager = activity.supportFragmentManager
                    val alertDialog = UnlockDialogFragment()

                    // Set an UnlockDialogListener to handle unlock events
                    alertDialog.setUnlockDialogListener(object : UnlockDialogListener {
                        override fun onUnlockSuccess()
                        {
                            locked = false
                            updatePasswordTextView(password, alertDialog.insertedMasterPassword)
                        }

                        override fun onUnlockFailure() {}
                    })

                    alertDialog.show(fm, "fragment_alert")
                }
                else
                {
                    appPw.text = context.getString(R.string.locked_password)
                    locked = true
                }
            }

            // --------- Copy Button ----------
            copyImageButton.setOnClickListener{
                if (!locked)
                {
                    copyPassword()
                }
                else
                {
                    Toast.makeText(context, "Unlock the password first.", Toast.LENGTH_SHORT).show()
                }
            }

            // --------- Edit Button ----------
            editImageButton.setOnClickListener {
                val activity = context as FragmentActivity
                val fm: FragmentManager = activity.supportFragmentManager
                val alertDialog = ModifyDialogFragment(credentialId)
                alertDialog.show(fm, "fragment_alert")
            }

            // --------- Delete Button ----------
            deleteImageButton.setOnClickListener{
                val activity = context as FragmentActivity
                val fm: FragmentManager = activity.supportFragmentManager
                val alertDialog = UnlockDialogFragment()

                // Set an UnlockDialogListener to handle unlock events
                alertDialog.setUnlockDialogListener(object : UnlockDialogListener {
                    override fun onUnlockSuccess()
                    {
                        deleteListener?.onDeleteCredential(credentialId)
                    }

                    override fun onUnlockFailure() {}
                })

                alertDialog.show(fm, UnlockDialogFragment.UNLOCK_DIALOG_FRAGMENT_TAG)
            }

        }

        private fun updatePasswordTextView(encryptedPassword: String, masterPassword: String)
        {
            appPw.text = if(locked) context.getString(R.string.locked_password)
                        else Cryptography.decryptText(encryptedPassword, masterPassword)
        }

        private fun copyPassword()
        {
            val clipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clipData = ClipData.newPlainText("Copied password", appPw.text)
            clipboardManager.setPrimaryClip(clipData)

            Toast.makeText(context, "Password copied to clipboard.", Toast.LENGTH_SHORT).show()
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
        holder.bind(item.id, item.service, item.username, item.password)
    }

}