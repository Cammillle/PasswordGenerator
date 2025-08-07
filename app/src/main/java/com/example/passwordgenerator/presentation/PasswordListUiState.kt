package com.example.passwordgenerator.presentation

import com.example.passwordgenerator.domain.model.Password
import com.example.passwordgenerator.domain.model.PasswordFolder

sealed class PasswordListUiState {

    object Loading : PasswordListUiState()

    data class Root(val folders: List<PasswordFolder>, val passwords: List<Password>) :
        PasswordListUiState()

    data class FolderContent(val folder: PasswordFolder, val passwords: List<Password>) :
        PasswordListUiState()

}