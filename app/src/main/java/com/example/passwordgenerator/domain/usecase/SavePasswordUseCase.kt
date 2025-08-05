package com.example.passwordgenerator.domain.usecase

import com.example.passwordgenerator.domain.model.Password
import com.example.passwordgenerator.domain.repository.PasswordRepository

class SavePasswordUseCase(private val repository: PasswordRepository) {
    suspend operator fun invoke(password: Password) = repository.savePassword(password)
}