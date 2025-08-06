package com.example.passwordgenerator.domain.model


sealed class PasswordListItem {
    data class FolderItem(val folder: PasswordFolder) : PasswordListItem()
    data class PasswordItem(val password: Password) : PasswordListItem()

}