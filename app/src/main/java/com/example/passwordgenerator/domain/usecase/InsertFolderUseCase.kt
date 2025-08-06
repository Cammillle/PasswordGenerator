package com.example.passwordgenerator.domain.usecase

import com.example.passwordgenerator.domain.model.Password
import com.example.passwordgenerator.domain.model.PasswordFolder
import com.example.passwordgenerator.domain.repository.PasswordFolderRepository
import com.example.passwordgenerator.domain.repository.PasswordRepository

class InsertFolderUseCase(
    private val repository: PasswordFolderRepository
) {
    suspend operator fun invoke(folder: PasswordFolder) = repository.insertFolder(folder)
}