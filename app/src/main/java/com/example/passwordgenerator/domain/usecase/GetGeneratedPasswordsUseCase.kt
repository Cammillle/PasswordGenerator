package com.example.passwordgenerator.domain.usecase

import com.example.passwordgenerator.domain.repository.PasswordRepository
import javax.inject.Inject

class GetGeneratedPasswordsUseCase @Inject constructor(
    private val repository: PasswordRepository
) {
    suspend operator fun  invoke() = repository.getGenerated()
}