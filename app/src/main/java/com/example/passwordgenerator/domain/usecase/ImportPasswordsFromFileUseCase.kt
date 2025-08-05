package com.example.passwordgenerator.domain.usecase

import android.net.Uri
import com.example.passwordgenerator.domain.model.Password
import com.example.passwordgenerator.domain.repository.PasswordRepository

class ImportPasswordsFromFileUseCase(private val passwordRepository: PasswordRepository) {
    suspend operator fun invoke(fileName:String): List<Password> {
        return passwordRepository.importPasswordsFromFile(fileName)
    }
}