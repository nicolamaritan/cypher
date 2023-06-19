package com.project.passwordmanager.common

import androidx.recyclerview.widget.DiffUtil
import com.project.passwordmanager.model.Credential

/**
 * Utility class used in conjunction with DiffUtil to determine the difference
 * between two lists of Credential objects.
 * This class is responsible for comparing items in the old list with
 * items in the new list to efficiently update a RecyclerView when changes occur.
 */
class CredentialDiffItemCallback : DiffUtil.ItemCallback<Credential>()
{
    override fun areItemsTheSame(oldItem: Credential, newItem: Credential) = (oldItem.id == newItem.id)

    override fun areContentsTheSame(oldItem: Credential, newItem: Credential) = (oldItem == newItem)
}