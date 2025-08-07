package com.example.passwordgenerator.domain.usecase

import com.example.passwordgenerator.domain.repository.PasswordFolderRepository
import com.example.passwordgenerator.domain.repository.PasswordRepository
import javax.inject.Inject

class GetAllFoldersUseCase @Inject constructor (private val repository: PasswordFolderRepository
) {
    suspend operator fun  invoke() = repository.getAllFolders()
}