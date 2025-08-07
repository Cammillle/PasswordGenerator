package com.example.passwordgenerator.presentation

import com.example.passwordgenerator.domain.model.Password
import com.example.passwordgenerator.domain.model.PasswordFolder


sealed class PasswordListItem {
    data class FolderItem(val folder: PasswordFolder) : PasswordListItem()
    data class PasswordItem(val password: Password) : PasswordListItem()

}