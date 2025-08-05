package com.example.passwordgenerator.domain.usecase

import android.net.Uri
import com.example.passwordgenerator.domain.model.Password
import com.example.passwordgenerator.domain.repository.PasswordRepository

class ExportPasswordsToFileUseCase(private val passwordRepository: PasswordRepository) {
    suspend operator fun invoke(passwords: List<Password>, fileUri: Uri) {
        passwordRepository.exportPasswordsToFile(passwords, fileUri)
    }
}