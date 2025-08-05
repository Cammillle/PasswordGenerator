package com.example.passwordgenerator.domain.model

import com.example.passwordgenerator.data.entity.PasswordEntity

sealed class PasswordListItem {
    data class FolderHeaderItem(val folderName: String) : PasswordListItem()
    data class PasswordItem(val password: PasswordEntity) : PasswordListItem()

}