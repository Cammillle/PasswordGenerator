package com.example.passwordgenerator.domain.usecase

import com.example.passwordgenerator.domain.repository.PasswordRepository
import javax.inject.Inject

class GetPasswordsByFolderIdUseCase @Inject constructor(
    private val repository: PasswordRepository
) {
    suspend operator fun invoke(folderId: Long) = repository.getByFolder(folderId)
}