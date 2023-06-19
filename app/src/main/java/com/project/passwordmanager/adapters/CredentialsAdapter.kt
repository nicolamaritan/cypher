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
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.project.passwordmanager.R
import com.project.passwordmanager.common.CredentialDiffItemCallback
import com.project.passwordmanager.fragments.ModifyDialogFragment
import com.project.passwordmanager.fragments.UnlockDialogFragment
import com.project.passwordmanager.listeners.DeleteListener
import com.project.passwordmanager.listeners.UnlockDialogListener
import com.project.passwordmanager.model.Credential
import com.project.passwordmanager.security.Cryptography

/**
 * This Adapter is responsible of linking the data fetched from the
 * database and the view in the CredentialsFragment. It shows the data and
 * setup the various operation for each item. Each item has the operation of
 * modify, remote, copy and lock/unlock.
 *
 * @param context the application context
 */
class CredentialsAdapter(private val context: Context):
    ListAdapter<Credential, CredentialsAdapter.CredentialsViewHolder>(CredentialDiffItemCallback()){

    private var deleteListener: DeleteListener? = null
    var unlockedCredentials = mutableListOf<Credential>()

    fun setDeleteListener(listener: DeleteListener)
    {
        deleteListener = listener
    }

    inner class CredentialsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        private val service: TextView = itemView.findViewById(R.id.service)
        private val username: TextView = itemView.findViewById(R.id.user)
        private var password: TextView = itemView.findViewById(R.id.password)
        private var locked: Boolean = true

        private val lockImageButton: ImageButton = itemView.findViewById(R.id.lock_imageButton)
        private val copyImageButton: ImageButton = itemView.findViewById(R.id.copy_imageButton)
        private val editImageButton: ImageButton = itemView.findViewById(R.id.edit_imageButton)
        private val deleteImageButton: ImageButton = itemView.findViewById(R.id.delete_imageButton)

        fun bind(credential: Credential)
        {
            this.service.text = credential.service
            this.username.text = credential.username

            var found = false
            unlockedCredentials.forEach{
                if (it.id == credential.id)
                {
                    this.password.text = it.password
                    locked = false
                    found = true
                }
            }
            if (!found)
            {
                this.password.text = context.getString(R.string.locked_password)
            }

            // --------- Lock Button ----------
            lockImageButton.setOnClickListener {
                if (locked)
                {
                    val activity = context as FragmentActivity
                    val fm: FragmentManager = activity.supportFragmentManager
                    val unlockDialogFragment = UnlockDialogFragment()

                    // Set an UnlockDialogListener to handle unlock events
                    unlockDialogFragment.setUnlockDialogListener(object : UnlockDialogListener {
                        override fun onUnlockSuccess()
                        {
                            locked = false

                            /* Save the unlocked credential with the password in clear.
                               This is used to restore the clear password when the screen
                               rotates.
                             */
                            unlockedCredentials.add(
                                Credential(
                                    id = credential.id,
                                    password = Cryptography.decryptText(credential.password, unlockDialogFragment.insertedMasterPassword)
                                )
                            )
                            updatePasswordTextView(credential.password, unlockDialogFragment.insertedMasterPassword)
                        }
                    })

                    unlockDialogFragment.show(fm, UnlockDialogFragment.UNLOCK_DIALOG_FRAGMENT_TAG)
                }
                else
                {
                    // Remove the saved credential as it is locked
                    unlockedCredentials.remove(
                        Credential(
                            id = credential.id,
                            password = this.password.text.toString()
                        )
                    )

                    this.password.text = context.getString(R.string.locked_password)
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
                    Toast.makeText(context, context.getString(R.string.unlock_the_password_first), Toast.LENGTH_SHORT).show()
                }
            }

            // --------- Edit Button ----------
            editImageButton.setOnClickListener {
                if(!locked)
                {
                    // Lock and removes state - Editing a credential locks it
                    locked = true
                    this.password.text = context.getString(R.string.locked_password)
                    for (i in 0 until unlockedCredentials.size) {
                        if (unlockedCredentials[i].id == credential.id) {
                            unlockedCredentials.removeAt(i)
                            break
                        }
                    }

                    val activity = context as FragmentActivity
                    val fm: FragmentManager = activity.supportFragmentManager
                    //val alertDialog = ModifyDialogFragment(credential)
                    val modifyDialogFragment = ModifyDialogFragment.newInstance(credential)
                    modifyDialogFragment.show(fm, UnlockDialogFragment.UNLOCK_DIALOG_FRAGMENT_TAG)
                }
                else
                {
                    Toast.makeText(context, context.getString(R.string.unlock_the_password_first), Toast.LENGTH_SHORT).show()
                }
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
                        deleteListener?.onDeleteCredential(credential.id)
                    }
                })

                alertDialog.show(fm, UnlockDialogFragment.UNLOCK_DIALOG_FRAGMENT_TAG)
            }

        }

        /**
         * Updates the password textview according to the locked state.
         *
         * @param encryptedPassword the encrypted password
         * @param masterPassword the clear master password used for decryption
         */
        private fun updatePasswordTextView(encryptedPassword: String, masterPassword: String)
        {
            password.text = if(locked) context.getString(R.string.locked_password)
                        else Cryptography.decryptText(encryptedPassword, masterPassword)
        }

        /**
         * Copies the password to the clipboard.
         */
        private fun copyPassword()
        {
            val clipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clipData = ClipData.newPlainText("Copied password", password.text)
            clipboardManager.setPrimaryClip(clipData)

            Toast.makeText(context, context.getString(R.string.password_copied_to_clipboard), Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CredentialsViewHolder
    {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.credentials_recyclerview_item, parent, false)

        return CredentialsViewHolder(view)
    }

    override fun onBindViewHolder(holder: CredentialsViewHolder, position: Int)
    {
        val item = getItem(position)
        holder.bind(item)
    }

}