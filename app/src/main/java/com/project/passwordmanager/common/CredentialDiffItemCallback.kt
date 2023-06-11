package com.project.passwordmanager.common

import androidx.recyclerview.widget.DiffUtil
import com.project.passwordmanager.model.Credential

class CredentialDiffItemCallback : DiffUtil.ItemCallback<Credential>()
{
    override fun areItemsTheSame(oldItem: Credential, newItem: Credential) = (oldItem.id == newItem.id)

    override fun areContentsTheSame(oldItem: Credential, newItem: Credential) = (oldItem == newItem)
}