package com.example.passwordgenerator.domain.usecase

import com.example.passwordgenerator.domain.repository.PasswordRepository

class GetPasswordsByFolderIdUseCase(
    private val repository: PasswordRepository
) {
    suspend operator fun invoke(folderId: Long) = repository.getByFolder(folderId)
}