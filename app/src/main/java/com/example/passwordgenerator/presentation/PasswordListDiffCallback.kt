package com.example.passwordgenerator.presentation

import androidx.recyclerview.widget.DiffUtil
import com.example.passwordgenerator.domain.model.PasswordListItem

class PasswordListDiffCallback : DiffUtil.ItemCallback<PasswordListItem>() {
    override fun areItemsTheSame(oldItem: PasswordListItem, newItem: PasswordListItem): Boolean {
        return when {
            oldItem is PasswordListItem.FolderHeaderItem && newItem is PasswordListItem.FolderHeaderItem ->
                oldItem.folderName == newItem.folderName

            oldItem is PasswordListItem.PasswordItem && newItem is PasswordListItem.PasswordItem ->
                oldItem.password.id == newItem.password.id

            else -> false
        }
    }

    override fun areContentsTheSame(oldItem: PasswordListItem, newItem: PasswordListItem): Boolean {
        return oldItem == newItem

    }
}