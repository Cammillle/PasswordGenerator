package com.example.passwordgenerator.domain.usecase

import com.example.passwordgenerator.domain.repository.PasswordRepository

class GetGeneratedPasswordsUseCase(
    private val repository: PasswordRepository
) {
    suspend operator fun  invoke() = repository.getGenerated()
}