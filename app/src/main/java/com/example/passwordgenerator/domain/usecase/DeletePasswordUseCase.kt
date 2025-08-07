package com.example.passwordgenerator.domain.usecase

import com.example.passwordgenerator.domain.model.Password
import com.example.passwordgenerator.domain.repository.PasswordRepository
import javax.inject.Inject

class DeletePasswordUseCase @Inject constructor(private val repository: PasswordRepository) {
    suspend operator fun invoke(password: Password) = repository.delete(password)
}