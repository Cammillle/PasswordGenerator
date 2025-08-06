package com.example.passwordgenerator.presentation.password_list_fragment

import androidx.recyclerview.widget.DiffUtil
import com.example.passwordgenerator.domain.model.PasswordListItem

class PasswordListDiffCallback : DiffUtil.ItemCallback<PasswordListItem>() {
    override fun areItemsTheSame(old: PasswordListItem, new: PasswordListItem): Boolean {
        return when {
            old is PasswordListItem.FolderItem && new is PasswordListItem.FolderItem ->
                old.folder.id == new.folder.id

            old is PasswordListItem.PasswordItem && new is PasswordListItem.PasswordItem ->
                old.password.id == new.password.id

            else -> false
        }
    }

    override fun areContentsTheSame(oldItem: PasswordListItem, newItem: PasswordListItem): Boolean {
        return oldItem == newItem

    }
}