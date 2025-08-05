package com.example.passwordgenerator.domain.usecase

import androidx.lifecycle.LiveData
import com.example.passwordgenerator.domain.model.Password
import com.example.passwordgenerator.domain.repository.PasswordRepository

class LoadPasswordsUseCase(private val passwordRepository: PasswordRepository) {
    operator fun invoke(): LiveData<List<Password>> {
        return passwordRepository.getAllPasswords()
    }
}